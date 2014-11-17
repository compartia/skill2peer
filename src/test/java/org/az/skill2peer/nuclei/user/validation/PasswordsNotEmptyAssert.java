package org.az.skill2peer.nuclei.user.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class PasswordsNotEmptyAssert extends ConstraintViolationAssert<PasswordsNotEmptyDTO> {

    private static final String VALIDATION_ERROR_MESSAGE = "PasswordsNotEmpty";

    public static PasswordsNotEmptyAssert assertThat(final Set<ConstraintViolation<PasswordsNotEmptyDTO>> actual) {
        return new PasswordsNotEmptyAssert(actual);
    }

    public PasswordsNotEmptyAssert(final Set<ConstraintViolation<PasswordsNotEmptyDTO>> actual) {
        super(PasswordsNotEmptyAssert.class, actual);
    }

    @Override
    protected String getErrorMessage() {
        return VALIDATION_ERROR_MESSAGE;
    }
}
