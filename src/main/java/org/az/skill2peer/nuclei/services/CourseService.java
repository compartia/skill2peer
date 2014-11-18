package org.az.skill2peer.nuclei.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.model.Course;
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

@Transactional
@Component
public class CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    Mapper mapper;

    @PersistenceContext
    EntityManager em;

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

    @Transactional(readOnly = false)
    public Course updateCourse(final CourseEditDto courseDto) {
        LOGGER.debug("updating course " + courseDto.getId());
        Assert.notNull(courseDto);
        Assert.notNull(courseDto.getId());
        final Course course = em.find(Course.class, courseDto.getId());
        Assert.notNull(course);
        assertCurrentUserHasPermission(course);
        Assert.state(course.getStatus() != CourseStatus.PUBLISHED, "Can not modify published course");

        mapper.map(courseDto, course);
        course.setStatus(CourseStatus.DRAFT);
        em.merge(course);
        return course;
    }

    private void assertCurrentUserHasPermission(final HasOwner obj) {
        //XXX: move to a service
        //XXX: make it aspect
    }

    private User getCurrentUser() {
        final User user = em.find(User.class, SecurityUtil.getCurrentUser().getId());
        return user;
    }

    Course makeOrGetDraft(final Course course) {
        if (course.getDraft() != null) {
            return course.getDraft();
        } else {
            //make deep copy;
            //em.detach(entity);
            final Course clonedObject = (Course)SerializationHelper.clone(course);
            course.setDraft(clonedObject);
            clonedObject.setId(null);
            clonedObject.setStatus(CourseStatus.DRAFT);
            clonedObject.setAuthor(getCurrentUser());

            em.merge(course);

            return clonedObject;
        }
    }
}
