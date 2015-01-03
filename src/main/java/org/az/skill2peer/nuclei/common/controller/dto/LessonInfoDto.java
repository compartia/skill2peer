package org.az.skill2peer.nuclei.common.controller.dto;

import org.az.skill2peer.nuclei.common.controller.rest.dto.LocationDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.PriceDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleInfoDto;

public class LessonInfoDto {

    private LocationDto location;

    private PriceDto price;

    private String description;

    private String skills;

    private Integer id;

    /**
     * take it from schedule
     */
    @Deprecated
    private boolean recurrent;

    private ScheduleInfoDto schedule;

    private String name;

    private String summary;

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public LocationDto getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public PriceDto getPrice() {
        return price;
    }

    public ScheduleInfoDto getSchedule() {
        return schedule;
    }

    public String getSkills() {
        return skills;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLocation(final LocationDto location) {
        this.location = location;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrice(final PriceDto price) {
        this.price = price;
    }

    public void setRecurrent(final boolean recurrent) {
        this.recurrent = recurrent;
    }

    public void setSchedule(final ScheduleInfoDto schedule) {
        this.schedule = schedule;
    }

    public void setSkills(final String skills) {
        this.skills = skills;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

}
