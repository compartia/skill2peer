package org.az.skill2peer.nuclei;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.ical.compat.jodatime.DateTimeIterable;
import com.google.ical.compat.jodatime.DateTimeIterator;
import com.google.ical.compat.jodatime.DateTimeIteratorFactory;
import com.google.ical.values.DateValueImpl;
import com.google.ical.values.Frequency;
import com.google.ical.values.RRule;
import com.google.ical.values.Weekday;
import com.google.ical.values.WeekdayNum;

public class CalendarUtilsTest {

    private DateTimeZone timeZone;

    @Before
    public void _setUp() {
        LocaleContextHolder.setLocale(new Locale.Builder().setLanguage("ru").setRegion("FR").build());
        timeZone = DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone());
    }

    @Test
    public void formatHours() {
        final Locale locale = LocaleContextHolder.getLocale();
        Assert.assertEquals("1 час", CalendarUtils.formatHoursDuration(locale, 1 * 60));

        Assert.assertEquals("12 часов", CalendarUtils.formatHoursDuration(locale, 12 * 60));

        Assert.assertEquals("14 часов", CalendarUtils.formatHoursDuration(locale, 14 * 60));

        Assert.assertEquals("111 часов 51 минута", CalendarUtils.formatHoursDuration(locale, 51 + 111 * 60));

        Assert.assertEquals("121 час", CalendarUtils.formatHoursDuration(locale, 121 * 60));

        Assert.assertEquals("32 часа", CalendarUtils.formatHoursDuration(locale, 32 * 60));

        Assert.assertEquals("2 часа", CalendarUtils.formatHoursDuration(locale, 2 * 60));
    }

    @Test
    public void getEventIteratorDailyWhenSequenceStartsEarlier() throws ParseException {
        /**saturday*/
        final DateTime start = new DateTime(2014, 12, 20, 11, 15, timeZone);
        /**WEDNESDAY*/
        final DateTime now = new DateTime(2014, 12, 24, 10, 00, timeZone);
        /** 2 weeks before*/

        final DateTimeIterator iter = CalendarUtils.getProperIterator(now, "RRULE:FREQ=DAILY", start, timeZone);

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(24, nextEvent.getDayOfMonth());
            Assert.assertEquals(DateTimeConstants.WEDNESDAY, nextEvent.getDayOfWeek());
            Assert.assertEquals(11, nextEvent.getHourOfDay());
            Assert.assertEquals(15, nextEvent.getMinuteOfHour());

        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.THURSDAY, nextEvent.getDayOfWeek());
            Assert.assertEquals(11, nextEvent.getHourOfDay());
            Assert.assertEquals(15, nextEvent.getMinuteOfHour());
        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.FRIDAY, nextEvent.getDayOfWeek());
            Assert.assertEquals(11, nextEvent.getHourOfDay());
            Assert.assertEquals(15, nextEvent.getMinuteOfHour());

        }
    }

    @Test
    public void getEventIteratorDailyWhenSequenceStartsLater() throws ParseException {

        /**saturday*/
        final DateTime start = new DateTime(2014, 12, 20, 11, 15, timeZone);
        final DateTime today = start.minusWeeks(2);
        /** 2 weeks before*/

        final DateTimeIterator iter = CalendarUtils.getProperIterator(today, "RRULE:FREQ=DAILY", start, timeZone);

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.SATURDAY, nextEvent.getDayOfWeek());
            Assert.assertEquals(11, nextEvent.getHourOfDay());
            Assert.assertEquals(15, nextEvent.getMinuteOfHour());
        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.SUNDAY, nextEvent.getDayOfWeek());
            Assert.assertEquals(11, nextEvent.getHourOfDay());
            Assert.assertEquals(15, nextEvent.getMinuteOfHour());
        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.MONDAY, nextEvent.getDayOfWeek());
            Assert.assertEquals(11, nextEvent.getHourOfDay());
            Assert.assertEquals(15, nextEvent.getMinuteOfHour());
            Assert.assertEquals(22, nextEvent.getDayOfMonth());
        }
    }

    @Test
    public void getNextEvent() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule();
        schedule.setStart(new DateTime(2014, 12, 20, 11, 00));

        final RRule rr = new RRule();
        {
            rr.setFreq(Frequency.WEEKLY);
            final ArrayList<WeekdayNum> wd = new ArrayList<WeekdayNum>();
            wd.add(new WeekdayNum(0, Weekday.MO));
            wd.add(new WeekdayNum(0, Weekday.TH));
            rr.setByDay(wd);
            rr.setUntil(new DateValueImpl(2030, 1, 2));
            rr.setWkSt(Weekday.MO);
            Assert.assertEquals("RRULE:FREQ=WEEKLY;WKST=MO;UNTIL=20300102;BYDAY=MO,TH", rr.toIcal());
        }

        schedule.setiCalString(rr.toIcal());

        final ReadableDateTime nextEvent = schedule.getNextEvent();

        final DateTime now = DateTime.now();

        Assert.assertEquals(1, nextEvent.getDayOfWeek());//expect MOnday
        Assert.assertEquals(22, nextEvent.getDayOfMonth());

        Assert.assertEquals(schedule.getStart().getMinuteOfHour(), nextEvent.getMinuteOfHour());
        Assert.assertEquals(schedule.getStart().getHourOfDay(), nextEvent.getHourOfDay());

        Assert.assertTrue(nextEvent.isAfter(now));
    }

    @Test
    public void getNextEvent2() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule();
        schedule.setStart(new DateTime(2020, 11, 25, 23, 55, timeZone));

        final RRule rr = new RRule();
        {
            rr.setFreq(Frequency.DAILY);
            rr.setUntil(new DateValueImpl(2024, 1, 2));
            rr.setWkSt(Weekday.MO);
            Assert.assertEquals("RRULE:FREQ=DAILY;WKST=MO;UNTIL=20240102", rr.toIcal());
        }

        schedule.setiCalString(rr.toIcal());

        final ReadableDateTime nextEvent = schedule.getNextEvent();

        Assert.assertTrue(nextEvent.isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());
        Assert.assertEquals(schedule.getStart().getHourOfDay(), nextEvent.getHourOfDay());
        Assert.assertEquals(schedule.getStart().getMinuteOfHour(), nextEvent.getMinuteOfHour());
    }

    @Test
    public void getNextEvent3() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule();
        schedule.setStart(new DateTime(2020, 11, 25, 00, 05, timeZone));

        final RRule rr = new RRule();
        {
            rr.setFreq(Frequency.DAILY);
            rr.setUntil(new DateValueImpl(2024, 1, 2));
            rr.setWkSt(Weekday.MO);

            Assert.assertEquals("RRULE:FREQ=DAILY;WKST=MO;UNTIL=20240102", rr.toIcal());
        }

        schedule.setiCalString(rr.toIcal());

        final ReadableDateTime nextEvent = schedule.getNextEvent();
        System.out.println();
        Assert.assertTrue(nextEvent.isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());
        Assert.assertEquals(schedule.getStart().getHourOfDay(), nextEvent.getHourOfDay());
        Assert.assertEquals(schedule.getStart().getMinuteOfHour(), nextEvent.getMinuteOfHour());
    }

    @Test
    public void listDates() throws ParseException {
        final String repeatRules = "RRULE:FREQ=WEEKLY;WKST=MO;UNTIL=20150102;BYDAY=TH";

        final TimeZone tz = LocaleContextHolder.getTimeZone();
        final DateTimeZone timeZone = DateTimeZone.forTimeZone(tz);

        try {
            DateTimeIterable dateIterable;

            dateIterable = DateTimeIteratorFactory.createDateTimeIterable(
                    repeatRules, DateTime.now().minusDays(1), timeZone, true);
            // final DateTimeIterator iterator = dateIterable.iterator();
            for (final DateTime next : dateIterable) {

                final DateTime dt = next.withZone(timeZone);

                System.out.println(dt);
            }
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }

        //        final LocalDate start = new LocalDate(2001, 4, 13);
        //
        //        // Every friday the thirteenth.
        //        final String ical = "RRULE:FREQ=WEEKLY;WKST=MO;UNTIL=20300102;BYDAY=MO,TH"; // stop after 13 occurences
        //
        //        // Print out each date in the series.
        //        for (final LocalDate date : LocalDateIteratorFactory.createLocalDateIterable(ical, start, true)) {
        //            System.out.println(date);
        //        }
    }

    @Test
    public void makeWeekPattern() {
        final List<DayEventsDto> eventGoups = CalendarUtils.makeWeekPattern();
        Assert.assertEquals(7, eventGoups.size());

        Assert.assertEquals("Пн", eventGoups.get(0).getDayShortName());
        Assert.assertEquals("Вс", eventGoups.get(6).getDayShortName());

    }

    @Test
    public void testDurationMapping3() {
        final DateTime from = DateTime.now();

        Assert.assertEquals("21 час 10 минут", CalendarUtils.formatPeriod(LocaleContextHolder.getLocale(),
                from.toDate(),
                from.plusMinutes(10 + 21 * 60).toDate()));

        Assert.assertEquals("10 минут", CalendarUtils.formatPeriod(LocaleContextHolder.getLocale(),
                from.toDate(),
                from.plusMinutes(10).toDate()));

    }
}
