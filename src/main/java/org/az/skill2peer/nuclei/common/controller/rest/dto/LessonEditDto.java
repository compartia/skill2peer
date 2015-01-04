package org.az.skill2peer.nuclei.common.controller.rest.dto;

public class LessonEditDto {

    private LocationDto location = new LocationDto();

    private PriceDto price;

    private String description;

    private String skills;

    private Integer id;

    /**
     * @deprecated use location.id
     */
    @Deprecated
    private Integer locationId;

    private ScheduleEditDto schedule;

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

    public Integer getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public PriceDto getPrice() {
        return price;
    }

    public ScheduleEditDto getSchedule() {
        return schedule;
    }

    public String getSkills() {
        return skills;
    }

    public String getSummary() {
        return summary;
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

    public void setLocationId(final Integer locationId) {
        this.locationId = locationId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrice(final PriceDto price) {
        this.price = price;
    }

    public void setSchedule(final ScheduleEditDto schedule) {
        this.schedule = schedule;
    }

    public void setSkills(final String skills) {
        this.skills = skills;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

}
