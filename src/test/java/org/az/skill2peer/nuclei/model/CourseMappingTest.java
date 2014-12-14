package org.az.skill2peer.nuclei.model;

import org.az.skill2peer.nuclei.TestUtil;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.ScheduleDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.az.skill2peer.nuclei.config.DozerConfig;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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

    @Test
    public void testCourseMappingFromDto() {
        final CourseEditDto c = TestUtil.makeCourseEditDto();
        final Course course = new Course();
        mapper.map(c, course);

        final LessonEditDto lessonEditDto = c.getLessons().get(0);
        final Lesson lesson = course.getLessons().get(0);

        compareLessons(lessonEditDto, lesson);
    }

    @Test
    public void testDtoFromSchedule() {
        final ScheduleDto s2 = new ScheduleDto();
        final Schedule s1 = new Schedule();
        s1.setStart(new DateTime(2017, 12, 16, 15, 45));

        mapper.map(s1, s2);

        compareScedules(s2, s1);
    }

    @Test
    public void testScheduleFromDto() {
        final ScheduleDto s1 = TestUtil.makeScheduleDto();
        final Schedule s2 = new Schedule();

        mapper.map(s1, s2);

        compareScedules(s1, s2);
    }

    private void compareLessons(final LessonEditDto l1, final Lesson l2) {
        Assert.assertEquals(l1.getDescription(), l2.getDescription());
        //        Assert.assertEquals(lessonEditDto.getDescription(), lesson.get);

        compareScedules(l1.getSchedule(), l2.getSchedule());
    }

    private void compareScedules(final ScheduleDto s1, final Schedule s2) {
        final DateTime t1 = s1.getDateTime().toDateTime();
        final DateTime t2 = s2.getStart();
        Assert.assertEquals(t1.getMinuteOfHour(), t2.getMinuteOfHour());
        Assert.assertEquals(t1.getHourOfDay(), t2.getHourOfDay());
        Assert.assertEquals(t1.getYear(), t2.getYear());
        Assert.assertEquals(t1.getMonthOfYear(), t2.getMonthOfYear());
        Assert.assertEquals(t1.getDayOfMonth(), t2.getDayOfMonth());
    }

}
