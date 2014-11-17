package org.az.skill2peer.nuclei.user.model;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.az.skill2peer.nuclei.user.model.Role;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.model.User;

public class UserAssert extends AbstractAssert<UserAssert, User> {

    private UserAssert(final User actual) {
        super(actual, UserAssert.class);
    }

    public static UserAssert assertThat(final User actual) {
        return new UserAssert(actual);
    }

    public UserAssert hasEmail(final String email) {
        isNotNull();

        Assertions.assertThat(actual.getEmail())
                .overridingErrorMessage("Expected email to be <%s> but was <%s>",
                        email,
                        actual.getEmail()
                )
                .isEqualTo(email);

        return this;
    }

    public UserAssert hasFirstName(final String firstName) {
        isNotNull();

        Assertions.assertThat(actual.getFirstName())
                .overridingErrorMessage("Expected first name to be <%s> but was <%s>",
                        firstName,
                        actual.getFirstName()
                )
                .isEqualTo(firstName);

        return this;
    }

    public UserAssert hasLastName(final String lastName) {
        isNotNull();

        Assertions.assertThat(actual.getLastName())
                .overridingErrorMessage("Expected last name to be <%s> but was <%s>",
                        lastName,
                        actual.getLastName()
                )
                .isEqualTo(lastName);

        return this;
    }

    public UserAssert hasNoId() {
        isNotNull();

        Assertions.assertThat(actual.getId())
                .overridingErrorMessage("Expected id to be <null> but was <%d>",
                        actual.getId()
                )
                .isNull();

        return this;
    }

    public UserAssert hasPassword(final String password) {
        isNotNull();

        Assertions.assertThat(actual.getPassword())
                .overridingErrorMessage("Expected password to be <%s> but was <%s>",
                        password,
                        actual.getPassword()
                )
                .isEqualTo(password);

        return this;
    }

    public UserAssert isRegisteredByUsingNormalRegistration() {
        isNotNull();

        Assertions.assertThat(actual.getSignInProvider())
                .overridingErrorMessage("Expected signInProvider to be <null> but was <%s>",
                        actual.getSignInProvider()
                )
                .isNull();

        return this;
    }

    public UserAssert isRegisteredByUsingSignInProvider(final SocialMediaService signInProvider) {
        isNotNull();

        Assertions.assertThat(actual.getSignInProvider())
                .overridingErrorMessage("Expected signInProvider to be <%s> but was <%s>",
                        signInProvider,
                        actual.getSignInProvider()
                )
                .isEqualTo(signInProvider);

        hasNoPassword();

        return this;
    }

    private void hasNoPassword() {
        isNotNull();

        Assertions.assertThat(actual.getPassword())
                .overridingErrorMessage("Expected password to be <null> but was <%s>",
                        actual.getPassword()
                )
                .isNull();
    }

    public UserAssert isRegisteredUser() {
        isNotNull();

        Assertions.assertThat(actual.getRole())
                .overridingErrorMessage("Expected role to be <ROLE_USER> but was <%s>",
                        actual.getRole()
                )
                .isEqualTo(Role.ROLE_USER);

        return this;
    }
}
