package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.joda.time.DateTime;

public class EventDto {
    /**
     * Mo, Su, Sa, Th, etc
     */
    private String dayShortName;

    private DateTime start;

    private DateTime end;

    public String getDayShortName() {
        return dayShortName;
    }

    public DateTime getEnd() {
        return end;
    }

    public DateTime getStart() {
        return start;
    }

    public void setDayShortName(final String dayShortName) {
        this.dayShortName = dayShortName;
    }

    public void setEnd(final DateTime end) {
        this.end = end;
    }

    public void setStart(final DateTime start) {
        this.start = start;
    }
}
