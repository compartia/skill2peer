package org.az.skill2peer.nuclei.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.az.skill2peer.nuclei.common.controller.dto.EventDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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
import com.google.ical.values.DateValueImpl;
import com.google.ical.values.Frequency;
import com.google.ical.values.RRule;

public class CalendarUtils {

    public static EventDto buidEventDto(final DateTime from, final DateTime to) {
        final EventDto eventDto = new EventDto();
        eventDto.setStart(from);
        eventDto.setEnd(to);
        return eventDto;
    }

    public static EventDto buidEventDto(final DateTime from, final DateTime to, final DateTimeZone timeZone) {

        final EventDto eventDto = new EventDto();
        eventDto.setStart(from.withZone(timeZone));
        if (to != null) {
            eventDto.setEnd(to.withZone(timeZone));
        }

        return eventDto;
    }

    public static EventDto buidEventDto(final DateTime from, final int duration) {
        final EventDto eventDto = new EventDto();
        eventDto.setStart(from);
        eventDto.setEnd(from.plusMinutes(duration));
        return eventDto;
    }

    public static DateTimeIterator createDateTimeIterator(
            final String repeatRules,
            final DateTime scheduleStart,
            final DateTimeZone timeZone) throws ParseException {

        DateTime start = scheduleStart;
        String exdate = "";
        final RRule rrule = new RRule(repeatRules);
        if (rrule.getFreq().ordinal() > Frequency.DAILY.ordinal()) {
            start = start.minusDays(1);
            exdate = "\nEXDATE:"
                    + ISODateTimeFormat.basicDateTimeNoMillis().print(start.withZone(timeZone).toLocalDateTime());
        }

        final DateTimeIterable dateIterable = DateTimeIteratorFactory.createDateTimeIterable(
                repeatRules + exdate,
                start,
                timeZone,
                true);

        return dateIterable.iterator();

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

    /**
     * XXX: why deprecated? because util class must not deal with entities
     * @param schedule
     * @return
     */
    @Deprecated
    public static EventDto getNextEvent(final Schedule schedule) {
        final String repeatRules = schedule.getiCalString();

        final DateTime scheduleStart = schedule.getStart();

        if (StringUtils.isEmpty(repeatRules) || scheduleStart == null) {

            return buidEventDto(scheduleStart, schedule.getEnd());
        }

        final TimeZone tz = LocaleContextHolder.getTimeZone();
        final DateTimeZone timeZone = DateTimeZone.forTimeZone(tz);

        try {

            final DateTimeIterator iterator = getProperIterator(DateTime.now(),
                    schedule.getiCalString(),
                    schedule.getStart(),
                    DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone()));

            if (iterator.hasNext()) {
                final DateTime next = new DateTime(iterator.next());
                final DateTime dt = next.withZone(timeZone);

                return buidEventDto(dt, schedule.getDuration());
            } else {
                return null;
            }
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static DateTimeIterator getProperIterator(final DateTime today,
            final String repeatRules,
            final DateTime scheduleStart,
            final DateTimeZone timeZone) throws ParseException {
        final DateTimeIterator iterator = createDateTimeIterator(
                repeatRules,
                scheduleStart,
                timeZone);

        iterator.advanceTo(today);
        return iterator;
    }

    public static List<DayEventsDto> groupEventsInWeek(final List<EventDto> eventsWithinPeriod) {
        final List<DayEventsDto> events = CalendarUtils.makeWeekPattern();

        for (final DayEventsDto set : events) {
            set.getEvents().clear();
        }
        for (final EventDto e : eventsWithinPeriod) {
            final Set<EventDto> set = events.get(e.getStart().getDayOfWeek() - 1).getEvents();
            //set.clear();
            set.add(e);
        }

        return events;
    }

    public static List<DayEventsDto> makeWeekPattern() {
        final ArrayList<DayEventsDto> ret = new ArrayList<DayEventsDto>(7);

        for (int f = 0; f < 7; f++) {
            final DayEventsDto de = new DayEventsDto();
            de.setDayShortName(LocalDateRenderingUtils.getDayShortNameLocal(f));
            ret.add(de);
        }
        return ret;
    }

    public static DateTime toClientTimeZone(final DateTime date) {
        if (date == null) {
            return null;
        }
        final DateTimeZone timeZone = DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone());
        return date.withZone(timeZone);
    }

    public static void withTimeZone(final List<EventDto> events, final DateTimeZone zone) {
        for (final EventDto event : events) {
            event.setStart(event.getStart().withZone(zone));
            if (event.getEnd() != null) {
                event.setEnd(event.getEnd().withZone(zone));
            }
        }
    }

    public static final Comparator<Schedule> SCHEDULE_COMPARATOR = new Comparator<Schedule>() {
        @Override
        public int compare(final Schedule s1, final Schedule s2) {
            return s1.getNextEvent().getStart().compareTo(s2.getNextEvent().getStart());
        }
    };

    public static final Comparator<Lesson> LESSON_COMPARATOR = new Comparator<Lesson>() {
        @Override
        public int compare(final Lesson s1, final Lesson s2) {
            final DateTime e1 = s1.getSchedule().getNextEvent().getStart();
            final DateTime e2 = s2.getSchedule().getNextEvent().getStart();

            if (e1 == null) {
                return -1;
            }
            if (e2 == null) {
                return 1;
            }
            return e1.compareTo(e2);
        }
    };

    public static final String DATE_FORMAT_MONTH = "MMMM";

    public static DateValueImpl makeDateValue(final DateTime now) {
        return new DateValueImpl(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth());
    }
}
