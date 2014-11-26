package org.az.skill2peer.nuclei.common.controller.rest.dto;

import java.util.Date;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 *
 *
 * @author Artem Zaborskiy
 *
 */
public class ScheduleDto {
    private Date start;

    /**
     * duration in minutes;
     */
    private int duration;

    public int getDuration() {
        return duration;
    }

    public String getDurationAsString() {
        final Period period = Period.minutes(duration);
        return PeriodFormat.wordBased(LocaleContextHolder.getLocale())
                .print(period.normalizedStandard(period.getPeriodType()));
    }

    public Date getStart() {
        return start;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public void setStart(final Date start) {
        this.start = start;
    }
}
