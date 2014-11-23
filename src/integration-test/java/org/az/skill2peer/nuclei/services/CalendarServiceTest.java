package org.az.skill2peer.nuclei.services;

import java.util.List;

import org.az.skill2peer.nuclei.common.controller.rest.dto.UserInfoDto;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;

public class CalendarServiceTest extends AbstractServiceTest {
    @Autowired
    CalendarService service;

    @Before
    public void _setUp() {
        final User user = User.getBuilder().email("email").id(1111).build();
        SecurityUtil.logInUser(user);
    }

    @Transactional
    @Test
    @DatabaseSetup(value = "edit-course.xml")
    public void getEnrolledUsers() throws Exception {

        final List<UserInfoDto> enrolledUsers = service.getEnrolledUsers(1);

        Assert.assertNotNull(enrolledUsers);
        Assert.assertEquals(3, enrolledUsers.size());
    }

}
