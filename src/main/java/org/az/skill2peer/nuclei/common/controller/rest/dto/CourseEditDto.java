package org.az.skill2peer.nuclei.common.controller.rest.dto;

import java.util.ArrayList;
import java.util.List;

import org.az.skill2peer.nuclei.common.model.CourseStatus;

public class CourseEditDto {

    private String description;

    private Integer id;

    private List<LessonEditDto> lessons = new ArrayList<LessonEditDto>();

    private String name;

    private CourseStatus status;

    private String summary;

    /**
     * comma-separated tags
     */
    private String tags;

    //    private SortedSet<Skill> skills;

    public CourseEditDto() {
        //   lessons.add(new Lesson());
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public List<LessonEditDto> getLessons() {
        return lessons;
    }

    public String getName() {
        return name;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public String getTags() {
        return tags;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLessons(final List<LessonEditDto> lessons) {
        this.lessons = lessons;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStatus(final CourseStatus status) {
        this.status = status;
    }

    public void setSummary(final String topic) {
        this.summary = topic;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

}
