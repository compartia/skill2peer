package org.az.skill2peer.nuclei.common.controller.rest.dto;

import java.util.Comparator;
import java.util.TreeSet;

import org.az.skill2peer.nuclei.common.controller.dto.EventDto;

public class DayEventsDto {
    /**
     * Mo, Su, Sa, Th, etc
     */
    private String dayShortName;

    private final TreeSet<EventDto> events = new TreeSet<EventDto>(new Comparator<EventDto>() {
        @Override
        public int compare(final EventDto o1, final EventDto o2) {
            return o1.getStart().compareTo(o2.getStart());
        }
    });

    public void addEvent(final EventDto e) {
        events.add(e);
    }

    public String getDayShortName() {
        return dayShortName;
    }

    public TreeSet<EventDto> getEvents() {
        return events;
    }

    public void setDayShortName(final String dayShortName) {
        this.dayShortName = dayShortName;
    }

    @Override
    public String toString() {
        return dayShortName + " " + "events: " + events.size();
    }
}
