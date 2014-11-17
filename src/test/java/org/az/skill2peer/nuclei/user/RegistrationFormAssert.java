package org.az.skill2peer.nuclei.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.az.skill2peer.nuclei.user.dto.RegistrationForm;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;

public class RegistrationFormAssert extends AbstractAssert<RegistrationFormAssert, RegistrationForm> {

    private RegistrationFormAssert(final RegistrationForm actual) {
        super(actual, RegistrationFormAssert.class);
    }

    public static RegistrationFormAssert assertThatRegistrationForm(final RegistrationForm actual) {
        return new RegistrationFormAssert(actual);
    }

    public RegistrationFormAssert hasEmail(final String email) {
        isNotNull();

        assertThat(actual.getEmail())
                .overridingErrorMessage("Expected email to be <%s> but was <%s>",
                        email,
                        actual.getEmail()
                )
                .isEqualTo(email);

        return this;
    }

    public RegistrationFormAssert hasFirstName(final String firstName) {
        isNotNull();

        assertThat(actual.getFirstName())
                .overridingErrorMessage("Expected first name to be <%s> but was <%s>",
                        firstName,
                        actual.getFirstName()
                )
                .isEqualTo(firstName);

        return this;
    }

    public RegistrationFormAssert hasLastName(final String lastName) {
        isNotNull();

        assertThat(actual.getLastName())
                .overridingErrorMessage("Expected last name to be <%s> but was <%s>",
                        lastName,
                        actual.getLastName())
                .isEqualTo(lastName);

        return this;
    }

    public RegistrationFormAssert hasNoPassword() {
        isNotNull();

        assertThat(actual.getPassword())
                .overridingErrorMessage("Expected password to be <null> but was <%s>",
                        actual.getPassword()
                )
                .isNull();

        return this;
    }

    public RegistrationFormAssert hasNoPasswordVerification() {
        isNotNull();

        assertThat(actual.getPasswordVerification())
                .overridingErrorMessage("Expected password verification to be <null> but was <%s>",
                        actual.getPasswordVerification()
                )
                .isNull();

        return this;
    }

    public RegistrationFormAssert hasPassword(final String password) {
        isNotNull();

        assertThat(actual.getPassword())
                .overridingErrorMessage("Expected password to be <%s> but was <%s>",
                        password,
                        actual.getPassword()
                )
                .isEqualTo(password);

        return this;
    }

    public RegistrationFormAssert hasPasswordVerification(final String passwordVerification) {
        isNotNull();

        assertThat(actual.getPasswordVerification())
                .overridingErrorMessage("Expected password verification to be <%s> but was <%s>",
                        passwordVerification,
                        actual.getPasswordVerification()
                )
                .isEqualTo(passwordVerification);

        return this;
    }

    public RegistrationFormAssert isNormalRegistration() {
        isNotNull();

        assertThat(actual.getSignInProvider())
                .overridingErrorMessage("Expected sign in provider to be <null> but was <%s>",
                        actual.getSignInProvider()
                )
                .isNull();

        return this;
    }

    public RegistrationFormAssert isSocialSignInWithSignInProvider(final SocialMediaService signInProvider) {
        isNotNull();

        assertThat(actual.getSignInProvider())
                .overridingErrorMessage("Expected sign in provider to be <%s> but was <%s>",
                        signInProvider,
                        actual.getSignInProvider()
                )
                .isEqualTo(signInProvider);

        return this;
    }
}
