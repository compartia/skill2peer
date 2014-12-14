package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.joda.time.LocalDateTime;

public class EventDto {
    /**
     * Mo, Su, Sa, Th, etc
     */
    private String dayShortName;

    private LocalDateTime start;

    private LocalDateTime end;

    public String getDayShortName() {
        return dayShortName;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setDayShortName(final String dayShortName) {
        this.dayShortName = dayShortName;
    }

    public void setEnd(final LocalDateTime end) {
        this.end = end;
    }

    public void setStart(final LocalDateTime start) {
        this.start = start;
    }
}
