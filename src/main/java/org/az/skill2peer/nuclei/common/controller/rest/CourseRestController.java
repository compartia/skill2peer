package org.az.skill2peer.nuclei.common.controller.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoListItemDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.model.CourseFavorite;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * XXX: move to service layer
     */
    @Deprecated
    @PersistenceContext
    EntityManager em;

    @Autowired
    CourseService courseService;

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @ResponseBody
    public CourseEditDto edit(@PathVariable("id") final Integer id) {
        return courseService.getEditableCourse(id);
    }

    //    @Transactional(readOnly = false)
    @RequestMapping(value = "/{id}/favorite", method = RequestMethod.POST)
    @ResponseBody
    public void favorite(@PathVariable("id") final Integer id) {
        final Integer userId = SecurityUtil.getCurrentUser().getId();

        final List<CourseFavorite> resultList = courseService.getCourseFavorites(id, userId);

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
        return courseService.getCourseInfo(id);
    }

    @RequestMapping(value = "/myCourses", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseInfoListItemDto> getMyCourses() {
        final List<CourseInfoListItemDto> resultList = courseService.getMyCourses();
        return resultList;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public CourseEditDto save(@RequestBody @Valid final CourseEditDto courseDto) {
        LOGGER.debug("course saved " + courseDto.getId());
        CourseEditDto course;

        if (courseDto.getId() != null) {
            //update existing
            course = courseService.updateCourse(courseDto);
        } else {
            course = courseService.createCourse(courseDto);
        }

        return course;
    }

}
