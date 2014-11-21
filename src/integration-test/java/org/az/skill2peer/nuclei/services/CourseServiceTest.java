package org.az.skill2peer.nuclei.services;

import org.az.skill2peer.ColumnSensingFlatXMLDataSetLoader;
import org.az.skill2peer.nuclei.common.controller.rest.dto.CourseEditDto;
import org.az.skill2peer.nuclei.config.PersistenceContext;
import org.az.skill2peer.nuclei.config.Skill2PeerApplicationContext;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
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
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
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
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
@ActiveProfiles(profiles = "test")
public class CourseServiceTest {
    @Autowired
    CourseService service;

    @Test
    @DatabaseSetup("create-course.xml")
    @ExpectedDatabase(value = "create-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createCourse() throws Exception {
        final CourseEditDto courseDto = buildCourse();
        service.createCourse(courseDto);
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
