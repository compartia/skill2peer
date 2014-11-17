package org.az.skill2peer.nuclei.common.controller.rest;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/courses", headers = { "Accept=application/json" })
public class CourseRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRestController.class);

    protected static final String VIEW_NAME_HOMEPAGE = "index";

    @PersistenceContext
    EntityManager em;

    @Autowired
    Mapper mapper;

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{id}/favorite", method = RequestMethod.POST)
    @ResponseBody
    public void favorite(@PathVariable("id") final Integer id) {
        final Integer userId = SecurityUtil.getCurrentUser().getId();

        final List<CourseFavorite> resultList = getCourseFavorites(id, userId);

        if (resultList.isEmpty()) {
            final CourseFavorite cf = new CourseFavorite();
            cf.setUserId(userId);
            cf.setCourseId(id);
            em.persist(cf);
        } else {
            em.remove(resultList.get(0));
        }

    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public CourseEditDto save(@RequestBody final CourseEditDto courseDto) {
        Course course;
        LOGGER.debug("updating course " + courseDto.getId());
        if (courseDto.getId() != null) {
            //update existing
            course = em.find(Course.class, courseDto.getId());
            assertCurrentUserHasPermission(course);
            Assert.state(course.getStatus() != CourseStatus.PUBLISHED, "Can not modify published course");

            mapper.map(courseDto, course);
            course.setStatus(CourseStatus.DRAFT);
            em.merge(course);
        } else {
            course = new Course();
            mapper.map(courseDto, course);
            course.setAuthor(em.find(User.class, SecurityUtil.getCurrentUser().getId()));
            course.setId(null);
            course.setStatus(CourseStatus.DRAFT);
            em.persist(course);
        }
        final CourseEditDto ret = new CourseEditDto();
        mapper.map(course, ret);
        return ret;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CourseMetaDataDto showHomePage(@PathVariable("id") final Integer id) {
        final CourseMetaDataDto dto = new CourseMetaDataDto();
        final Course c = em.find(Course.class, id);
        dto.setId(c.getId());
        dto.setFavorited(!getCourseFavorites(id, SecurityUtil.getCurrentUser().getId()).isEmpty());
        return dto;
    }

    private void assertCurrentUserHasPermission(final HasOwner obj) {
        //XXX: move to a service
    }

    private List<CourseFavorite> getCourseFavorites(final Integer courseId, final Integer userId) {
        final List<CourseFavorite> resultList = em
                .createNamedQuery("CourseFavorite.findByUserAndCourse", CourseFavorite.class)
                .setParameter("userId", userId)
                .setParameter("courseId", courseId)
                .getResultList();
        return resultList;
    }
}
