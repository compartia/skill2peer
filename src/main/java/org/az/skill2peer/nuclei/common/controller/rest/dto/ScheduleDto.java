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
public class ScheduleDto {
    private DateTime start;

    /**
     * duration in minutes;
     */
    private int duration;

    private String next;

    public int getDuration() {
        return duration;
    }

    public String getDurationAsString() {
        final Period period = Period.minutes(duration);
        return PeriodFormat.wordBased(LocaleContextHolder.getLocale())
                .print(period.normalizedStandard(period.getPeriodType()));
    }

    public DateTime getEnd() {
        final DateTime s = new DateTime(start);
        return s.plus(Period.minutes(duration));
    }

    public String getNext() {
        return next;
    }

    public DateTime getStart() {
        return start;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public void setNext(final String next) {
        this.next = next;
    }

    //    public int getStartDay(){
    //        DateTime time=new DateTime(start);
    //        time.getDayOfMonth();
    //    }

    public void setStart(final DateTime start) {
        this.start = start;
    }
}
