package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 *
 *
 * @author Artem Zaborskiy
 *
 */
public class ScheduleEditDto {
    private static final String DATE_FORMAT_MONTH = "MMMM";

    /**
     * duration in minutes;
     */
    private int duration;

    private DateTimeEditDto dateTime;

    private String next;

    public DateTimeEditDto getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getDurationAsString() {
        final Period period = Period.minutes(duration);
        return PeriodFormat.wordBased(LocaleContextHolder.getLocale())
                .print(period.normalizedStandard(period.getPeriodType()));
    }

    public DateTime getEnd() {
        final DateTime s = new DateTime(dateTime.toDateTime());
        return s.plus(Period.minutes(duration));
    }

    public String getEndMonth() {
        return getEnd().toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public String getNext() {
        return next;
    }

    public String getStartMonth() {
        return this.dateTime.toDateTime().toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public void setDateTime(final DateTimeEditDto dateTime) {
        this.dateTime = dateTime;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

}
