package org.az.skill2peer.nuclei.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.TimeFormat;

import com.google.ical.compat.jodatime.DateTimeIterable;
import com.google.ical.compat.jodatime.DateTimeIteratorFactory;

public class CalendarUtils {
    public static String getDurationAsString(final Locale locale, final Date from, final Date to) {

        if (from == null || to == null) {
            return "";
        }

        final PrettyTime p = new PrettyTime(locale);
        p.setReference(from);

        final List<Duration> durations = p.calculatePreciseDuration(to);

        String result = null;

        final StringBuilder builder = new StringBuilder();
        Duration duration = null;
        TimeFormat format = null;
        for (int i = 0; i < durations.size(); i++) {
            duration = durations.get(i);
            format = p.getFormat(duration.getUnit());

            final boolean isLast = (i == durations.size() - 1);
            if (!isLast) {
                builder.append(format.formatUnrounded(duration));
                builder.append(" ");
            }
            else {
                builder.append(format.format(duration));
            }
        }
        result = builder.toString();

        return result;

    }

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
