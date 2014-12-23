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

    private boolean recurrent;

    private boolean single;

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
        final LessonInfoDto firstLesson = scs.get(0);
        final LessonInfoDto lastLesson = scs.get(scs.size() - 1);

        s.setNextEvent(firstLesson.getSchedule().getNextEvent());
        if (!isRecurrent()) {
            s.setStart(firstLesson.getSchedule().getNextEvent());
            s.setEnd(lastLesson.getSchedule().getEnd());
        } else {
            s.setStart(firstLesson.getSchedule().getStart());
        }

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
            return null;
        }
        return CalendarUtils.formatHoursDuration(LocaleContextHolder.getLocale(), totalDuration);
    }

    public List<DayEventsDto> getWeekSchedule() {
        return weekSchedule;
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    public boolean isSingle() {
        return single;
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

    public void setRecurrent(final boolean recurrent) {
        this.recurrent = recurrent;
    }

    public void setSchedule(final ScheduleInfoDto schedule) {
        this.schedule = schedule;
    }

    public void setSingle(final boolean single) {
        this.single = single;
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
