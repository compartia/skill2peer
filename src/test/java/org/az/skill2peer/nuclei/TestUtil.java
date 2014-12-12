package org.az.skill2peer.nuclei;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleDto;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;

public class TestUtil {

    public static CourseEditDto makeCourseDto() {
        final CourseEditDto courseDto = new CourseEditDto();
        courseDto.setName("title");
        courseDto.setDescription("description");
        courseDto.setSummary("summary");

        final LessonEditDto lesson = makeLesson();
        courseDto.getLessons().add(lesson);
        return courseDto;
    }

    public static LessonEditDto makeLesson() {
        final LessonEditDto lesson = new LessonEditDto();
        lesson.setName("lesson name");
        lesson.setDescription("lesson description");
        lesson.setSchedule(makeScheduleDto());
        return lesson;
    }

    public static Schedule makeSchedule() {
        final Schedule schedule = new Schedule();
        //        schedule.setDuration(120);
        schedule.setStart(new DateTime(2020, 11, 25, 10, 00));
        schedule.setiCalString("RRULE:FREQ=DAILY;"
                + "INTERVAL=1;"
                + "UNTIL=20230430T083000Z;");
        schedule.setId(1);
        schedule.setEnd(new DateTime(2021, 11, 25, 10, 00));
        return schedule;
    }

    public static String makeStringOfLen(final int length) {
        final StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }

    private static ScheduleDto makeScheduleDto() {
        final ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setStart(new DateTime(2020, 11, 25, 10, 00));
        return scheduleDto;
    }

    private static final String CHARACTER = "a";
}
