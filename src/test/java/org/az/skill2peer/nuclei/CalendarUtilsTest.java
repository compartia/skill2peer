package org.az.skill2peer.nuclei;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.ical.compat.jodatime.DateTimeIterator;
import com.google.ical.values.DateValueImpl;
import com.google.ical.values.Frequency;
import com.google.ical.values.RRule;
import com.google.ical.values.Weekday;

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
            TestUtil.compareTime(start, nextEvent);
            Assert.assertEquals(24, nextEvent.getDayOfMonth());
            Assert.assertEquals(DateTimeConstants.WEDNESDAY, nextEvent.getDayOfWeek());

        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.THURSDAY, nextEvent.getDayOfWeek());
            TestUtil.compareTime(start, nextEvent);
        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.FRIDAY, nextEvent.getDayOfWeek());
            TestUtil.compareTime(start, nextEvent);

        }
    }

    @Test
    public void getEventIteratorDailyWhenSequenceStartsLater() throws ParseException {

        /**saturday*/
        final DateTime start = new DateTime(2014, 12, 20, 11, 15, timeZone);
        final DateTime today = start.minusWeeks(2).plusHours(2);
        /** 2 weeks before*/

        final DateTimeIterator iter = CalendarUtils.getProperIterator(today, "RRULE:FREQ=DAILY", start, timeZone);

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.SATURDAY, nextEvent.getDayOfWeek());
            TestUtil.compareTime(start, nextEvent);
        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.SUNDAY, nextEvent.getDayOfWeek());
            TestUtil.compareTime(start, nextEvent);
        }

        {
            final DateTime nextEvent = iter.next().withZone(timeZone);
            Assert.assertEquals(DateTimeConstants.MONDAY, nextEvent.getDayOfWeek());
            TestUtil.compareTime(start, nextEvent);
            Assert.assertEquals(22, nextEvent.getDayOfMonth());
        }
    }

    @Test
    public void getEventsWithinWeek() throws Exception {
        final DateTime now = DateTime.now();

        final Schedule schedule = new Schedule();
        schedule.setiCalString("RRULE:FREQ=WEEKLY;BYDAY=MO;UNTIL=20300102");
        schedule.setStart(DateTime.parse("2004-12-26T16:00"));
        schedule.setEnd(DateTime.parse("2004-12-26T18:00"));

        Assert.assertTrue(schedule.isRecurrent());

        Assert.assertTrue(schedule.getStart().isBefore(now));
        final List<EventDto> eventsWithinWeek = schedule.getEventsWithinWeek(now);
        Assert.assertEquals(1, eventsWithinWeek.size());
        TestUtil.compareTime(schedule.getStart(), eventsWithinWeek.get(0).getStart());
    }

    @Test
    public void getNextEvent() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule(true);
        schedule.setStart(new DateTime(2024, 12, 21, 11, 00));
        schedule.setiCalString("RRULE:FREQ=WEEKLY;UNTIL=20300102;BYDAY=MO,TH");

        final DateTime nextEvent = schedule.getNextEvent();

        Assert.assertTrue(nextEvent.isAfter(DateTime.now()));
        TestUtil.compareTime(schedule.getStart(), nextEvent);
        Assert.assertEquals(DateTimeConstants.MONDAY, nextEvent.getDayOfWeek());//expect MOnday

    }

    @Test
    public void getNextEvent2() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule(true);
        schedule.setStart(new DateTime(2020, 11, 25, 23, 55, timeZone));

        final RRule rr = new RRule();
        {
            rr.setFreq(Frequency.DAILY);
            rr.setUntil(new DateValueImpl(2024, 1, 2));
            rr.setWkSt(Weekday.MO);
            Assert.assertEquals("RRULE:FREQ=DAILY;WKST=MO;UNTIL=20240102", rr.toIcal());
        }

        schedule.setiCalString(rr.toIcal());

        final DateTime nextEvent = schedule.getNextEvent();

        Assert.assertTrue(nextEvent.isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());

        TestUtil.compareTime(schedule.getStart(), nextEvent);
    }

    @Test
    public void getNextEvent3() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule(true);
        schedule.setStart(new DateTime(2020, 11, 25, 00, 05, timeZone));

        final RRule rr = new RRule();
        {
            rr.setFreq(Frequency.DAILY);
            rr.setUntil(new DateValueImpl(2024, 1, 2));
            rr.setWkSt(Weekday.MO);

            Assert.assertEquals("RRULE:FREQ=DAILY;WKST=MO;UNTIL=20240102", rr.toIcal());
        }

        schedule.setiCalString(rr.toIcal());

        final DateTime nextEvent = schedule.getNextEvent();
        System.out.println();
        Assert.assertTrue(nextEvent.isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());

        TestUtil.compareTime(schedule.getStart(), nextEvent);
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
