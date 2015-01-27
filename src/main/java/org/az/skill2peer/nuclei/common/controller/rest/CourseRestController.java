package org.az.skill2peer.nuclei.common.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoListItemDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LocationDto;
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

    @Autowired
    CourseService courseService;

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @ResponseBody
    public CourseEditDto edit(@PathVariable("id") final Integer id) {
        return courseService.getEditableCourse(id);
    }

    @RequestMapping(value = "/{id}/favorite", method = RequestMethod.POST)
    // @ResponseBody
    public void favorite(@PathVariable("id") final Integer id) {
        courseService.favorite(id);
    }

    @RequestMapping(value = "/availableLocations", method = RequestMethod.GET)
    @ResponseBody
    public List<LocationDto> getAvailableLocations() {
        return courseService.getAvailableLocations();
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
