package org.az.skill2peer.nuclei.common.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.az.skill2peer.nuclei.common.controller.dto.EventDto;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.common.base.Preconditions;
import com.google.ical.compat.jodatime.DateTimeIterator;
import com.google.ical.values.DateValue;
import com.google.ical.values.RRule;

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

    //    @Column(name = "duration")
    //    private Integer duration;

    @Column(name = "repeat")
    private String iCalString;

    /* methods */

    public Integer getDuration() {
        if (start == null || end == null) {
            return null;
        }
        return Minutes.minutesBetween(start, end).getMinutes();
    }

    public DateTime getEnd() {
        return end;
    }

    public List<EventDto> getEventsWithinPeriod(final DateTime from, final DateTime to) {

        final List<EventDto> result = new ArrayList<EventDto>();
        if (getStart() != null) {

            final DateTimeZone timeZone = DateTimeZone.UTC;

            if (StringUtils.isEmpty(getiCalString())) {
                /**
                 * recurrence is not defined, make single event if it is in range
                 */
                if (getStart().isAfter(from) && getStart().isBefore(to)) {
                    result.add(CalendarUtils.buidEventDto(getStart(), getEnd(), timeZone));
                }

            } else {

                /**
                 * this is a recurrent event
                 */

                try {

                    final DateTimeIterator iterator = CalendarUtils.getProperIterator(from,
                            this.getiCalString(),
                            this.getStart(),
                            timeZone);

                    while (iterator.hasNext()) {

                        final DateTime dt = iterator.next().withZone(timeZone);

                        if (dt.isBefore(to)) {

                            DateTime eventEnd = null;
                            if (getDuration() != null) {
                                eventEnd = dt.plusMinutes(getDuration());
                            }
                            result.add(CalendarUtils.buidEventDto(dt, eventEnd, timeZone));

                        } else {
                            break;
                        }
                    }

                } catch (final ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        CalendarUtils.withTimeZone(result, DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone()));
        return result;
    }

    public List<EventDto> getEventsWithinWeek(final DateTime week) {
        Preconditions.checkNotNull(week);
        final DateTime weekStart = week.withDayOfWeek(DateTimeConstants.MONDAY).withMillisOfDay(0);
        final DateTime weekEnd = week.withDayOfWeek(DateTimeConstants.SUNDAY).withHourOfDay(23).withMinuteOfHour(59);

        return getEventsWithinPeriod(weekStart, weekEnd);
    }

    public String getiCalString() {
        return iCalString;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public EventDto getNextEvent() {
        return CalendarUtils.getNextEvent(this);
    }

    public DateTime getStart() {
        return start;
    }

    public boolean isPast() {
        final DateTime now = DateTime.now();
        if (isRecurrent()) {

            try {
                final RRule rRule = new RRule(getiCalString());
                final DateValue until = rRule.getUntil();
                if (until == null) {
                    //forever
                    return false;
                }
                return until.compareTo(CalendarUtils.makeDateValue(now)) < 0;
            } catch (final ParseException e) {
                throw new RuntimeException(e);
            }

        } else {
            if (getEnd() != null) {
                return getEnd().isBefore(DateTime.now());
            } else {
                return false;
            }
        }
    }

    public boolean isRecurrent() {
        return StringUtils.isNotBlank(getiCalString());
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

}
