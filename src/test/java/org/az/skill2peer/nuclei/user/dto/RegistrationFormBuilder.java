package org.az.skill2peer.nuclei.user.dto;

import org.az.skill2peer.nuclei.user.dto.RegistrationForm;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;

public class RegistrationFormBuilder {

    private final RegistrationForm dto;

    public RegistrationFormBuilder() {
        dto = new RegistrationForm();
    }

    public RegistrationFormBuilder email(final String email) {
        dto.setEmail(email);
        return this;
    }

    public RegistrationFormBuilder firstName(final String firstName) {
        dto.setFirstName(firstName);
        return this;
    }

    public RegistrationFormBuilder lastName(final String lastName) {
        dto.setLastName(lastName);
        return this;
    }

    public RegistrationFormBuilder password(final String password) {
        dto.setPassword(password);
        return this;
    }

    public RegistrationFormBuilder passwordVerification(final String passwordVerification) {
        dto.setPasswordVerification(passwordVerification);
        return this;
    }

    public RegistrationFormBuilder isSocialSignInViaSignInProvider(final SocialMediaService signInProvider) {
        dto.setSignInProvider(signInProvider);
        return this;
    }

    public RegistrationForm build() {
        return dto;
    }
}
