package org.az.skill2peer.nuclei.services;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
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

    @Deprecated
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

    @Deprecated
    public static List<EventDto> getWeekSchedule(final Collection<Schedule> schedules) {
        final ArrayList<Schedule> scs = new ArrayList<Schedule>(schedules);
        Collections.sort(scs, SCHEDULE_COMPARATOR);
        //        final LocalDate firstEvent = scs.get(0).getNextEvent();

        final ArrayList<EventDto> ret = new ArrayList<EventDto>();
        for (final Schedule sc : scs) {
            final DateTime nextEvent = sc.getNextEvent();

            final EventDto eventDto = new EventDto();
            eventDto.setStart(nextEvent.toDateTime());
            eventDto.setDayShortName(DateTimeFormat.forPattern("EE").print(nextEvent));
            // eventDto.setName(name);

            ret.add(eventDto);
        }
        return ret;

    }

    public static List<DayEventsDto> makeWeekPattern(final DateTime week) {
        final DateTime weekStart = week.withDayOfWeek(DateTimeConstants.MONDAY);

        final ArrayList<DayEventsDto> ret = new ArrayList<DayEventsDto>(7);

        DateTime day = weekStart;
        for (int f = 0; f < 7; f++) {
            final DayEventsDto de = new DayEventsDto();
            final EventDto e = new EventDto();

            final String dayname = DayOfWeek
                    .of(1 + f)
                    .getDisplayName(TextStyle.SHORT_STANDALONE, LocaleContextHolder.getLocale());
            e.setDayShortName(dayname);
            de.setDayShortName(dayname);
            e.setStart(day);

            de.addEvent(e);
            ret.add(de);

            day = day.plusDays(1);
        }
        return ret;
    }

    public static final Comparator<Schedule> SCHEDULE_COMPARATOR = new Comparator<Schedule>() {
        @Override
        public int compare(final Schedule s1, final Schedule s2) {
            return s1.getNextEvent().compareTo(s2.getNextEvent());
        }
    };

    public static final Comparator<Lesson> LESSON_COMPARATOR = new Comparator<Lesson>() {
        @Override
        public int compare(final Lesson s1, final Lesson s2) {
            final DateTime e1 = s1.getSchedule().getNextEvent();
            final DateTime e2 = s2.getSchedule().getNextEvent();

            if (e1 == null) {
                return -1;
            }
            if (e2 == null) {
                return 1;
            }
            return e1.compareTo(e2);
        }
    };
}
