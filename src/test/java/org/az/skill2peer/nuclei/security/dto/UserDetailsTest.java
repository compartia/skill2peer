package org.az.skill2peer.nuclei.security.dto;

import static org.az.skill2peer.nuclei.security.dto.UserDetailsAssert.assertThat;

import org.az.skill2peer.nuclei.user.model.Role;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.junit.Test;

public class UserDetailsTest {

    private static final String EMAIL = "john.smith@gmail.com";

    private static final String FIRST_NAME = "John";

    private static final Integer ID = 1;

    private static final String LAST_NAME = "Smith";

    private static final String PASSWORD = "password";

    private static final String SOCIAL_USER_DUMMY_PASSWORD = "SocialUser";

    @Test
    public void build_UserRegisteredByUsingFormRegistration_ShouldCreateNewObject() {
        final SocialUserDetails user = SocialUserDetails.getBuilder().firstName(FIRST_NAME).id(ID)
                .lastName(LAST_NAME).password(PASSWORD).role(Role.ROLE_USER).username(EMAIL).build();

        assertThat(user).hasFirstName(FIRST_NAME).hasId(ID).hasLastName(LAST_NAME).hasPassword(PASSWORD)
                .hasUsername(EMAIL).isActive().isRegisteredUser().isRegisteredByUsingFormRegistration();
    }

    @Test
    public void build_UserUsingSocialSignIn_ShouldCreateNewObject() {
        final SocialUserDetails user = SocialUserDetails.getBuilder().firstName(FIRST_NAME).id(ID)
                .lastName(LAST_NAME).password(null).role(Role.ROLE_USER)
                .socialSignInProvider(SocialMediaService.TWITTER).username(EMAIL).build();

        assertThat(user).hasFirstName(FIRST_NAME).hasId(ID).hasLastName(LAST_NAME)
                .hasPassword(SOCIAL_USER_DUMMY_PASSWORD).hasUsername(EMAIL).isActive().isRegisteredUser()
                .isSignedInByUsingSocialSignInProvider(SocialMediaService.TWITTER);
    }
}
