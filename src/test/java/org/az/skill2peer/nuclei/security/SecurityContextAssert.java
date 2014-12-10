package org.az.skill2peer.nuclei.security;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.az.skill2peer.nuclei.security.dto.SocialUserDetails;
import org.az.skill2peer.nuclei.security.dto.UserDetailsAssert;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class SecurityContextAssert extends AbstractAssert<SecurityContextAssert, SecurityContext> {

    public static SecurityContextAssert assertThat(final SecurityContext actual) {
        return new SecurityContextAssert(actual);
    }

    private SecurityContextAssert(final SecurityContext actual) {
        super(actual, SecurityContextAssert.class);
    }

    public SecurityContextAssert loggedInUserHasPassword(final String password) {
        isNotNull();

        final SocialUserDetails loggedIn = (SocialUserDetails)actual.getAuthentication().getPrincipal();

        Assertions.assertThat(loggedIn)
                .overridingErrorMessage("Expected logged in user to be <not null> but was <null>")
                .isNotNull();

        UserDetailsAssert.assertThat(loggedIn)
                .hasPassword(password);

        return this;
    }

    public SecurityContextAssert loggedInUserIs(final User user) {
        isNotNull();

        final SocialUserDetails loggedIn = (SocialUserDetails)actual.getAuthentication().getPrincipal();

        Assertions.assertThat(loggedIn)
                .overridingErrorMessage("Expected logged in user to be <%s> but was <null>",
                        user
                )
                .isNotNull();

        UserDetailsAssert.assertThat(loggedIn)
                .hasFirstName(user.getFirstName())
                .hasId(user.getId())
                .hasLastName(user.getLastName())
                .hasUsername(user.getEmail())
                .isActive()
                .isRegisteredUser();

        return this;
    }

    public SecurityContextAssert loggedInUserIsRegisteredByUsingNormalRegistration() {
        isNotNull();

        final SocialUserDetails loggedIn = (SocialUserDetails)actual.getAuthentication().getPrincipal();

        Assertions.assertThat(loggedIn)
                .overridingErrorMessage("Expected logged in user to be <not null> but was <null>")
                .isNotNull();

        UserDetailsAssert.assertThat(loggedIn)
                .isRegisteredByUsingFormRegistration();

        return this;
    }

    public SecurityContextAssert loggedInUserIsSignedInByUsingSocialProvider(final SocialMediaService signInProvider) {
        isNotNull();

        final SocialUserDetails loggedIn = (SocialUserDetails)actual.getAuthentication().getPrincipal();

        Assertions.assertThat(loggedIn)
                .overridingErrorMessage("Expected logged in user to be <not null> but was <null>")
                .isNotNull();

        UserDetailsAssert.assertThat(loggedIn)
                .hasPassword("SocialUser")
                .isSignedInByUsingSocialSignInProvider(signInProvider);

        return this;
    }

    public SecurityContextAssert userIsAnonymous() {
        isNotNull();

        final Authentication authentication = actual.getAuthentication();

        Assertions.assertThat(authentication)
                .overridingErrorMessage("Expected authentication to be <null> but was <%s>.",
                        authentication
                )
                .isNull();

        return this;
    }
}
