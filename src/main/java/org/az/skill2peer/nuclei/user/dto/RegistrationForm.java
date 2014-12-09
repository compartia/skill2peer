package org.az.skill2peer.nuclei.user.dto;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.validation.PasswordsNotEmpty;
import org.az.skill2peer.nuclei.user.validation.PasswordsNotEqual;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@PasswordsNotEmpty(triggerFieldName = "signInProvider", passwordFieldName = "password", passwordVerificationFieldName = "passwordVerification")
@PasswordsNotEqual(passwordFieldName = "password", passwordVerificationFieldName = "passwordVerification")
public class RegistrationForm {

    public static final String FIELD_NAME_EMAIL = "email";

    @Email
    @NotEmpty
    @Size(max = 100)
    private String email;

    @NotEmpty
    @Size(max = 100)
    private String firstName;

    @NotEmpty
    @Size(max = 100)
    private String lastName;

    private String password;

    private String passwordVerification;

    private SocialMediaService signInProvider;

    public RegistrationForm() {

    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    public boolean isNormalRegistration() {
        return signInProvider == null;
    }

    public boolean isSocialSignIn() {
        return signInProvider != null;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPasswordVerification(final String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public void setSignInProvider(final SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("email", email).append("firstName", firstName)
                .append("lastName", lastName).append("signInProvider", signInProvider).toString();
    }
}
