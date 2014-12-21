package org.az.skill2peer.nuclei.common.model;

import java.text.ParseException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.ical.compat.jodatime.DateTimeIterable;
import com.google.ical.compat.jodatime.DateTimeIterator;
import com.google.ical.compat.jodatime.DateTimeIteratorFactory;

@Entity
@Table(name = "schedule")
@SequenceGenerator(name = "schedule_id_seq", sequenceName = "schedule_id_seq")
public class Schedule extends BaseEntity<Integer> {

    private static final long serialVersionUID = 4873701983319765707L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_seq")
    private Integer id;

    @Column(name = "start_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime start;

    @Column(name = "end_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime end;

    @Column(name = "repeat")
    private String iCalString;

    //    @Column(name = "duration")
    //    private Integer duration;

    public Integer getDuration() {
        if (start == null || end == null) {
            return null;
        }
        return Minutes.minutesBetween(start, end).getMinutes();
    }

    /* methods */

    public DateTime getEnd() {
        return end;
    }

    public String getiCalString() {
        return iCalString;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public DateTime getNextEvent() {
        return CalendarUtils.getNextEvent(this);
    }

    public DateTime getStart() {
        return start;
    }

    public List<DayEventsDto> getWeekSchedule() {
        DateTime nextEvent = getNextEvent();
        if (nextEvent == null) {
            nextEvent = DateTime.now();
        }
        return getWeekSchedule(nextEvent);
    }

    public List<DayEventsDto> getWeekSchedule(final DateTime week) {
        final List<DayEventsDto> events = CalendarUtils.makeWeekPattern(week);

        final DateTime weekStart = events.get(0).getFirst().getStart().withMillisOfDay(0);
        final DateTime weekEnd = events.get(6).getFirst().getStart().withHourOfDay(23).withMinuteOfHour(59);

        if (getStart() == null) {
            /**
             * schedule is not defined
             */
            return events;
        } else {
            //            final TimeZone tz = ;
            final DateTimeZone timeZone = DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone());

            if (StringUtils.isEmpty(getiCalString())) {
                /**
                 * recurrence is not defined, make single event
                 */
                final DayEventsDto eventDto = events.get(getStart().getDayOfWeek() - 1);
                eventDto.getFirst().setEnd(getEnd().withZone(timeZone));
                eventDto.getFirst().setStart(getStart().withZone(timeZone));
                return events;
            } else {

                /**
                 * recurrence is defined
                 */

                try {

                    final DateTime iteratorStart = applyTime(weekStart);

                    final DateTimeIterable dateIterable = DateTimeIteratorFactory.createDateTimeIterable(
                            getiCalString(),
                            iteratorStart,
                            timeZone,
                            true);

                    final DateTimeIterator iterator = dateIterable.iterator();
                    while (iterator.hasNext()) {

                        final DateTime dt = iterator.next().withZone(timeZone);

                        if (dt.isBefore(weekEnd)) {

                            final DayEventsDto dayEvents = events.get(dt.getDayOfWeek() - 1);
                            final EventDto event = dayEvents.getFirst();

                            event.setStart(dt);
                            event.setEnd(dt.plusMinutes(getDuration()));

                        } else {
                            break;
                        }
                    }

                } catch (final ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            return events;
        }
    }

    public void setEnd(final DateTime end) {
        this.end = end;
    }

    public void setiCalString(final String iCalString) {
        this.iCalString = iCalString;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setStart(final DateTime start) {
        this.start = start;
    }

    private DateTime applyTime(final DateTime weekStart) {
        final DateTime iteratorStart = getStart().withDate(weekStart.getYear(),
                weekStart.getMonthOfYear(),
                weekStart.getDayOfMonth());
        return iteratorStart;
    }
}
