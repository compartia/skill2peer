package org.az.skill2peer.nuclei.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
import org.az.skill2peer.nuclei.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

public class CourseAdminServiceTest extends AbstractServiceTest {
    @Autowired
    CourseAdminService service;

    @Autowired
    UserRepository users;

    @PersistenceContext
    EntityManager em;

    @Test
    @DatabaseSetup(value = "publish-course.xml")
    @ExpectedDatabase(value = "publish-course-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void publishEditedCourse() throws Exception {
        final User usr = users.findByEmail("admin@admin.com");
        SecurityUtil.logInUser(usr);
        final Integer publishCourse = service.publishCourse(72);
        em.flush();
        Assert.assertEquals(171, publishCourse.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void publishNonExistentCourse() throws Exception {
        service.publishCourse(8899);
    }

}
