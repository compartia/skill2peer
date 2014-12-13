package org.az.skill2peer.nuclei.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

import com.google.ical.compat.jodatime.LocalDateIterable;
import com.google.ical.compat.jodatime.LocalDateIteratorFactory;

public class CalendarUtils {

    public static LocalDate getNextEvent(final Schedule schedule) {

        try {
            LocalDateIterable dateIterable;
            dateIterable = LocalDateIteratorFactory.createLocalDateIterable(schedule
                    .getiCalString(),
                    schedule.getStart().toLocalDate(),
                    false);

            final LocalDate next = dateIterable.iterator().next();
            return next;
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static Period getPeriodToNextEvent(final Schedule schedule) {
        final LocalDate nextEvent = getNextEvent(schedule);
        final Period period = Period.fieldDifference(LocalDate.now(), nextEvent);
        return period;
    }

    public static List<EventDto> getWeekSchedule(final Set<Schedule> schedules) {
        final ArrayList<Schedule> scs = new ArrayList<Schedule>(schedules);

        Collections.sort(scs, SCHEDULE_COMPARATOR);
        //        final LocalDate firstEvent = scs.get(0).getNextEvent();

        final ArrayList<EventDto> ret = new ArrayList<EventDto>();
        for (final Schedule sc : scs) {
            final LocalDate nextEvent = sc.getNextEvent();
            final EventDto eventDto = new EventDto();

            final LocalDateTime nextEventDayTime = new LocalDateTime(nextEvent);
            nextEventDayTime.withHourOfDay(sc.getStart().getHourOfDay());
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
