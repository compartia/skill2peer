package org.az.skill2peer.nuclei.user.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class PasswordsNotEqualAssert extends ConstraintViolationAssert<PasswordsNotEqualDTO> {

    private static final String VALIDATION_ERROR_MESSAGE = "PasswordsNotEqual";

    public PasswordsNotEqualAssert(final Set<ConstraintViolation<PasswordsNotEqualDTO>> actual) {
        super(PasswordsNotEqualAssert.class, actual);
    }

    public static PasswordsNotEqualAssert assertThat(final Set<ConstraintViolation<PasswordsNotEqualDTO>> actual) {
        return new PasswordsNotEqualAssert(actual);
    }

    @Override
    protected String getErrorMessage() {
        return VALIDATION_ERROR_MESSAGE;
    }
}
