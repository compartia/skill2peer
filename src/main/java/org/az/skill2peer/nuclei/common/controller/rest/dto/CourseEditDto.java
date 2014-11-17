package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.az.skill2peer.nuclei.common.model.CourseStatus;

public class CourseEditDto {

    private String description;

    private Integer id;

    //    private List<Lesson> lessons = new ArrayList<Lesson>();

    private String name;

    //    private SortedSet<Skill> skills;

    private CourseStatus status;

    private String summary;

    /**
     * comma-separated tags
     */
    private String tags;

    public CourseEditDto() {
        //   lessons.add(new Lesson());
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    //    public List<Lesson> getLessons() {
    // return lessons;
    //    }

    //    public Location getLocation() {
    //        return getFirstLesson().getLocation();
    //    }

    public String getName() {
        return name;
    }

    //
    //    public Schedule getSchedule() {
    //        return getFirstLesson().getSchedule();
    //    }

    //    public SortedSet<Skill> getSkills() {
    //        return skills;
    //    }

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

    //    public void setLessons(final List<Lesson> lessons) {
    //        this.lessons = lessons;
    //    }

    public void setName(final String name) {
        this.name = name;
    }

    //    public void setSchedule(final Schedule schedule) {
    //        getFirstLesson().setSchedule(schedule);
    //    }

    //    public void setSkills(final SortedSet<Skill> skills) {
    //        this.skills = skills;
    //    }

    public void setStatus(final CourseStatus status) {
        this.status = status;
    }

    public void setSummary(final String topic) {
        this.summary = topic;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

    //    private Lesson getFirstLesson() {
    //        if (lessons.isEmpty()) {
    //            lessons.add(new Lesson());
    //        }
    //        return lessons.get(0);
    //    }
}
