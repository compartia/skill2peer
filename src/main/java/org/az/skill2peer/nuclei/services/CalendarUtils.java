package org.az.skill2peer.nuclei.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;

import com.google.ical.compat.jodatime.DateTimeIterable;
import com.google.ical.compat.jodatime.DateTimeIteratorFactory;

public class CalendarUtils {

    public static DateTime getNextEvent(final Schedule schedule) {

        try {
            DateTimeIterable dateIterable;

            final String rdata = schedule.getiCalString();
            final ReadableDateTime start = schedule.getStart();
            final DateTimeZone tzid = schedule.getStart().getZone();
            dateIterable = DateTimeIteratorFactory.createDateTimeIterable(
                    rdata, start, tzid, false);

            //                    .createLocalDateIterable(schedule
            //                    .getiCalString(),
            //                    schedule.getStart().toLocalDate(),
            //                    false);

            final ReadableDateTime next = dateIterable.iterator().next();
            return next.toDateTime();
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static Period getPeriodToNextEvent(final Schedule schedule) {
        final ReadableDateTime nextEvent = getNextEvent(schedule);
        final Period period = Period.fieldDifference(LocalDate.now(), nextEvent.toDateTime().toLocalDateTime());
        return period;
    }

    public static List<EventDto> getWeekSchedule(final Set<Schedule> schedules) {
        final ArrayList<Schedule> scs = new ArrayList<Schedule>(schedules);

        Collections.sort(scs, SCHEDULE_COMPARATOR);
        //        final LocalDate firstEvent = scs.get(0).getNextEvent();

        final ArrayList<EventDto> ret = new ArrayList<EventDto>();
        for (final Schedule sc : scs) {
            final ReadableDateTime nextEvent = sc.getNextEvent();
            final EventDto eventDto = new EventDto();

            final LocalDateTime nextEventDayTime = new LocalDateTime(nextEvent);
            eventDto.setStart(nextEventDayTime);
            eventDto.setDayShortName(DateTimeFormat.forPattern("EE").print(nextEventDayTime));
            ret.add(eventDto);
        }
        return ret;

    }

    public static final Comparator<Schedule> SCHEDULE_COMPARATOR = new Comparator<Schedule>() {
        @Override
        public int compare(final Schedule s1, final Schedule s2) {
            return s1.getNextEvent().compareTo(s2.getNextEvent());
        }
    };
}
