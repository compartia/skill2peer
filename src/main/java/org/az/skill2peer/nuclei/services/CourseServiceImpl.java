package org.az.skill2peer.nuclei.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseFavorite;
import org.az.skill2peer.nuclei.common.model.CourseStatus;
import org.az.skill2peer.nuclei.common.model.HasOwner;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
import org.dozer.Mapper;
import org.hibernate.internal.util.SerializationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;

/**
*
* @author Artem Zaborskiy
*
*/
@Transactional
@Component
public class CourseServiceImpl implements CourseService, CourseAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    Mapper mapper;

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
        return course;
    }

    @Override
    @Transactional(readOnly = false)
    public Course editCourse(final Integer courseId) {
        final Course course = em.find(Course.class, courseId);
        Assert.notNull(course);
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
    public CourseMetaDataDto getCourseInfo(final Integer id) {
        final CourseMetaDataDto dto = new CourseMetaDataDto();
        final Course c = em.find(Course.class, id);
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

            return publishedVersion.getId();
        }

    }

    @Override
    @Transactional(readOnly = false)
    public Course updateCourse(final CourseEditDto courseDto) {
        LOGGER.debug("updating course " + courseDto.getId());

        Assert.notNull(courseDto);
        Assert.notNull(courseDto.getId());
        final Course course = em.find(Course.class, courseDto.getId());
        Assert.notNull(course);
        assertCurrentUserHasPermission(course);
        assertNotPublished(course);

        mapper.map(courseDto, course);
        course.setStatus(CourseStatus.DRAFT);
        em.merge(course);
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

    private Course cloneCourse(final Course course) {
        final Course clonedObject = (Course)SerializationHelper.clone(course);
        clonedObject.setAuthor(getCurrentUser());
        return clonedObject;
    }

    private Course getCourse(final Integer courseId) {
        Preconditions.checkNotNull(courseId);
        final Course course = em.find(Course.class, courseId);
        return course;
    }

    private User getCurrentUser() {
        final User user = em.find(User.class, SecurityUtil.getCurrentUser().getId());
        Assert.notNull(user);
        return user;
    }

    Course makeOrGetDraft(final Course course) {
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

            return clonedObject;
        }
    }
}
