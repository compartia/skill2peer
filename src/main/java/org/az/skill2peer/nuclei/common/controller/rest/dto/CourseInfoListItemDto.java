package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.az.skill2peer.nuclei.common.model.CourseStatus;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * used for displaying list of courses
 * @author Artem Zaborskiy
 *
 */
public class CourseInfoListItemDto {
    private CourseStatus status;

    private ScheduleInfoDto scheduleInfo;

    /**
     *  : duration of the entire course
     */
    private Integer totalDuration;

    private UserInfoDto author;

    private LocationDto location;

    private PriceDto price;

    private String skills;

    private Integer id;

    private boolean recurrent;

    private boolean single;

    private String name;

    private String summary;

    public UserInfoDto getAuthor() {
        return author;
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

    public ScheduleInfoDto getScheduleInfo() {
        return scheduleInfo;
    }

    public String getSkills() {
        return skills;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public String getTotalDurationAsString() {
        if (totalDuration == null) {
            return null;
        }
        return CalendarUtils.formatHoursDuration(LocaleContextHolder.getLocale(), totalDuration);
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    public boolean isSingle() {
        return single;
    }

    public void setAuthor(final UserInfoDto author) {
        this.author = author;
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

    public void setScheduleInfo(final ScheduleInfoDto scheduleInfo) {
        this.scheduleInfo = scheduleInfo;
    }

    public void setSingle(final boolean single) {
        this.single = single;
    }

    public void setSkills(final String skills) {
        this.skills = skills;
    }

    public void setStatus(final CourseStatus status) {
        this.status = status;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public void setTotalDuration(final Integer duration) {
        this.totalDuration = duration;
    }

}
