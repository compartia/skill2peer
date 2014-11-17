package org.az.skill2peer.nuclei.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.az.skill2peer.nuclei.user.model.User;

@Entity
@Table(name = "course")
@SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq")
public class Course extends BaseEntity<Integer> implements HasOwner {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "author")
    private User author;

    @Size(max = 10000)
    @Column(name = "description")
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq")
    private Integer id;

    @Transient
    //XXX:
    private SortedSet<User> lectors;

    @Transient
    //XXX:
    private List<Lesson> lessons = new ArrayList<Lesson>();

    @Size(max = 160)
    @Column(name = "title")
    private String name;

    @Transient
    //XXX:
    private SortedSet<Skill> skills;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Size(max = 450)
    @Column(name = "summary")
    private String summary;

    @Transient
    //XXX:
    private SortedSet<Tag> tags;

    public Course() {
        lessons.add(new Lesson());
    }

    public User getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public SortedSet<User> getLectors() {
        return lectors;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public Location getLocation() {
        return getFirstLesson().getLocation();
    }

    public String getName() {
        return name;
    }

    public Schedule getSchedule() {
        return getFirstLesson().getSchedule();
    }

    public SortedSet<Skill> getSkills() {
        return skills;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public SortedSet<Tag> getTags() {
        return tags;
    }

    //    public boolean isFavorited(final User user) {
    //    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLectors(final SortedSet<User> lectors) {
        this.lectors = lectors;
    }

    public void setLessons(final List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSchedule(final Schedule schedule) {
        getFirstLesson().setSchedule(schedule);
    }

    public void setSkills(final SortedSet<Skill> skills) {
        this.skills = skills;
    }

    public void setStatus(final CourseStatus status) {
        this.status = status;
    }

    public void setSummary(final String topic) {
        this.summary = topic;
    }

    public void setTags(final SortedSet<Tag> tags) {
        this.tags = tags;
    }

    private Lesson getFirstLesson() {
        if (lessons.isEmpty()) {
            lessons.add(new Lesson());
        }
        return lessons.get(0);
    }
}