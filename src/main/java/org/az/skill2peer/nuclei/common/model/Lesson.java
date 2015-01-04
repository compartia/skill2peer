package org.az.skill2peer.nuclei.common.model;

import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.az.skill2peer.nuclei.common.controller.dto.EventDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;

import com.google.common.base.Preconditions;

/**
 * a course consists of lessons
 *
 * @author Artem Zaborskiy
 *
 */
@Entity
@Table(name = "lesson")
@SequenceGenerator(name = "lesson_id_seq", sequenceName = "lesson_id_seq")
public class Lesson extends BaseEntity<Integer> {

    private static final long serialVersionUID = 765205335817537834L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_id_seq")
    private Integer id;

    @Valid
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotNull
    @Valid
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @Column(name = "description")
    @Size(max = 10000)
    private String description;

    @Column(name = "name")
    @Size(max = 500)
    private String name;

    public static final Comparator<Lesson> SCHEDULE_COMPARATOR = new Comparator<Lesson>() {
        @Override
        public int compare(final Lesson s1, final Lesson s2) {
            final Schedule schedule1 = s1.getSchedule();
            final Schedule schedule2 = s2.getSchedule();
            if (schedule1 == schedule2) {
                return 0;
            }
            if (schedule1 == null) {
                return -1;
            }
            if (schedule2 == null) {
                return 1;
            }
            final DateTime nextEvent1 = schedule1.getNextEvent().getStart();
            final DateTime nextEvent2 = schedule2.getNextEvent().getStart();

            if (nextEvent1 == nextEvent2) {
                return 0;
            }

            if (nextEvent1 == null) {
                return -1;
            }
            if (nextEvent2 == null) {
                return 1;
            }
            return nextEvent1.compareTo(nextEvent2);
        }
    };

    public Course getCourse() {
        return course;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return schedule.getDuration();
    }

    /*    methods   */

    public List<EventDto> getEventsWithinWeek(final DateTime week) {
        Preconditions.checkNotNull(week);

        final List<EventDto> eventsWithinPeriod = schedule.getEventsWithinWeek(week);
        for (final EventDto e : eventsWithinPeriod) {
            /**
             * applying events names
             */
            e.setName(getName());
        }
        return eventsWithinPeriod;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public EventDto getNextEvent() {
        EventDto nextEvent = schedule.getNextEvent();
        if (nextEvent == null) {
            //in case schedule is undefined
            nextEvent = new EventDto();
        }
        nextEvent.setName(this.getName());
        return nextEvent;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Deprecated
    public List<DayEventsDto> getWeekSchedule(final DateTime week) {
        final List<EventDto> eventsWithinPeriod = getEventsWithinWeek(week);
        return CalendarUtils.groupEventsInWeek(eventsWithinPeriod);
    }

    public boolean isPast() {
        return this.getSchedule().isPast();
    }

    public boolean isRecurrent() {
        return this.getSchedule().isRecurrent();
    }

    public void setCourse(final Course course) {
        this.course = course;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;
    }
}
