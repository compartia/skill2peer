package org.az.skill2peer.nuclei.common.model;

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

import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
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

    @NotNull
    @Valid
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @Column(name = "description")
    @Size(max = 10000)
    private String description;

    /*    methods   */

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return schedule.getDuration();
    }

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
        return "XXX: Lesson.getName() is undefined";
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Deprecated
    public List<DayEventsDto> getWeekSchedule(final DateTime week) {
        final List<EventDto> eventsWithinPeriod = getEventsWithinWeek(week);
        return CalendarUtils.groupEventsInWeek(eventsWithinPeriod);
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

    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;
    }
}
