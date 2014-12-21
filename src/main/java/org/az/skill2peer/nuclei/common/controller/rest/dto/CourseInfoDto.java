package org.az.skill2peer.nuclei.common.controller.rest.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.az.skill2peer.nuclei.services.CalendarUtils;
import org.springframework.context.i18n.LocaleContextHolder;

public class CourseInfoDto {

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

    private ScheduleInfoDto schedule;

    private String name;

    private String summary;

    private List<DayEventsDto> weekSchedule;

    private List<LessonInfoDto> lessons = new ArrayList<LessonInfoDto>();

    public static final Comparator<LessonInfoDto> SCHEDULE_COMPARATOR = new Comparator<LessonInfoDto>() {
        @Override
        public int compare(final LessonInfoDto s1, final LessonInfoDto s2) {
            return s1.getSchedule().getNextEvent().compareTo(s2.getSchedule().getNextEvent());
        }
    };

    public UserInfoDto getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public List<LessonInfoDto> getLessons() {
        return lessons;
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
        final ScheduleInfoDto s = new ScheduleInfoDto();

        final ArrayList<LessonInfoDto> scs = new ArrayList<LessonInfoDto>(getLessons());
        Collections.sort(scs, SCHEDULE_COMPARATOR);

        s.setNextEvent(scs.get(0).getSchedule().getNextEvent());
        s.setStart(scs.get(0).getSchedule().getNextEvent());
        s.setEnd(scs.get(scs.size() - 1).getSchedule().getEnd());
        return s;
    }

    public String getSkills() {
        return skills;
    }

    public String getSummary() {
        return summary;
    }

    public String getTotalDurationAsString() {
        if (totalDuration == null) {
            return "";
        }
        //        final DateTime from = new DateTime();
        //        DateTime to = new DateTime(from);
        //        to = to.plusMinutes(totalDuration);
        return CalendarUtils.formatHoursDuration(LocaleContextHolder.getLocale(), totalDuration);
        //        return CalendarUtils.formatPeriod(LocaleContextHolder.getLocale(),
        //                from.toDate(),
        //                to.toDate());

    }

    public List<DayEventsDto> getWeekSchedule() {
        return weekSchedule;
    }

    public boolean isSingleLesson() {
        return this.lessons.size() == 1;
    }

    public void setAuthor(final UserInfoDto author) {
        this.author = author;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLessons(final List<LessonInfoDto> lessons) {
        this.lessons = lessons;
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

    public void setSchedule(final ScheduleInfoDto schedule) {
        this.schedule = schedule;
    }

    public void setSkills(final String skills) {
        this.skills = skills;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    //    private SortedSet<User> lectors;
    //
    //    private List<Lesson> lessons = new ArrayList<Lesson>();

    public void setTotalDuration(final Integer duration) {
        this.totalDuration = duration;
    }

    public void setWeekSchedule(final List<DayEventsDto> weekSchedule) {
        this.weekSchedule = weekSchedule;
    }
}
