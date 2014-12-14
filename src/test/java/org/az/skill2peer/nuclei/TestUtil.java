package org.az.skill2peer.nuclei;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DateTimeEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class TestUtil {

    public static CourseEditDto makeCourseEditDto() {
        final CourseEditDto courseDto = new CourseEditDto();
        courseDto.setName("title");
        courseDto.setDescription("description");
        courseDto.setSummary("summary");

        final LessonEditDto lesson = makeLessonEditDto();
        courseDto.getLessons().add(lesson);
        return courseDto;
    }

    public static LessonEditDto makeLessonEditDto() {
        final LessonEditDto lesson = new LessonEditDto();
        lesson.setName("lesson name");
        lesson.setDescription("lesson description");
        lesson.setSchedule(makeScheduleDto());
        return lesson;
    }

    public static Schedule makeSchedule() {
        final Schedule schedule = new Schedule();
        //        schedule.setDuration(120);
        final LocalDate now = LocalDate.now();
        schedule.setStart(new DateTime(now.getYear() + 1, 11, 25, 10, 00));
        schedule.setiCalString("RRULE:FREQ=DAILY;"
                + "INTERVAL=1;"
                + "UNTIL=20230430T083000Z;");
        schedule.setId(1);
        schedule.setEnd(new DateTime(2021, 11, 25, 10, 00));
        return schedule;
    }

    public static ScheduleDto makeScheduleDto() {
        final ScheduleDto scheduleDto = new ScheduleDto();
        //
        //        scheduleDto.setHours(10);
        //        scheduleDto.setMinutes(10);
        scheduleDto.setDuration(125);
        final DateTimeEditDto dateTime = new DateTimeEditDto();
        dateTime.setHours(2);
        dateTime.setMinutes(45);
        dateTime.setStartDateStr("2017-12-10T21:00:00.000Z");
        scheduleDto.setDateTime(dateTime);

        //(new DateTime(2020, 11, 25, 10, 00));
        return scheduleDto;
    }

    public static String makeStringOfLen(final int length) {
        final StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }

    private static final String CHARACTER = "a";
}
