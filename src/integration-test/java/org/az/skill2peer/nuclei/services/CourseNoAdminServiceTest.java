package org.az.skill2peer.nuclei.services;

import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
import org.az.skill2peer.nuclei.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

public class CourseNoAdminServiceTest extends AbstractServiceTest {
    @Autowired
    CourseAdminService service;

    @Autowired
    UserRepository users;

    @Before
    public void _setUp() {
        final User usr = users.findByEmail("user@user.com");
        SecurityUtil.logInUser(usr);
    }

    @Test(expected = AccessDeniedException.class)
    @DatabaseSetup(value = "publish-course-no-admin.xml")
    public void publishEditedCourse() throws Exception {
        final Integer publishCourse = service.publishCourse(72);
        Assert.assertEquals(171, publishCourse.intValue());
    }

}
