package org.az.skill2peer.nuclei;

import static org.joda.time.DateTimeZone.UTC;

import java.util.ArrayList;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleEditDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.joda.time.DateTime;
import org.junit.Assert;

public class TestUtil {

    public static void compareDateTime(final DateTime at1, final DateTime at2) {
        final DateTime t1 = at1.withZone(UTC);
        final DateTime t2 = at2.withZone(UTC);

        Assert.assertNotNull(t1);
        Assert.assertNotNull(t2);
        compareTime(t1, t2);
        Assert.assertEquals(t1.getYear(), t2.getYear());
        Assert.assertEquals(t1.getMonthOfYear(), t2.getMonthOfYear());
        Assert.assertEquals(t1.getDayOfMonth(), t2.getDayOfMonth());
    }

    public static void compareTime(final DateTime at1, final DateTime at2) {
        final DateTime t1 = at1.withZone(UTC);
        final DateTime t2 = at2.withZone(UTC);

        Assert.assertNotNull(t1);
        Assert.assertNotNull(t2);
        Assert.assertEquals(t1.getMinuteOfHour(), t2.getMinuteOfHour());
        Assert.assertEquals(t1.getHourOfDay(), t2.getHourOfDay());

    }

    public static Course makeCourse(final int lessons, final boolean recurrent) {
        final Course course = new Course();
        course.setName("title");
        course.setDescription("description");
        course.setSummary("summary");
        course.setLessons(new ArrayList<Lesson>());
        for (int f = 0; f < lessons; f++) {
            final Lesson lesson = makeLesson(recurrent);
            course.getLessons().add(lesson);
        }
        return course;
    }

    public static CourseEditDto makeCourseEditDto() {
        final CourseEditDto courseDto = new CourseEditDto();
        courseDto.setName("title");
        courseDto.setDescription("description");
        courseDto.setSummary("summary");

        final LessonEditDto lesson = makeLessonEditDto();
        courseDto.getLessons().add(lesson);
        return courseDto;
    }

    public static Lesson makeLesson(final boolean recurrent) {
        final Lesson lesson = new Lesson();

        lesson.setDescription("lesson description");
        lesson.setSchedule(makeSchedule(recurrent));
        return lesson;
    }

    public static LessonEditDto makeLessonEditDto() {
        final LessonEditDto lesson = new LessonEditDto();
        lesson.setName("lesson name");
        lesson.setDescription("lesson description");
        lesson.setSchedule(makeScheduleEditDto());
        return lesson;
    }

    public static Schedule makeSchedule(final boolean recurrent) {
        final Schedule schedule = new Schedule();
        //        schedule.setDuration(120);
        //        final LocalDate now = LocalDate.now();
        schedule.setStart(new DateTime(2018, 11, 30, 23, 00));
        schedule.setEnd(schedule.getStart().plusMinutes(60));

        if (recurrent) {
            schedule.setiCalString("RRULE:FREQ=DAILY;"
                    + "INTERVAL=1;"
                    + "UNTIL=20230430T083000Z;");
        }
        schedule.setId(1);

        return schedule;
    }

    public static ScheduleEditDto makeScheduleEditDto() {
        final ScheduleEditDto scheduleDto = new ScheduleEditDto();
        //
        //        scheduleDto.setHours(10);
        //        scheduleDto.setMinutes(10);
        scheduleDto.setDuration(125);
        final Boolean[] repeat = { true, false, false, false, false, false, false };
        scheduleDto.setRepeatDays(repeat);
        //        final DateTimeEditDto dateTime = new DateTimeEditDto();
        //        dateTime.setHours(2);
        //        dateTime.setMinutes(45);
        //        dateTime.setStartDateStr("2017-12-10T21:00:00.000Z");
        final DateTime start = new DateTime("2017-12-10T2:45:00.000Z");
        scheduleDto.setDateTime(start.toString());

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
