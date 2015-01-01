package org.az.skill2peer.nuclei.services;

import static org.az.skill2peer.nuclei.services.CalendarUtils.toClientTimeZone;

import java.time.DayOfWeek;
import java.time.format.TextStyle;

import org.joda.time.DateTime;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocalDateRenderingUtils {
    public static String getDates(final DateTime start, final DateTime end) {
        final DateTime startLocal = CalendarUtils.toClientTimeZone(start);
        final DateTime eldLocal = CalendarUtils.toClientTimeZone(end);
        String ret = startLocal.toString("d MMMM", LocaleContextHolder.getLocale());
        if (eldLocal != null) {
            ret += eldLocal.toString(" - d MMMM", LocaleContextHolder.getLocale());
        }
        if (start.getYear() != DateTime.now().getYear()) {
            ret += ", " + start.getYear();
        }
        return ret;
    }

    public static String getDayInMonth(final DateTime date) {
        if (date == null) {
            return null;
        }
        return toClientTimeZone(date).toString("d", LocaleContextHolder.getLocale());
    }

    public static String getDayShortNameLocal(final DateTime date) {
        return LocalDateRenderingUtils.getDayShortNameLocal(date.getDayOfWeek() - 1);
    }

    public static String getDayShortNameLocal(final int f) {
        return DayOfWeek
                .of(1 + f)
                .getDisplayName(TextStyle.SHORT_STANDALONE, LocaleContextHolder.getLocale());
    }

    public static String getMonthLongName(final DateTime date) {
        if (date == null) {
            return null;
        }
        return CalendarUtils.toClientTimeZone(date).toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public static String getTimes(final DateTime start, final DateTime end) {
        final DateTime startLocal = CalendarUtils.toClientTimeZone(start);
        final DateTime eldLocal = CalendarUtils.toClientTimeZone(end);
        String ret = startLocal.toString("HH:mm", LocaleContextHolder.getLocale());
        if (eldLocal != null) {
            ret += eldLocal.toString(" - HH:mm", LocaleContextHolder.getLocale());
        }
        if (start.getYear() != DateTime.now().getYear()) {
            ret += ", " + start.getYear();
        }
        return ret;
    }

    private static final String DATE_FORMAT_MONTH = "MMMM";
}
