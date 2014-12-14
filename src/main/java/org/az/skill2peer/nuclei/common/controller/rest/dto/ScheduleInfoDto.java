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
public class ScheduleInfoDto {
    private static final String DATE_FORMAT_MONTH = "MMMM";

    private String next;

    private DateTime start;

    private DateTime end;

    public int getDuration() {
        return duration;
    }

    public String getDurationAsString() {
        final Period period = Period.minutes(duration);
        return PeriodFormat.wordBased(LocaleContextHolder.getLocale())
                .print(period.normalizedStandard(period.getPeriodType()));
    }

    public DateTime getEnd() {
        return end;
    }

    public String getEndMonth() {
        return getEnd().toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public String getNext() {
        return next;
    }

    public String getStartMonth() {
        return start.toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

}
