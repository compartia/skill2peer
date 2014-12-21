package org.az.skill2peer.nuclei.model;

import java.util.Locale;

import org.az.skill2peer.nuclei.TestUtil;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleInfoDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.az.skill2peer.nuclei.config.DozerConfig;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        DozerConfig.class
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class
})
@ActiveProfiles(profiles = "test")
public class CourseMappingTest {
    @Autowired
    Mapper mapper;

    @Before
    public void _setUp() {
        LocaleContextHolder.setLocale(new Locale.Builder().setLanguage("ru").build());
    }

    @Test
    public void testCourseMappingFromDto() {
        final CourseEditDto c = TestUtil.makeCourseEditDto();
        final Course course = new Course();

        mapper.map(c, course);

        final LessonEditDto lessonEditDto = c.getLessons().get(0);
        final Lesson lesson = course.getLessons().get(0);

        Assert.assertEquals(Integer.valueOf(125), course.getTotalDuration());

        compareLessons(lessonEditDto, lesson);
    }

    @Test
    public void testCourseMappingToDto() {
        final int numberOfLessons = 48;
        final Course source = TestUtil.makeCourse(numberOfLessons);
        final CourseInfoDto target = new CourseInfoDto();

        mapper.map(source, target);

        Assert.assertEquals(numberOfLessons, target.getLessons().size());
        Assert.assertEquals("48 часов", target.getTotalDurationAsString());
        Assert.assertEquals(7, target.getWeekSchedule().size());
    }

    @Test
    public void testDtoFromSchedule() {
        final ScheduleEditDto s2 = new ScheduleEditDto();
        final Schedule s1 = new Schedule();
        s1.setStart(new DateTime(2017, 12, 16, 15, 45));

        mapper.map(s1, s2);

        compareSchedules(s2, s1);
    }

    @Test
    public void testDurationMapping1() {
        final Course source = TestUtil.makeCourse(2);

        final Schedule schedule = source.getLessons().get(0).getSchedule();
        schedule.setEnd(schedule.getStart().plusMinutes(10));

        final Schedule schedule2 = source.getLessons().get(1).getSchedule();
        schedule2.setEnd(schedule2.getStart().plusMinutes(5 * 60 + 5));

        final CourseInfoDto target = new CourseInfoDto();
        mapper.map(source, target);

        Assert.assertEquals("5 часов 15 минут", target.getTotalDurationAsString());

    }

    @Test
    public void testDurationMapping3() {
        final CourseInfoDto target = new CourseInfoDto();
        final Course source = TestUtil.makeCourse(2);

        final Schedule schedule = source.getLessons().get(0).getSchedule();
        schedule.setEnd(schedule.getStart().plusMinutes(120));

        final Schedule schedule2 = source.getLessons().get(1).getSchedule();
        {
            schedule2.setEnd(schedule2.getStart().plusMinutes(120));
            mapper.map(source, target);
            Assert.assertEquals("4 часа", target.getTotalDurationAsString());
        }

        {
            schedule2.setEnd(schedule2.getStart().plusMinutes(19 * 60));
            mapper.map(source, target);
            Assert.assertEquals("21 час", target.getTotalDurationAsString());
        }

        {
            schedule2.setEnd(schedule2.getStart().plusMinutes(2 + 19 * 60));
            mapper.map(source, target);
            Assert.assertEquals("21 час 2 минуты", target.getTotalDurationAsString());
        }

        {
            schedule2.setEnd(schedule2.getStart().plusMinutes(10 + 19 * 60));
            mapper.map(source, target);
            Assert.assertEquals("21 час 10 минут", target.getTotalDurationAsString());
        }
    }

    @Test
    public void testDurationMappingUndefinedSchedule() {
        final Course source = TestUtil.makeCourse(2);

        final Schedule schedule = source.getLessons().get(0).getSchedule();
        schedule.setEnd(schedule.getStart().plusMinutes(10));

        final Schedule schedule2 = source.getLessons().get(1).getSchedule();
        schedule2.setEnd(null);
        schedule2.setStart(null);

        final CourseInfoDto target = new CourseInfoDto();
        mapper.map(source, target);

        Assert.assertEquals("", target.getTotalDurationAsString());

    }

    @Test
    public void testScheduleFromDto() {
        final ScheduleEditDto s1 = TestUtil.makeScheduleDto();
        final Schedule s2 = new Schedule();

        mapper.map(s1, s2);

        compareSchedules(s1, s2);
    }

    @Test
    public void testScheduleToInfoDtoMapping() {

        final ScheduleInfoDto target = new ScheduleInfoDto();
        final Schedule source = TestUtil.makeSchedule();
        source.setEnd(source.getStart().plusHours(2).plusMinutes(32));
        mapper.map(source, target);
        compareSchedules(target, source);

        Assert.assertEquals("2 часа 32 минуты", target.getDurationAsString());
        Assert.assertEquals("ноября", target.getStartMonth());
        Assert.assertEquals("декабря", target.getEndMonth());

        target.setEnd(null);
        Assert.assertEquals("", target.getDurationAsString());

    }

    private void compareDates(final DateTime t1, final DateTime t2) {
        Assert.assertNotNull(t1);
        Assert.assertNotNull(t2);
        Assert.assertEquals(t1.getMinuteOfHour(), t2.getMinuteOfHour());
        Assert.assertEquals(t1.getHourOfDay(), t2.getHourOfDay());
        Assert.assertEquals(t1.getYear(), t2.getYear());
        Assert.assertEquals(t1.getMonthOfYear(), t2.getMonthOfYear());
        Assert.assertEquals(t1.getDayOfMonth(), t2.getDayOfMonth());
    }

    private void compareLessons(final LessonEditDto l1, final Lesson l2) {
        Assert.assertEquals(l1.getDescription(), l2.getDescription());

        compareSchedules(l1.getSchedule(), l2.getSchedule());
    }

    private void compareSchedules(final ScheduleEditDto s1, final Schedule s2) {
        final DateTime t1 = s1.getDateTime().toDateTime();
        final DateTime t2 = s2.getStart();
        compareDates(t1, t2);
    }

    private void compareSchedules(final ScheduleInfoDto s1, final Schedule s2) {
        compareDates(s1.getStart(), s2.getStart());
        compareDates(s1.getEnd(), s2.getEnd());

    }

}
