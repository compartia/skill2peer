package org.az.skill2peer.nuclei.user.validation;

import org.az.skill2peer.nuclei.user.validation.PasswordsNotEqual;

@PasswordsNotEqual(
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification")
public class PasswordsNotEqualDTO {

    @SuppressWarnings("unused")
    private String password;

    @SuppressWarnings("unused")
    private String passwordVerification;

    public static PasswordsNotEqualDTOBuilder getBuilder() {
        return new PasswordsNotEqualDTOBuilder();
    }

    public static class PasswordsNotEqualDTOBuilder {

        private final PasswordsNotEqualDTO dto;

        public PasswordsNotEqualDTOBuilder() {
            dto = new PasswordsNotEqualDTO();
        }

        public PasswordsNotEqualDTOBuilder password(final String password) {
            dto.password = password;
            return this;
        }

        public PasswordsNotEqualDTOBuilder passwordVerification(final String passwordVerification) {
            dto.passwordVerification = passwordVerification;
            return this;
        }

        public PasswordsNotEqualDTO build() {
            return dto;
        }
    }

}
