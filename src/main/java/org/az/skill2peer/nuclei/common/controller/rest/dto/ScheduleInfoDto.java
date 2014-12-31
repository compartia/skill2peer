package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.context.i18n.LocaleContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 *
 * @author Artem Zaborskiy
 *
 */
public class ScheduleInfoDto {
    private static final String DATE_FORMAT_MONTH = "MMMM";

    @Deprecated
    private String next;

    @JsonIgnore
    private DateTime start;

    @JsonIgnore
    private DateTime end;

    private String nextEvent;

    public String getDates() {
        String ret = start.toString("d MMMM", LocaleContextHolder.getLocale());
        if (end != null) {
            ret += end.toString(" - d MMMM", LocaleContextHolder.getLocale());
        }
        if (start.getYear() != DateTime.now().getYear()) {
            ret += ", " + start.getYear();
        }
        return ret;
    }

    /**
     * something like "2 часа 32 минуты"
     * @return
     */
    public String getDurationAsString() {
        if (start == null || end == null) {
            return "";
        }
        return CalendarUtils.formatHoursDuration(LocaleContextHolder.getLocale(), Minutes
                .minutesBetween(start, end)
                .getMinutes());
        //        return CalendarUtils.formatPeriod(LocaleContextHolder.getLocale(), start.toDate(), end.toDate());

    }

    public DateTime getEnd() {
        return end;
    }

    public String getEndMonth() {
        if (getEnd() != null) {
            return getEnd().toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
        } else {
            return null;
        }
    }

    @Deprecated
    public String getNext() {
        return next;
    }

    public String getNextEvent() {
        return nextEvent;
    }

    public DateTime getStart() {
        return start;
    }

    public String getStartMonth() {
        return start.toString(DATE_FORMAT_MONTH, LocaleContextHolder.getLocale());
    }

    public void setEnd(final DateTime end) {
        this.end = end;
    }

    public void setNextEvent(final String nextEvent) {
        this.nextEvent = nextEvent == null ? null : nextEvent.toString();
    }

    public void setStart(final DateTime start) {
        this.start = start;
    }

}
