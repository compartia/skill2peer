package org.az.skill2peer.nuclei.user.validation;

import org.az.skill2peer.nuclei.user.validation.PasswordsNotEmpty;

@PasswordsNotEmpty(
        triggerFieldName = "trigger",
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification")
public class PasswordsNotEmptyDTO {

    public static class PasswordsNotEmptyDTOBuilder {

        private final PasswordsNotEmptyDTO dto;

        public PasswordsNotEmptyDTOBuilder() {
            dto = new PasswordsNotEmptyDTO();
        }

        public PasswordsNotEmptyDTO build() {
            return dto;
        }

        public PasswordsNotEmptyDTOBuilder password(final String password) {
            dto.password = password;
            return this;
        }

        public PasswordsNotEmptyDTOBuilder passwordVerification(final String passwordVerification) {
            dto.passwordVerification = passwordVerification;
            return this;
        }

        public PasswordsNotEmptyDTOBuilder trigger(final String trigger) {
            dto.trigger = trigger;
            return this;
        }
    }

    public static PasswordsNotEmptyDTOBuilder getBuilder() {
        return new PasswordsNotEmptyDTOBuilder();
    }

    @SuppressWarnings("unused")
    private String password;

    @SuppressWarnings("unused")
    private String passwordVerification;

    @SuppressWarnings("unused")
    private String trigger;

}
