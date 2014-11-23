package org.az.skill2peer.nuclei.services;

import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseStatus;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
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
    CourseService service;

    @Before
    public void _setUp() {
        final User user = User.getBuilder().email("email").id(1111).build();
        SecurityUtil.logInUser(user);
    }

    @Test
    @DatabaseSetup(value = "create-course.xml")
    @ExpectedDatabase(value = "create-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createCourse() throws Exception {
        final CourseEditDto courseDto = buildCourse();
        final Course c = service.createCourse(courseDto);

        Assert.assertNotNull(c.getId());
    }

    /**
     * expect that course will be cloned and its status is DRAFT
     * @throws Exception
     */
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

    @Test
    @DatabaseSetup(value = "edit-draft-course.xml")
    public void editDraftPublishedCourse() throws Exception {
        final Course editCourse = service.editCourse(71);

        Assert.assertNotNull(editCourse.getPublishedVersion());
        Assert.assertEquals(72, editCourse.getId().intValue());
        Assert.assertNotNull(editCourse.getPublishedVersion().getDraft());
        Assert.assertEquals(CourseStatus.DRAFT, editCourse.getStatus());
    }

    //@Transactional
    //@Ignore
    @Test
    @DatabaseSetup(value = "edit-draft-course.xml")
    @ExpectedDatabase(value = "edit-draft-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateDraftCourse() throws Exception {
        final CourseEditDto editableCourse = service.getEditableCourse(71);
        Assert.assertEquals(CourseStatus.DRAFT, editableCourse.getStatus());
        Assert.assertEquals(72, editableCourse.getId().intValue());
        editableCourse.setDescription("description edited");
        service.updateCourse(editableCourse);

        //
        final CourseEditDto editableCourse2 = service.getEditableCourse(72);
        Assert.assertEquals("description edited", editableCourse2.getDescription());
    }

    private CourseEditDto buildCourse() {
        final CourseEditDto courseDto = new CourseEditDto();
        courseDto.setName("title");
        courseDto.setDescription("description");
        courseDto.setSummary("summary");
        return courseDto;
    }

}
