package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.joda.time.DateTime;

public class EventDto {
    /**
     * Mo, Su, Sa, Th, etc

     * @deprecated because this value should be derived via start or from group of events
     * @param dayShortName
     */
    @Deprecated
    private String dayShortName;

    private DateTime start;

    private DateTime end;

    private String name;

    public String getDayShortName() {
        //XXX: calculate it
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

    /**
     * @deprecated because this value should be derived via start or from group of events
     * @param dayShortName
     */
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
