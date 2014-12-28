package org.az.skill2peer.nuclei.services;

import java.util.List;

import org.az.skill2peer.nuclei.TestUtil;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseInfoListItemDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.DayEventsDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.EventDto;
import org.az.skill2peer.nuclei.common.controller.rest.dto.LessonEditDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseStatus;
import org.az.skill2peer.nuclei.common.model.Lesson;
import org.az.skill2peer.nuclei.common.model.Schedule;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

public class CourseServiceTest extends AbstractServiceTest {
    @Autowired
    CourseServiceImpl service;

    @Autowired
    Mapper mapper;

    @Before
    public void _setUp() {
        final User user = User.getBuilder().email("email").id(1111).build();
        SecurityUtil.logInUser(user);
    }

    @Transactional
    @Test
    @DatabaseSetup(value = "delete-course.xml")
    @ExpectedDatabase(value = "delete-course.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void cloneCourse() throws Exception {
        final Course course = service.getCourse(1);
        final Course cloneCourse = service.cloneCourse(1);

        Assert.assertNotNull(cloneCourse.getLessons());
        Assert.assertTrue(!cloneCourse.getLessons().isEmpty());

        final Lesson lesson1 = course.getLessons().get(0);
        final Lesson lesson2 = cloneCourse.getLessons().get(0);
        Assert.assertTrue(lesson1 != lesson2);
        Assert.assertTrue(lesson1.getSchedule() != lesson2.getSchedule());

    }

    @Transactional
    @Test
    @DatabaseSetup(value = "create-course.xml")
    @ExpectedDatabase(value = "create-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createCourse() throws Exception {
        final CourseEditDto courseDto = TestUtil.makeCourseEditDto();
        final CourseEditDto c = service.createCourse(courseDto);

        Assert.assertNotNull(c.getId());
    }

    @Transactional
    @Test
    @DatabaseSetup(value = "delete-course.xml")
    @ExpectedDatabase(value = "delete-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteCourse() throws Exception {
        service.deleteCourse(1);
    }

    /**
     * expect that course will be cloned and its status is DRAFT
     * @throws Exception
     */
    @Transactional
    @Test
    @DatabaseSetup(value = "edit-course.xml")
    @ExpectedDatabase(value = "edit-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void editCourse() throws Exception {
        final Course editCourse = service.editCourse(1);

        Assert.assertNotNull(editCourse.getPublishedVersion());
        Assert.assertEquals(1, editCourse.getPublishedVersion().getId().intValue());
        Assert.assertNotNull(editCourse.getPublishedVersion().getDraft());
        Assert.assertEquals(CourseStatus.DRAFT, editCourse.getStatus());
    }

    @Transactional
    @Test
    @DatabaseSetup(value = "edit-draft-course.xml")
    public void editDraftCourse() throws Exception {
        final Course editCourse = service.editCourse(72);

        Assert.assertNotNull(editCourse.getPublishedVersion());
        Assert.assertEquals(72, editCourse.getId().intValue());
        Assert.assertNotNull(editCourse.getPublishedVersion().getDraft());
        Assert.assertEquals(CourseStatus.DRAFT, editCourse.getStatus());
    }

    @Transactional
    @Test
    @DatabaseSetup(value = "edit-draft-course.xml")
    public void editDraftPublishedCourse() throws Exception {
        final Course editCourse = service.editCourse(71);

        Assert.assertNotNull(editCourse.getPublishedVersion());
        Assert.assertEquals(72, editCourse.getId().intValue());
        Assert.assertNotNull(editCourse.getPublishedVersion().getDraft());
        Assert.assertEquals(CourseStatus.DRAFT, editCourse.getStatus());
    }

    @Test
    @DatabaseSetup(value = "delete-course.xml")
    public void getCourse() throws Exception {
        final Course course = service.getCourse(1);

        Assert.assertNotNull(course.getLessons());
        Assert.assertNotNull(course.getAuthor());
        Assert.assertNotNull(course.getAuthor().getFirstName());
        Assert.assertTrue(!course.getLessons().isEmpty());

        final Lesson lesson1 = course.getLessons().get(0);
        final Schedule schedule = lesson1.getSchedule();

        Assert.assertEquals(1, schedule.getId().intValue());
        Assert.assertEquals("RRULE:FREQ=DAILY;WKST=MO;UNTIL=20300101", schedule.getiCalString());

    }

    @Test
    @DatabaseSetup(value = "course-schedule-1.xml")
    public void getCourseWeekSchedule() throws Exception {

        final Course source = service.getCourse(1);
        final Schedule schedule = source.getLessons().get(0).getSchedule();
        final DateTime start = schedule.getStart();

        Assert.assertTrue(start.isBefore(DateTime.now()));
        final CourseInfoDto course = new CourseInfoDto();

        mapper.map(source, course);

        TestUtil.compareTime(start, course.getLessons().get(0).getSchedule().getStart());

        final List<DayEventsDto> weekSchedule = course.getWeekSchedule();
        Assert.assertEquals(7, weekSchedule.size());

        final DayEventsDto eventsOnMonday = weekSchedule.get(0);
        Assert.assertEquals(1, eventsOnMonday.getEvents().size());
        final EventDto first = eventsOnMonday.getEvents().first();

        Assert.assertEquals(DateTimeConstants.MONDAY, first.getStart().getDayOfWeek());

        TestUtil.compareTime(schedule.getEnd(), first.getEnd());
        TestUtil.compareTime(schedule.getStart(), first.getStart());

    }

    @Transactional
    @Test
    @DatabaseSetup(value = "edit-draft-course.xml")
    public void getEditableCourse() throws Exception {
        final CourseEditDto editableCourse = service.getEditableCourse(71);
        Assert.assertEquals(CourseStatus.DRAFT, editableCourse.getStatus());
        Assert.assertEquals(72, editableCourse.getId().intValue());
        final List<LessonEditDto> lessons = editableCourse.getLessons();
        Assert.assertNotNull(lessons);
        Assert.assertEquals(1, lessons.size());
        final LessonEditDto lessonEditDto = lessons.get(0);
        Assert.assertEquals(1, lessonEditDto.getId().intValue());
    }

    @Test
    @DatabaseSetup(value = "course-schedule-1.xml")
    public void getEventsWithinWeek() throws Exception {
        final DateTime now = DateTime.now();
        final Course source = service.getCourse(1);

        final Lesson lesson = source.getLessons().get(0);

        final Schedule schedule = lesson.getSchedule();
        Assert.assertTrue(schedule.isRecurrent());

        Assert.assertTrue(schedule.getStart().isBefore(now));
        final List<EventDto> eventsWithinWeek = schedule.getEventsWithinWeek(now);
        Assert.assertEquals(1, eventsWithinWeek.size());
    }

    @Transactional
    @Test
    @DatabaseSetup(value = "delete-course.xml")
    public void getMyCourses() {
        final List<CourseInfoListItemDto> myCourses = service.getMyCourses();
        Assert.assertEquals(2, myCourses.size());

        final CourseInfoListItemDto courseInfoListItemDto = myCourses.get(0);

        Assert.assertEquals("summary", courseInfoListItemDto.getSummary());
        Assert.assertEquals("title", courseInfoListItemDto.getName());
        Assert.assertNotNull(courseInfoListItemDto.getScheduleInfo());
        Assert.assertNotNull(courseInfoListItemDto.getStatus());
    }

    @Transactional
    @Test
    @DatabaseSetup(value = "edit-draft-course.xml")
    @ExpectedDatabase(value = "edit-draft-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateDraftCourse() throws Exception {
        final CourseEditDto editableCourse = service.getEditableCourse(71);

        editableCourse.setDescription("description edited");
        final LessonEditDto lessonEditDto = editableCourse.getLessons().get(0);

        editableCourse.getLessons().add(TestUtil.makeLessonEditDto());

        lessonEditDto.getLocation().setDescription("description_edited");
        service.updateCourse(editableCourse);

        //
        final CourseEditDto editableCourse2 = service.getEditableCourse(72);
        Assert.assertEquals("description edited", editableCourse2.getDescription());
    }

    @Transactional
    @Test(expected = IllegalStateException.class)
    @DatabaseSetup(value = "edit-draft-course.xml")
    @ExpectedDatabase(value = "edit-draft-course.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updatePublishedCourse_shouldFail() throws Exception {

        final CourseEditDto dto = TestUtil.makeCourseEditDto();
        dto.setId(71);
        service.updateCourse(dto);

    }
}
