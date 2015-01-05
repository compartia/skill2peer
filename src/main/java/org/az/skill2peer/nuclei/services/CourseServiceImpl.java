package org.az.skill2peer.nuclei.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.Validator;

import org.az.skill2peer.nuclei.common.controller.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoListItemDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LocationDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseFavorite;
import org.az.skill2peer.nuclei.common.model.CourseStatus;
import org.az.skill2peer.nuclei.common.model.HasIntegerId;
import org.az.skill2peer.nuclei.common.model.HasOwner;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Location;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
import org.az.skill2peer.nuclei.user.repository.CourseRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;

/**
*
* @author Artem Zaborskiy
*
*/
@Service
public class CourseServiceImpl implements CourseService, CourseAdminService {
    public static boolean containsId(final Collection<? extends HasIntegerId> lessons, final Integer id) {
        for (final HasIntegerId o : lessons) {
            if (id.equals(o.getId())) {
                return true;
            }
        }
        return false;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    Mapper mapper;

    @Resource
    CourseRepository repo;

    @PersistenceContext
    EntityManager em;

    @Autowired
    Validator validator;

    @Override
    @Transactional(readOnly = false)
    public CourseEditDto createCourse(final CourseEditDto courseEditDto) {
        final Course course = new Course();
        mapper.map(courseEditDto, course, "course-edit");

        course.setAuthor(getCurrentUser());
        course.setId(null);
        course.setStatus(CourseStatus.DRAFT);

        updateLessons(course, courseEditDto.getLessons());
        em.persist(course);

        final CourseEditDto ret = new CourseEditDto();
        mapper.map(course, ret);
        return ret;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCourse(final Integer courseId) {
        final Course draftCourse = getCourse(courseId);
        assertNotPublished(draftCourse);
        em.remove(draftCourse);
    }

    @Transactional(readOnly = false)
    public Course editCourse(final Integer courseId) {
        final Course course = getCourse(courseId);
        assertCurrentUserHasPermission(course);

        if (course.getStatus() == CourseStatus.DRAFT) {
            return course;
        } else {
            return makeOrGetDraft(course);
        }

    }

    @Override
    public List<LocationDto> getAvailableLocations() {
        final List<Location> l = em.createNamedQuery("User.getAvailableLocations", Location.class)
                .setParameter("authorId", getCurrentUser().getId())
                .getResultList();

        final List<LocationDto> ret = new ArrayList<LocationDto>();
        for (final Location loc : l) {
            final LocationDto dto = new LocationDto();

            mapper.map(loc, dto);

            ret.add(dto);
        }
        return ret;

    }

    @Override
    public List<CourseFavorite> getCourseFavorites(final Integer courseId, final Integer userId) {
        final List<CourseFavorite> resultList = em
                .createNamedQuery("CourseFavorite.findByUserAndCourse", CourseFavorite.class)
                .setParameter("userId", userId)
                .setParameter("courseId", courseId)
                .getResultList();
        return resultList;
    }

    @Override
    public CourseInfoDto getCourseFullInfo(final Integer id) {
        final Course course = getCourse(id);
        Preconditions.checkState(course.getStatus() == CourseStatus.PUBLISHED);
        final CourseInfoDto dto = new CourseInfoDto();
        mapper.map(course, dto);
        return dto;
    }

    @Override
    public CourseMetaDataDto getCourseInfo(final Integer id) {
        final CourseMetaDataDto dto = new CourseMetaDataDto();
        final Course c = repo.findOne(id);

        dto.setId(c.getId());
        dto.setFavorited(!getCourseFavorites(id, SecurityUtil.getCurrentUser().getId()).isEmpty());
        return dto;
    }

    @Override
    @Transactional(readOnly = false)
    public CourseEditDto getEditableCourse(final Integer courseId) {
        final Course editableCourse = editCourse(courseId);
        final CourseEditDto editableDto = new CourseEditDto();
        mapper.map(editableCourse, editableDto);
        return editableDto;
    }

    @Override
    public List<CourseInfoListItemDto> getMyCourses() {
        final List<Course> resultList = em
                .createNamedQuery("Course.findAllByAuthor", Course.class)
                .setParameter("authorId", getCurrentUser().getId())
                .getResultList();
        final List<CourseInfoListItemDto> dtos = new ArrayList<CourseInfoListItemDto>();
        for (final Course c : resultList) {
            final CourseInfoListItemDto dto = new CourseInfoListItemDto();
            mapper.map(c, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * admin/moderator method
     */
    @Override
    public Integer publishCourse(final Integer courseId) {
        final Course draft = getCourse(courseId);
        assertNotPublished(draft);

        final Course publishedVersion = draft.getPublishedVersion();
        if (null == publishedVersion) {
            // create published version copy data to the published one;
            final Course clonedObject = cloneCourse(draft);
            clonedObject.setStatus(CourseStatus.PUBLISHED);
            return clonedObject.getId();
        } else {
            //  copy data to the published one;

            mapper.map(draft, publishedVersion, "course-copy-publish");
            publishedVersion.setStatus(CourseStatus.PUBLISHED);
            publishedVersion.setDraft(null);
            publishedVersion.setPublishedVersion(null);

            em.persist(publishedVersion);
            em.remove(draft);

            return publishedVersion.getId();
        }

    }

    @Override
    @Transactional(readOnly = false)
    public CourseEditDto updateCourse(final @Valid CourseEditDto courseDto) {
        LOGGER.debug("updating course " + courseDto.getId());

        final Course course = getCourse(courseDto.getId());

        assertCurrentUserHasPermission(course);
        assertNotPublished(course);
        mapper.map(courseDto, course, "course-edit");
        updateLessons(course, courseDto.getLessons());
        course.setStatus(CourseStatus.DRAFT);

        em.persist(course);

        final CourseEditDto ret = new CourseEditDto();
        mapper.map(course, ret);
        //-----
        return ret;
    }

    private void assertCurrentUserHasPermission(final HasOwner obj) {
        //XXX: move to a service
        //XXX: make it aspect
    }

    private void assertNotPublished(final Course course) {
        Preconditions.checkNotNull(course);
        Preconditions.checkState(course.getStatus() != CourseStatus.PUBLISHED);
        Preconditions.checkState(course.getDraft() == null);
    }

    private User getCurrentUser() {
        final User user = em.find(User.class, SecurityUtil.getCurrentUser().getId());
        Assert.notNull(user);
        return user;
    }

    private Course makeOrGetDraft(final Course course) {
        if (course.getDraft() != null) {
            return course.getDraft();
        } else {
            //make deep copy;
            final Course clonedObject = cloneCourse(course);
            course.setDraft(clonedObject);
            clonedObject.setId(null);
            clonedObject.setStatus(CourseStatus.DRAFT);
            clonedObject.setAuthor(getCurrentUser());

            clonedObject.setPublishedVersion(course);
            em.persist(course);
            em.persist(clonedObject);

            return clonedObject;
        }
    }

    private void updateLessons(final Course course, final List<LessonEditDto> lessons) {
        if (course.getLessons() == null) {
            course.setLessons(new ArrayList<Lesson>());
        } else {
            course.getLessons().clear();
        }
        for (final LessonEditDto lessonDto : lessons) {
            final Lesson lesson;

            if (lessonDto.getId() != null) {
                lesson = em.find(Lesson.class, lessonDto.getId());
            } else {
                lesson = new Lesson();
            }

            mapper.map(lessonDto, lesson, "lesson-update");

            lesson.setCourse(course);
            Preconditions.checkNotNull(lessonDto.getSchedule());
            Preconditions.checkNotNull(lesson.getSchedule());

            updateLocation(lesson, lessonDto.getLocation());
            course.getLessons().add(lesson);

        }

        if (course.getLessons().size() == 1) {
            /**
             * the only lesson
             */
            final Lesson theOnly = course.getLessons().get(0);
            course.setName(theOnly.getName());
            course.setSummary(theOnly.getSummary());
            course.setDescription(theOnly.getDescription());
        }

    }

    private void updateLocation(final Lesson lesson, final LocationDto locationDto) {
        final Location existingLocation = em.find(Location.class, locationDto.getId());
        if (existingLocation == null) {
            final Location newLocation = new Location();
            mapper.map(locationDto, newLocation);
            newLocation.setId(null);
            lesson.setLocation(newLocation);
        } else {
            lesson.setLocation(existingLocation);
        }
    }

    /**
     * makes deep copy
     * @param course
     * @return
     */
    Course cloneCourse(final Course course) {
        final Course clonedObject = new Course();
        mapper.map(course, clonedObject, "course-copy-publish");

        clonedObject.setAuthor(getCurrentUser());
        return clonedObject;
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    Course cloneCourse(final Integer courseId) {
        return cloneCourse(getCourse(courseId));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    Course getCourse(final Integer courseId) {
        Preconditions.checkNotNull(courseId);
        return repo.findOne(courseId);
    }
}
