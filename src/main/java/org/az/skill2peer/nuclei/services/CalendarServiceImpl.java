package org.az.skill2peer.nuclei.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.UserInfoDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.Enrollment;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public class CalendarServiceImpl implements CalendarService {
    @PersistenceContext
    EntityManager em;

    @Autowired
    Mapper mapper;

    @Override
    public List<UserInfoDto> getEnrolledUsers(final Integer courseId) {
        final List<Enrollment> resultList = getEnrollments(courseId);

        final List<UserInfoDto> ret = new ArrayList<UserInfoDto>();
        for (final Enrollment e : resultList) {
            final UserInfoDto us = new UserInfoDto();
            mapper.map(e.getUser(), us);
            ret.add(us);
        }

        return ret;
    }

    public List<EventDto> getEvents() {
        return getEvents(SecurityUtil.getCurrentUser().getId());
    }

    @Override
    public List<EventDto> getEvents(final Integer userId) {
        // TODO Auto-generated method stub
        return null;
    }

    private List<Enrollment> getEnrollments(final Integer courseId) {
        Preconditions.checkNotNull(courseId);
        final Course course = em.find(Course.class, courseId);
        Preconditions.checkNotNull(course);

        final List<Enrollment> resultList = em
                .createNamedQuery("Enrollment.findByCourseId", Enrollment.class)
                .setParameter("courseId", courseId)
                .getResultList();

        //Adding author as enrolled
        final Enrollment ve = new Enrollment();
        ve.setCourse(course);
        ve.setUser(course.getAuthor());//XXX: get teachers
        resultList.add(ve);
        return resultList;
    }

}
