package org.az.skill2peer.nuclei;

import java.util.List;
import java.util.Locale;

import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.ical.values.DateValueImpl;
import com.google.ical.values.Frequency;
import com.google.ical.values.RRule;
import com.google.ical.values.Weekday;

public class CalendarUtilsTest {

    @Before
    public void _setUp() {
        LocaleContextHolder.setLocale(new Locale.Builder().setLanguage("ru").setRegion("FR").build());
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
    public void getNextEvent() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule();
        schedule.setStart(new DateTime(2020, 11, 25, 11, 00));

        final RRule rr = new RRule();
        {
            rr.setFreq(Frequency.DAILY);
            rr.setUntil(new DateValueImpl(2020, 1, 2));
            rr.setWkSt(Weekday.MO);
            Assert.assertEquals("RRULE:FREQ=DAILY;WKST=MO;UNTIL=20200102", rr.toIcal());
        }

        schedule.setiCalString(rr.toIcal());

        final ReadableDateTime nextEvent = schedule.getNextEvent();
        System.out.println();
        Assert.assertTrue(nextEvent.toDateTime().isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());
        Assert.assertEquals(schedule.getStart().getHourOfDay(), nextEvent.getHourOfDay());
        Assert.assertEquals(schedule.getStart().getMinuteOfHour(), nextEvent.getMinuteOfHour());
    }

    @Test
    public void getNextEvent2() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule();
        schedule.setStart(new DateTime(2020, 11, 25, 23, 55));

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
        Assert.assertTrue(nextEvent.toDateTime().isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());
        Assert.assertEquals(schedule.getStart().getHourOfDay(), nextEvent.getHourOfDay());
        Assert.assertEquals(schedule.getStart().getMinuteOfHour(), nextEvent.getMinuteOfHour());
    }

    @Test
    public void getNextEvent3() throws Exception {
        final Schedule schedule = TestUtil.makeSchedule();
        schedule.setStart(new DateTime(2020, 11, 25, 00, 05,
                DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone())));

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
        Assert.assertTrue(nextEvent.toDateTime().isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());
        Assert.assertEquals(schedule.getStart().getHourOfDay(), nextEvent.getHourOfDay());
        Assert.assertEquals(schedule.getStart().getMinuteOfHour(), nextEvent.getMinuteOfHour());
    }

    @Test
    public void getWeekSchedule() {
        final Schedule schedule = new Schedule();
        final DateTimeZone tz = DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone());
        schedule.setStart(new DateTime(2014, 11, 10, 13, 40, tz));
        schedule.setEnd(new DateTime(2014, 11, 10, 18, 40, tz));
        {
            final RRule rr = new RRule();
            rr.setFreq(Frequency.DAILY);
            rr.setUntil(new DateValueImpl(2024, 1, 2));
            rr.setWkSt(Weekday.MO);
            schedule.setiCalString(rr.toIcal());
        }

        final List<DayEventsDto> weekSchedule = schedule.getWeekSchedule(new DateTime(2014, 11, 25, 18, 40));
        Assert.assertEquals(7, weekSchedule.size());
        int dw = 1;
        for (final DayEventsDto events : weekSchedule) {
            Assert.assertEquals(1, events.getEvents().size());
            final EventDto first = events.getFirst();

            Assert.assertEquals(tz, first.getStart().getZone());
            Assert.assertEquals(tz, first.getEnd().getZone());

            Assert.assertEquals(dw, first.getStart().getDayOfWeek());

            Assert.assertEquals(18, first.getEnd().getHourOfDay());
            Assert.assertEquals(13, first.getStart().getHourOfDay());

            dw++;
        }
    }

    @Test
    public void getWeekScheduleNoRepeat() {
        final Schedule schedule = new Schedule();
        final DateTimeZone tz = DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone());
        schedule.setStart(new DateTime(2014, 11, 12, 13, 40, tz));
        schedule.setEnd(new DateTime(2014, 11, 12, 18, 40, tz));

        final List<DayEventsDto> weekSchedule = schedule.getWeekSchedule(new DateTime(2014, 11, 10, 18, 40));
        Assert.assertEquals(7, weekSchedule.size());
        final int dw = 1;

        //WED
        final DayEventsDto dayEventsDto = weekSchedule.get(2);
        Assert.assertEquals("Ср", dayEventsDto.getDayShortName());
        final EventDto first = dayEventsDto.getFirst();
        Assert.assertEquals(tz, first.getStart().getZone());
        Assert.assertEquals(13, first.getStart().getHourOfDay());
        Assert.assertEquals(18, first.getEnd().getHourOfDay());

        //        for (final DayEventsDto events : weekSchedule) {
        //            Assert.assertEquals(1, events.getEvents().size());
        //            final EventDto first = events.getFirst();
        //
        //            Assert.assertEquals(tz, first.getStart().getZone());
        //            Assert.assertEquals(tz, first.getEnd().getZone());
        //
        //            Assert.assertEquals(dw, first.getStart().getDayOfWeek());
        //
        //            Assert.assertEquals(18, first.getEnd().getHourOfDay());
        //            Assert.assertEquals(13, first.getStart().getHourOfDay());
        //
        //            dw++;
        //        }
    }

    @Test
    public void makeWeekPattern() {
        final List<DayEventsDto> wp = CalendarUtils.makeWeekPattern(new DateTime(2014, 11, 26, 18, 40));
        Assert.assertEquals(7, wp.size());

        Assert.assertEquals("Пн", wp.get(0).getDayShortName());
        Assert.assertEquals("Вс", wp.get(6).getDayShortName());

        Assert.assertEquals(24, wp.get(0).getFirst().getStart().getDayOfMonth());
        Assert.assertEquals(30, wp.get(6).getFirst().getStart().getDayOfMonth());
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
