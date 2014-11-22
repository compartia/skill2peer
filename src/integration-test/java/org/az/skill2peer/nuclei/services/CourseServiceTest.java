package org.az.skill2peer.nuclei.services;

import org.az.skill2peer.ColumnSensingFlatXMLDataSetLoader;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.common.model.Course;
import org.az.skill2peer.nuclei.common.model.CourseStatus;
import org.az.skill2peer.nuclei.config.PersistenceContext;
import org.az.skill2peer.nuclei.config.Skill2PeerApplicationContext;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        ServicesTestContext.class,
        Skill2PeerApplicationContext.class,
        PersistenceContext.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
@ActiveProfiles(profiles = "test")
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = )
//@Transactional
public class CourseServiceTest {
    @Autowired
    CourseService service;

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
        final Course editCourse = service.editCourse(2);

        Assert.assertNotNull(editCourse.getPublishedVersion());
        Assert.assertEquals(2, editCourse.getId().intValue());
        Assert.assertNotNull(editCourse.getPublishedVersion().getDraft());
        Assert.assertEquals(CourseStatus.DRAFT, editCourse.getStatus());
    }

    @Test
    @DatabaseSetup(value = "edit-draft-course.xml")
    public void editDraftPublishedCourse() throws Exception {
        final Course editCourse = service.editCourse(1);

        Assert.assertNotNull(editCourse.getPublishedVersion());
        Assert.assertEquals(2, editCourse.getId().intValue());
        Assert.assertNotNull(editCourse.getPublishedVersion().getDraft());
        Assert.assertEquals(CourseStatus.DRAFT, editCourse.getStatus());
    }

    @Before
    public void setUp() {
        final User user = User.getBuilder().email("email").id(1111).build();
        SecurityUtil.logInUser(user);
    }

    private CourseEditDto buildCourse() {
        final CourseEditDto courseDto = new CourseEditDto();
        courseDto.setName("title");
        courseDto.setDescription("description");
        courseDto.setSummary("summary");
        return courseDto;
    }

}
