package org.az.skill2peer.nuclei.services;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseFavorite;
import org.az.skill2peer.nuclei.common.model.CourseStatus;
import org.az.skill2peer.nuclei.common.model.HasOwner;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    Mapper mapper;

    @Resource
    CourseRepository repo;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = false)
    public Course createCourse(final CourseEditDto courseDto) {
        final Course course = new Course();
        mapper.map(courseDto, course);

        course.setAuthor(getCurrentUser());
        course.setId(null);
        course.setStatus(CourseStatus.DRAFT);

        em.persist(course);
        em.flush();
        return course;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCourse(final Integer courseId) {
        final Course draft = getCourse(courseId);
        assertNotPublished(draft);
        em.remove(draft);
        em.flush();
    }

    //    @Override
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
    public CourseEditDto getEditableCourse(final Integer courseId) {
        final Course editableCourse = editCourse(courseId);
        final CourseEditDto editableDto = new CourseEditDto();
        mapper.map(editableCourse, editableDto);
        return editableDto;
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
            em.persist(clonedObject);
            return clonedObject.getId();
        } else {
            //  copy data to the published one;

            mapper.map(draft, publishedVersion, "course-copy-publish");
            publishedVersion.setStatus(CourseStatus.PUBLISHED);
            publishedVersion.setDraft(null);
            publishedVersion.setPublishedVersion(null);

            em.remove(draft);
            em.merge(publishedVersion);
            em.flush();
            return publishedVersion.getId();
        }

    }

    @Override
    @Transactional(readOnly = false)
    public Course updateCourse(final CourseEditDto courseDto) {
        LOGGER.debug("updating course " + courseDto.getId());

        Assert.notNull(courseDto);

        final Course course = getCourse(courseDto.getId());
        Assert.notNull(course);
        assertCurrentUserHasPermission(course);
        assertNotPublished(course);

        mapper.map(courseDto, course);
        course.setStatus(CourseStatus.DRAFT);
        em.merge(course);
        em.flush();
        return course;
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
            //em.detach(entity);
            final Course clonedObject = cloneCourse(course);
            course.setDraft(clonedObject);
            clonedObject.setId(null);
            clonedObject.setStatus(CourseStatus.DRAFT);
            clonedObject.setAuthor(getCurrentUser());

            clonedObject.setPublishedVersion(course);
            em.persist(clonedObject);
            em.merge(course);
            em.flush();

            return clonedObject;
        }
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
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
        return em.find(Course.class, courseId);
    }
}
