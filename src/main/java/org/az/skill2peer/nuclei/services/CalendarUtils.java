package org.az.skill2peer.nuclei.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.impl.DurationImpl;
import org.ocpsoft.prettytime.units.Hour;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Minute;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.ical.compat.jodatime.DateTimeIterable;
import com.google.ical.compat.jodatime.DateTimeIteratorFactory;

public class CalendarUtils {
    public static String formatHoursDuration(final Locale locale, final int minutes) {

        final int hoursNum = (minutes - minutes % 60) / 60;
        final int minutesNum = minutes % 60;

        final PrettyTime p = new PrettyTime(locale);

        final DurationImpl durationHrs = new DurationImpl();
        durationHrs.setQuantity(hoursNum);
        durationHrs.setUnit(p.getUnit(Hour.class));

        final TimeFormat hrsFormat = p.getFormat(durationHrs.getUnit());
        String result = hrsFormat.formatUnrounded(durationHrs);

        if (minutesNum > 0) {
            final DurationImpl durationMins = new DurationImpl();
            durationMins.setQuantity(minutesNum);
            durationMins.setUnit(p.getUnit(Minute.class));
            final TimeFormat minsFormat = p.getFormat(durationMins.getUnit());
            result = result + " " + minsFormat.formatUnrounded(durationMins);
        }

        return result;

    }

    public static String formatPeriod(final Locale locale, final Date from, final Date to) {

        if (from == null || to == null) {
            return "";
        }

        final PrettyTime p = new PrettyTime(from, locale);
        final List<Duration> durations = p.calculatePreciseDuration(to);

        final StringBuilder builder = new StringBuilder();
        Duration duration = null;
        TimeFormat format = null;
        for (int i = 0; i < durations.size(); i++) {
            duration = durations.get(i);
            format = p.getFormat(duration.getUnit());

            final boolean isLast = (i == durations.size() - 1);
            if (!(duration.getUnit() instanceof JustNow)) {
                builder.append(format.formatUnrounded(duration));
                if (!isLast) {
                    builder.append(" ");
                }
            }

        }

        return builder.toString();
    }

    public static DateTime getNextEvent(final Schedule schedule) {
        final String repeatRules = schedule.getiCalString();

        if (StringUtils.isEmpty(repeatRules) || schedule.getStart() == null) {
            return schedule.getStart();
        }

        final TimeZone tz = LocaleContextHolder.getTimeZone();
        final DateTimeZone timeZone = DateTimeZone.forTimeZone(tz);

        try {
            DateTimeIterable dateIterable;

            dateIterable = DateTimeIteratorFactory.createDateTimeIterable(
                    repeatRules, schedule.getStart(), timeZone, true);

            final DateTime next = new DateTime(dateIterable.iterator().next());
            final DateTime dt = next.withZone(timeZone);

            return dt;
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<EventDto> getWeekSchedule(final Set<Schedule> schedules) {
        final ArrayList<Schedule> scs = new ArrayList<Schedule>(schedules);
        Collections.sort(scs, SCHEDULE_COMPARATOR);
        //        final LocalDate firstEvent = scs.get(0).getNextEvent();

        final ArrayList<EventDto> ret = new ArrayList<EventDto>();
        for (final Schedule sc : scs) {
            final ReadableDateTime nextEvent = sc.getNextEvent();
            final EventDto eventDto = new EventDto();

            eventDto.setStart(nextEvent.toDateTime());
            eventDto.setDayShortName(DateTimeFormat.forPattern("EE").print(nextEvent));
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
