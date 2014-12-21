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
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.impl.DurationImpl;
import org.ocpsoft.prettytime.units.Hour;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Minute;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.ical.compat.jodatime.DateTimeIterable;
import com.google.ical.compat.jodatime.DateTimeIterator;
import com.google.ical.compat.jodatime.DateTimeIteratorFactory;

public class CalendarUtils {

    public static EventDto buidEventDto(final DateTime from, final DateTime to, final DateTimeZone timeZone) {

        final EventDto eventDto = new EventDto();
        eventDto.setStart(from.withZone(timeZone));
        if (to != null) {
            eventDto.setEnd(to.withZone(timeZone));
        }

        final String dayname = getDayShortNameLocal(from);

        eventDto.setDayShortName(dayname);
        return eventDto;
    }

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

    public static String getDayShortNameLocal(final DateTime date) {
        return getDayShortNameLocal(date.getDayOfWeek() - 1);
    }

    public static String getDayShortNameLocal(final int f) {
        return DayOfWeek
                .of(1 + f)
                .getDisplayName(TextStyle.SHORT_STANDALONE, LocaleContextHolder.getLocale());
    }

    @Deprecated
    public static DateTime getNextEvent(final Schedule schedule) {
        final String repeatRules = schedule.getiCalString();

        final DateTime scheduleStart = schedule.getStart();

        if (StringUtils.isEmpty(repeatRules) || scheduleStart == null) {
            return scheduleStart;
        }

        final TimeZone tz = LocaleContextHolder.getTimeZone();
        final DateTimeZone timeZone = DateTimeZone.forTimeZone(tz);

        try {

            final DateTimeIterator iterator = getProperIterator(schedule);
            final DateTime next = new DateTime(iterator.next());
            final DateTime dt = next.withZone(timeZone);

            return dt;
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static DateTimeIterator getProperIterator(final Schedule schedule) throws ParseException {
        final DateTime scheduleStart = schedule.getStart();
        final String repeatRules = schedule.getiCalString();
        final TimeZone tz = LocaleContextHolder.getTimeZone();
        final DateTimeZone timeZone = DateTimeZone.forTimeZone(tz);

        DateTime start = DateTime
                .now()
                .withHourOfDay(scheduleStart.getHourOfDay())
                .withMinuteOfHour(scheduleStart.getMinuteOfHour());
        if (start.isBefore(scheduleStart)) {
            start = scheduleStart.minusDays(1);
        }
        final String exdate = ISODateTimeFormat.basicDateTimeNoMillis().print(start.toLocalDateTime());

        final DateTimeIterable dateIterable = DateTimeIteratorFactory.createDateTimeIterable(
                repeatRules + "\nEXDATE:" + exdate,
                start,
                timeZone,
                true);

        final DateTimeIterator iterator = dateIterable.iterator();
        return iterator;
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

    public static List<DayEventsDto> groupEventsInWeek(final List<EventDto> eventsWithinPeriod) {
        final List<DayEventsDto> events = CalendarUtils.makeWeekPattern();

        for (final EventDto e : eventsWithinPeriod) {
            final Set<EventDto> set = events.get(e.getStart().getDayOfWeek() - 1).getEvents();
            set.clear();
            set.add(e);
        }

        return events;

    }

    public static List<DayEventsDto> makeWeekPattern() {
        //        final DateTime weekStart = week.withDayOfWeek(DateTimeConstants.MONDAY);

        final ArrayList<DayEventsDto> ret = new ArrayList<DayEventsDto>(7);

        //        DateTime day = weekStart;
        for (int f = 0; f < 7; f++) {
            final DayEventsDto de = new DayEventsDto();
            //            final EventDto e = new EventDto();
            //
            final String dayname = getDayShortNameLocal(f);
            //            e.setDayShortName(dayname);
            de.setDayShortName(dayname);
            //            e.setStart(day);
            //
            //            de.addEvent(e);
            ret.add(de);

            //            day = day.plusDays(1);
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
