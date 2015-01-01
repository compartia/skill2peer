package org.az.skill2peer.nuclei.common.controller.rest.dto;

import static org.az.skill2peer.nuclei.services.LocalDateRenderingUtils.getMonthLongName;

import org.az.skill2peer.nuclei.common.controller.dto.EventDto;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.az.skill2peer.nuclei.services.LocalDateRenderingUtils;
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

    @JsonIgnore
    private DateTime start;

    @JsonIgnore
    private DateTime end;

    private EventDto nextEvent;

    public String getDates() {
        return LocalDateRenderingUtils.getDates(start, end);
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

    }

    public DateTime getEnd() {
        return end;
    }

    public String getEndMonth() {
        return getMonthLongName(getEnd());
    }

    public EventDto getNextEvent() {
        return nextEvent;
    }

    public DateTime getStart() {
        return start;
    }

    public String getStartMonth() {
        return getMonthLongName(getStart());
    }

    public void setEnd(final DateTime end) {
        this.end = end;
    }

    public void setNextEvent(final EventDto nextEvent) {
        this.nextEvent = nextEvent;
    }

    public void setStart(final DateTime start) {
        this.start = start;
    }

}
