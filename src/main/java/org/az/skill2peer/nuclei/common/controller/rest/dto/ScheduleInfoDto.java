package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;
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

    /**
     * something like "2 часа 32 минуты"
     * @return
     */
    public String getDurationAsString() {
        if (start == null || end == null) {
            return "";
        }
        return CalendarUtils.getDurationAsString(LocaleContextHolder.getLocale(), start.toDate(), end.toDate());

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

    public String getNext() {
        return next;
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

    public void setStart(final DateTime start) {
        this.start = start;
    }

}
