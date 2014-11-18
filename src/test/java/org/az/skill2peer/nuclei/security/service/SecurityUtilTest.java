package org.az.skill2peer.nuclei.security.service;

import static org.az.skill2peer.nuclei.security.SecurityContextAssert.assertThat;

import org.az.skill2peer.nuclei.model.UserBuilder;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.model.User;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtilTest {

    private static final String EMAIL = "foo@bar.com";

    private static final String FIRST_NAME = "Foo";

    private static final Integer ID = 1;

    private static final String LAST_NAME = "Bar";

    private static final String PASSWORD = "password";

    @Test
    public void logInUser_UserRegisteredByUsingFormRegistration_ShouldAddUserDetailsToSecurityContext() {
        final User user = new UserBuilder().email(EMAIL).firstName(FIRST_NAME).id(ID).lastName(LAST_NAME)
                .password(PASSWORD).build();

        SecurityUtil.logInUser(user);

        assertThat(SecurityContextHolder.getContext()).loggedInUserIs(user).loggedInUserHasPassword(PASSWORD)
                .loggedInUserIsRegisteredByUsingNormalRegistration();
    }

    @Test
    public void logInUser_UserSignInByUsingSocialSignInProvider_ShouldAddUserDetailsToSecurityContext() {
        final User user = new UserBuilder().email(EMAIL).firstName(FIRST_NAME).id(ID).lastName(LAST_NAME)
                .signInProvider(SocialMediaService.TWITTER).build();

        SecurityUtil.logInUser(user);

        assertThat(SecurityContextHolder.getContext()).loggedInUserIs(user)
                .loggedInUserIsSignedInByUsingSocialProvider(SocialMediaService.TWITTER);
    }
}
