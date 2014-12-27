package org.az.skill2peer.nuclei.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
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
import org.joda.time.DateTimeZone;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        Assert.assertEquals("RRULE:FREQ=WEEKLY;BYDAY=MO", lesson.getSchedule().getiCalString());
        /**
         * total duration is undefined because course has recurrent lessons
         */
        Assert.assertEquals(null, course.getTotalDuration());

        compareLessons(lessonEditDto, lesson);
    }

    @Test
    public void testCourseMappingToDto() {
        final int numberOfLessons = 48;
        final Course source = TestUtil.makeCourse(numberOfLessons, false);
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
        final Course source = TestUtil.makeCourse(2, false);

        final Schedule schedule = source.getLessons().get(0).getSchedule();
        schedule.setEnd(schedule.getStart().plusMinutes(10));

        final Schedule schedule2 = source.getLessons().get(1).getSchedule();
        schedule2.setEnd(schedule2.getStart().plusMinutes(5 * 60 + 5));

        final CourseInfoDto target = new CourseInfoDto();
        mapper.map(source, target);

        Assert.assertEquals("5 часов 15 минут", target.getTotalDurationAsString());
        Assert.assertEquals(false, target.isRecurrent());
        Assert.assertEquals(false, target.isSingle());

        final Course source2 = TestUtil.makeCourse(1, false);
        Assert.assertEquals(true, source2.isSingle());
    }

    @Test
    public void testDurationMapping3() {
        final CourseInfoDto target = new CourseInfoDto();
        final Course source = TestUtil.makeCourse(2, false);

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
        final Course source = TestUtil.makeCourse(2, true);

        final Schedule schedule = source.getLessons().get(0).getSchedule();
        schedule.setEnd(schedule.getStart().plusMinutes(10));

        final Schedule schedule2 = source.getLessons().get(1).getSchedule();
        schedule2.setEnd(null);
        schedule2.setStart(null);

        final CourseInfoDto target = new CourseInfoDto();
        mapper.map(source, target);

        Assert.assertEquals(null, target.getTotalDurationAsString());

    }

    @Test
    public void testJsonDatesMapping() throws JsonParseException, JsonMappingException, IOException {

        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/courseCreate.json");
        Assert.assertNotNull(resourceAsStream);
        final String myResource = IOUtils.toString(resourceAsStream);

        final ObjectMapper mapper = new ObjectMapper();
        final CourseEditDto user = mapper.readValue(myResource, CourseEditDto.class);

        final DateTime start = user.getLessons().get(0).getSchedule().getStart();
        Assert.assertEquals("2014-12-27T19:15:00.000Z", start.withZone(DateTimeZone.UTC).toString());
    }

    @Test
    public void testScheduleEditDto() {
        final ScheduleEditDto dto = TestUtil.makeScheduleEditDto();

        Assert.assertEquals("RRULE:FREQ=WEEKLY;BYDAY=MO", dto.getiCalString());

        final Boolean[] expecteds = { true, true, false, false, false, false, false };
        dto.setiCalString("RRULE:FREQ=WEEKLY;BYDAY=MO,TU");
        Assert.assertArrayEquals(expecteds, dto.getRepeatDays());
    }

    @Test
    public void testScheduleFromDto() {
        final ScheduleEditDto s1 = TestUtil.makeScheduleEditDto();
        final Schedule s2 = new Schedule();

        mapper.map(s1, s2);

        compareSchedules(s1, s2);
    }

    @Test
    public void testScheduleToInfoDtoMapping() {

        final ScheduleInfoDto target = new ScheduleInfoDto();
        final Schedule source = TestUtil.makeSchedule(false);
        source.setEnd(source.getStart().plusHours(2).plusMinutes(32));
        mapper.map(source, target);
        compareSchedules(target, source);

        Assert.assertEquals("2 часа 32 минуты", target.getDurationAsString());
        Assert.assertEquals("ноября", target.getStartMonth());
        Assert.assertEquals("декабря", target.getEndMonth());

        target.setEnd(null);
        Assert.assertEquals("", target.getDurationAsString());

    }

    private void compareLessons(final LessonEditDto l1, final Lesson l2) {
        Assert.assertEquals(l1.getDescription(), l2.getDescription());

        compareSchedules(l1.getSchedule(), l2.getSchedule());
    }

    private void compareSchedules(final ScheduleEditDto s1, final Schedule s2) {
        final DateTime t1 = s1.getStart();
        final DateTime t2 = s2.getStart();
        TestUtil.compareDateTime(t1, t2);
    }

    private void compareSchedules(final ScheduleInfoDto s1, final Schedule s2) {
        TestUtil.compareDateTime(s1.getStart(), s2.getStart());
        TestUtil.compareDateTime(s1.getEnd(), s2.getEnd());

    }

}
