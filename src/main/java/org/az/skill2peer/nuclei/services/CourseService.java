package org.az.skill2peer.nuclei.services;

import java.util.List;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseFavorite;

public interface CourseService {

    Course createCourse(CourseEditDto courseDto);

    void deleteCourse(final Integer courseId);

    List<CourseFavorite> getCourseFavorites(Integer courseId, Integer userId);

    CourseInfoDto getCourseFullInfo(Integer id);

    CourseMetaDataDto getCourseInfo(Integer id);

    CourseEditDto getEditableCourse(Integer courseId);

    Course updateCourse(CourseEditDto courseDto);

}
