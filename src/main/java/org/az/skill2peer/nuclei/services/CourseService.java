package org.az.skill2peer.nuclei.services;

import java.util.List;

import org.az.skill2peer.nuclei.common.controller.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoListItemDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseMetaDataDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LocationDto;
import org.az.skill2peer.nuclei.common.model.CourseFavorite;

public interface CourseService {

    CourseEditDto createCourse(CourseEditDto courseDto);

    void deleteCourse(final Integer courseId);

    List<LocationDto> getAvailableLocations();

    List<CourseFavorite> getCourseFavorites(Integer courseId, Integer userId);

    CourseInfoDto getCourseFullInfo(Integer id);

    CourseMetaDataDto getCourseInfo(Integer id);

    CourseEditDto getEditableCourse(Integer courseId);

    List<CourseInfoListItemDto> getMyCourses();

    CourseEditDto updateCourse(CourseEditDto courseDto);

}
