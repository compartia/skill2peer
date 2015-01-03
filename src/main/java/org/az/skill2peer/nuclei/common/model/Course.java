package org.az.skill2peer.nuclei.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import javax.persistence.CascadeType;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.az.skill2peer.nuclei.common.controller.dto.EventDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleInfoDto;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.az.skill2peer.nuclei.user.model.User;
import org.joda.time.DateTime;

import com.google.common.base.Preconditions;

/**
 * Course must have at least one lesson.
 *
 * @author Artem Zaborskiy
 *
 */
@Entity
@Table(name = "course")
@SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq")
@NamedQueries({ @NamedQuery(name = "Course.findAllByAuthor", query = "from Course where author.id=:authorId") })
public class Course extends BaseEntity<Integer> implements HasOwner {

    private static final long serialVersionUID = 3541638359681997928L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "author")
    private User author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "published_version_id")
    private Course publishedVersion;

    /**
     * editable copy. Must be null, if course is published
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "publishedVersion")
    private Course draft;

    @Size(max = 10000)
    @Column(name = "description")
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq")
    private Integer id;

    @Transient
    //XXX:
    private SortedSet<User> lectors;

    @OneToMany(targetEntity = Lesson.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @Valid
    private List<Lesson> lessons;

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

    public Course() {
    }

    public User getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Course getDraft() {
        return draft;
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

    public EventDto getNextEvent() {
        final ArrayList<Lesson> scs = new ArrayList<Lesson>(getLessons());
        Collections.sort(scs, Lesson.SCHEDULE_COMPARATOR);
        final Lesson firstLesson = scs.get(0);
        final EventDto nextEvent = firstLesson.getNextEvent();
        return nextEvent;
    }

    public Course getPublishedVersion() {
        return publishedVersion;
    }

    public ScheduleInfoDto getScheduleInfo() {
        final ScheduleInfoDto scheduleInfo = new ScheduleInfoDto();
        final ArrayList<Lesson> scs = new ArrayList<Lesson>(getLessons());
        Collections.sort(scs, Lesson.SCHEDULE_COMPARATOR);
        final Lesson firstLesson = scs.get(0);
        final Lesson lastLesson = scs.get(scs.size() - 1);

        final EventDto nextEvent = firstLesson.getNextEvent();

        scheduleInfo.setNextEvent(nextEvent);

        if (!isRecurrent()) {
            scheduleInfo.setStart(nextEvent.getStart());
            scheduleInfo.setEnd(lastLesson.getSchedule().getEnd());
        } else {
            scheduleInfo.setStart(firstLesson.getSchedule().getStart());
        }

        return scheduleInfo;
    }

    public Collection<Schedule> getSchedules() {
        final ArrayList<Schedule> ret = new ArrayList<Schedule>();
        for (final Lesson l : lessons) {
            ret.add(l.getSchedule());
        }
        return ret;
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

    public Integer getTotalDuration() {
        if (isRecurrent()) {
            return null;
        } else {
            int totalDuration = 0;
            for (final Lesson l : lessons) {
                final Integer duration = l.getDuration();
                if (duration != null) {
                    totalDuration += duration;
                } else {
                    return null;
                }
            }
            return totalDuration;
        }
    }

    public List<DayEventsDto> getWeekSchedule() {
        final ArrayList<Lesson> scs = new ArrayList<Lesson>(getLessons());
        Collections.sort(scs, CalendarUtils.LESSON_COMPARATOR);

        final Lesson firstLesson = scs.get(0);
        final DateTime nextEvent = firstLesson.getSchedule().getNextEvent().getStart();

        if (nextEvent != null) {
            return getWeekSchedule(nextEvent);
        } else {
            /**
             * empty week schedule
             */
            return CalendarUtils.makeWeekPattern();
        }
    }

    public List<DayEventsDto> getWeekSchedule(final DateTime weekStart) {
        Preconditions.checkNotNull(weekStart);

        final List<EventDto> allEvents = new ArrayList<EventDto>();
        for (final Lesson lesson : lessons) {
            final List<EventDto> eventsWithinWeek = lesson.getEventsWithinWeek(weekStart);
            allEvents.addAll(eventsWithinWeek);
        }

        return CalendarUtils.groupEventsInWeek(allEvents);

    }

    public boolean isPast() {
        for (final Lesson l : lessons) {
            if (!l.isPast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isRecurrent() {
        for (final Lesson l : lessons) {
            if (l.isRecurrent()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSingle() {
        return lessons.size() == 1;
    }

    public boolean isSingleLesson() {
        return this.lessons.size() == 1;
    }

    @Override
    @PrePersist
    public void prePersist() {
        Preconditions.checkState(lessons != null && lessons.size() >= 1);

    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDraft(final Course draft) {
        this.draft = draft;
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

    public void setPublishedVersion(final Course publishedVersion) {
        this.publishedVersion = publishedVersion;
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

    private Lesson getFirstLesson() {
        if (lessons.isEmpty()) {
            lessons.add(new Lesson());
        }
        return lessons.get(0);
    }
}
