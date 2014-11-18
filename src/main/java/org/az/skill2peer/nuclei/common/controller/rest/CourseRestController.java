package org.az.skill2peer.nuclei.common.controller.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseFavorite;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.services.CourseService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @PersistenceContext
    EntityManager em;

    @Autowired
    Mapper mapper;

    @Autowired
    CourseService courseService;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CourseMetaDataDto getCourseById(@PathVariable("id") final Integer id) {
        final CourseMetaDataDto dto = new CourseMetaDataDto();
        final Course c = em.find(Course.class, id);
        dto.setId(c.getId());
        dto.setFavorited(!getCourseFavorites(id, SecurityUtil.getCurrentUser().getId()).isEmpty());
        return dto;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public CourseEditDto save(@RequestBody final CourseEditDto courseDto) {
        Course course;

        if (courseDto.getId() != null) {
            //update existing
            course = courseService.updateCourse(courseDto);
        } else {
            course = courseService.createCourse(courseDto);
        }

        final CourseEditDto ret = new CourseEditDto();
        mapper.map(course, ret);
        return ret;
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
