package org.az.skill2peer.nuclei.user.validation;

import static org.az.skill2peer.nuclei.user.validation.PasswordsNotEqualAssert.assertThat;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.az.skill2peer.nuclei.user.validation.PasswordsNotEqual;
import org.junit.Before;
import org.junit.Test;

public class PasswordsNotEqualValidatorTest {

    private static final String PASSWORD = "password";

    private static final String PASSWORD_VERIFICATION = "passwordVerification";

    private Validator validator;

    @Before
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void passwordsNotEqual_BothPasswordsAreNull_ShouldPassValidation() {
        final PasswordsNotEqualDTO passesValidation = PasswordsNotEqualDTO.getBuilder().build();

        assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
    }

    @Test
    public void passwordsNotEqual_PasswordIsNull_ShouldReturnValidationErrorsForBothFields() {
        final PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEqual_PasswordVerificationIsNull_ShouldReturnValidationErrorsForBothFields() {
        final PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .password(PASSWORD)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEqual_BothPasswordsAreEmpty_ShouldPassValidation() {
        final PasswordsNotEqualDTO passesValidation = PasswordsNotEqualDTO.getBuilder()
                .password("")
                .passwordVerification("")
                .build();

        assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
    }

    @Test
    public void passwordsNotEqual_PasswordIsEmpty_ShouldReturnValidationErrorsForBothFields() {
        final PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .password("")
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEqual_PasswordVerificationIsEmpty_ShouldReturnValidationErrorsForBothFields() {
        final PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .password(PASSWORD)
                .passwordVerification("")
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEqual_PasswordMismatch_ShouldReturnValidationErrorsForBothFields() {
        final PasswordsNotEqualDTO failsValidation = PasswordsNotEqualDTO.getBuilder()
                .password(PASSWORD)
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEqual_PasswordsMatch_ShouldPassValidation() {
        final PasswordsNotEqualDTO passesValidation = PasswordsNotEqualDTO.getBuilder()
                .password(PASSWORD)
                .passwordVerification(PASSWORD)
                .build();

        assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
    }

    @Test(expected = ValidationException.class)
    public void passwordsNotEqual_InvalidPasswordField_ShouldThrowException() {
        final InvalidPasswordFieldDTO invalid = new InvalidPasswordFieldDTO();

        validator.validate(invalid);
    }

    @Test(expected = ValidationException.class)
    public void passwordsNotEqual_InvalidPasswordVerificationField_ShouldThrowException() {
        final InvalidPasswordVerificationFieldDTO invalid = new InvalidPasswordVerificationFieldDTO();

        validator.validate(invalid);
    }

    @PasswordsNotEqual(
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification")
    private class InvalidPasswordFieldDTO {
        @SuppressWarnings("unused")
        public String passwordVerification;
    }

    @PasswordsNotEqual(
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification")
    private class InvalidPasswordVerificationFieldDTO {
        @SuppressWarnings("unused")
        public String password;
    }
}
