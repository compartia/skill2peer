package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.joda.time.DateTime;

public class EventDto {

    private DateTime start;

    private DateTime end;

    private String name;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventDto other = (EventDto)obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (start == null) {
            if (other.start != null) {
                return false;
            }
        } else if (!start.equals(other.start)) {
            return false;
        }
        return true;
    }

    /**
     * Mo, Su, Sa, Th, etc
     */
    public String getDayShortName() {
        return CalendarUtils.getDayShortNameLocal(start);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
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
