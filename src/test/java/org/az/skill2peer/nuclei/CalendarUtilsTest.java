package org.az.skill2peer.nuclei;

import java.util.Locale;

import org.az.skill2peer.nuclei.common.model.Schedule;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

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
        schedule.setiCalString("RRULE:FREQ=DAILY;"
                + "INTERVAL=1;"
                //                + "UNTIL=20230430T083000Z;"
                + "");

        final ReadableDateTime nextEvent = schedule.getNextEvent();
        System.out.println();
        Assert.assertTrue(nextEvent.toDateTime().isAfter(DateTime.now()));
        Assert.assertEquals(25, nextEvent.getDayOfMonth());
        Assert.assertEquals(schedule.getStart().getHourOfDay(), nextEvent.getHourOfDay());
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
