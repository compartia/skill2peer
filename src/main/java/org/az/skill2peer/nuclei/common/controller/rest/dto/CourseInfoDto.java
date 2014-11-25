package org.az.skill2peer.nuclei.common.controller.rest.dto;

import java.util.SortedSet;

import org.az.skill2peer.nuclei.common.model.Skill;

public class CourseInfoDto {

    private UserInfoDto author;

    private String description;

    private Integer id;

    private String name;

    private SortedSet<Skill> skills;

    private String summary;

    public UserInfoDto getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SortedSet<Skill> getSkills() {
        return skills;
    }

    public String getSummary() {
        return summary;
    }

    public void setAuthor(final UserInfoDto author) {
        this.author = author;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    //    private SortedSet<User> lectors;
    //
    //    private List<Lesson> lessons = new ArrayList<Lesson>();

    public void setName(final String name) {
        this.name = name;
    }

    public void setSkills(final SortedSet<Skill> skills) {
        this.skills = skills;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }
}
