package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.joda.time.DateTime;

public class EventDto {
    /**
     * Mo, Su, Sa, Th, etc
     */
    @Deprecated
    private String dayShortName;

    private DateTime start;

    private DateTime end;

    private String name;

    @Deprecated
    public String getDayShortName() {
        return dayShortName;
    }

    public DateTime getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }

    public DateTime getStart() {
        return start;
    }

    @Deprecated
    public void setDayShortName(final String dayShortName) {
        this.dayShortName = dayShortName;
    }

    public void setEnd(final DateTime end) {
        this.end = end;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStart(final DateTime start) {
        this.start = start;
    }
}
