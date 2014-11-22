package org.az.skill2peer.nuclei.services;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

public class CourseAdminServiceTest extends AbstractServiceTest {
    @Autowired
    CourseAdminService service;

    @Test
    @DatabaseSetup(value = "publish-course.xml")
    @ExpectedDatabase(value = "publish-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void publishEditedCourse() throws Exception {
        final Integer publishCourse = service.publishCourse(72);
        Assert.assertEquals(171, publishCourse.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void publishNonExistentCourse() throws Exception {
        service.publishCourse(8899);
    }

    /**
     * cannot publish published course
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    @DatabaseSetup(value = "publish-course.xml")
    public void publishPublishedCourse() throws Exception {
        service.publishCourse(171);
    }

}
