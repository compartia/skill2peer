package org.az.skill2peer.nuclei.common.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoListItemDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LocationDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.PriceDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.UserInfoDto;
import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.springframework.context.i18n.LocaleContextHolder;

public class CourseInfoDto extends CourseInfoListItemDto {

    /**
     *  : duration of the entire course
     */
    private Integer totalDuration;

    private UserInfoDto author;

    private LocationDto location;

    private PriceDto price;

    private String description;

    private String skills;

    private Integer id;

    private boolean recurrent;

    private boolean single;

    private String name;

    private String summary;

    private List<DayEventsDto> weekSchedule;

    private List<LessonInfoDto> lessons = new ArrayList<LessonInfoDto>();

    @Override
    public UserInfoDto getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public List<LessonInfoDto> getLessons() {
        return lessons;
    }

    @Override
    public LocationDto getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PriceDto getPrice() {
        return price;
    }

    @Override
    public String getSkills() {
        return skills;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public String getTotalDurationAsString() {
        if (totalDuration == null) {
            return null;
        }
        return CalendarUtils.formatHoursDuration(LocaleContextHolder.getLocale(), totalDuration);
    }

    public List<DayEventsDto> getWeekSchedule() {
        return weekSchedule;
    }

    @Override
    public boolean isRecurrent() {
        return recurrent;
    }

    @Override
    public boolean isSingle() {
        return single;
    }

    public boolean isSingleLesson() {
        return this.lessons.size() == 1;
    }

    @Override
    public void setAuthor(final UserInfoDto author) {
        this.author = author;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLessons(final List<LessonInfoDto> lessons) {
        this.lessons = lessons;
    }

    @Override
    public void setLocation(final LocationDto location) {
        this.location = location;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setPrice(final PriceDto price) {
        this.price = price;
    }

    @Override
    public void setRecurrent(final boolean recurrent) {
        this.recurrent = recurrent;
    }

    @Override
    public void setSingle(final boolean single) {
        this.single = single;
    }

    @Override
    public void setSkills(final String skills) {
        this.skills = skills;
    }

    @Override
    public void setSummary(final String summary) {
        this.summary = summary;
    }

    //    private SortedSet<User> lectors;
    //
    //    private List<Lesson> lessons = new ArrayList<Lesson>();

    @Override
    public void setTotalDuration(final Integer duration) {
        this.totalDuration = duration;
    }

    public void setWeekSchedule(final List<DayEventsDto> weekSchedule) {
        this.weekSchedule = weekSchedule;
    }
}
