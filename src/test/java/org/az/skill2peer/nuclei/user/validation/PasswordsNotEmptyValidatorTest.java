package org.az.skill2peer.nuclei.user.validation;

import static org.az.skill2peer.nuclei.user.validation.PasswordsNotEmptyAssert.assertThat;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.az.skill2peer.nuclei.user.validation.PasswordsNotEmpty;
import org.junit.Before;
import org.junit.Test;

public class PasswordsNotEmptyValidatorTest {

    private static final String PASSWORD = "password";

    private static final String PASSWORD_VERIFICATION = "passwordVerification";

    private static final String TRIGGER = "trigger";

    private Validator validator;

    @Before
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldIsNotNull_ShouldValidateNothing() {
        final PasswordsNotEmptyDTO notValidated = PasswordsNotEmptyDTO.getBuilder()
                .trigger(TRIGGER)
                .build();

        assertThat(validator.validate(notValidated)).hasNoValidationErrors();
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldNullAndPasswordFieldsHaveValues_ShouldPassValidation() {
        final PasswordsNotEmptyDTO passesValidation = PasswordsNotEmptyDTO.getBuilder()
                .password(PASSWORD)
                .passwordVerification(PASSWORD_VERIFICATION)
                .trigger(TRIGGER)
                .build();

        assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldAndPasswordFieldsAreNull_ShouldReturnValidationErrorForPasswordField() {
        final PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(1)
                .hasValidationErrorForField("password");
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldIsNullAndPasswordFieldIsEmpty_ShouldReturnValidationErrorForPasswordField() {
        final PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .password("")
                .passwordVerification(PASSWORD_VERIFICATION)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(1)
                .hasValidationErrorForField("password");
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldAndPasswordVerificationFieldsAreNull_ShouldReturnValidationErrorForPasswordVerificationField() {
        final PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .password(PASSWORD)
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(1)
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldIsNullAndPasswordVerificationFieldIsEmpty_ShouldReturnValidationErrorForPasswordVerificationField() {
        final PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .password(PASSWORD)
                .passwordVerification("")
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(1)
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldIsNullAndBothPasswordFieldsAreNull_ShouldReturnValidationErrorsForBothFields() {
        final PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder().build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test
    public void passwordsNotEmpty_TriggerFieldIsNullAndBothPasswordFieldsAreEmpty_ShouldReturnValidationErrorsForBothFields() {
        final PasswordsNotEmptyDTO failsValidation = PasswordsNotEmptyDTO.getBuilder()
                .password("")
                .passwordVerification("")
                .build();

        assertThat(validator.validate(failsValidation))
                .numberOfValidationErrorsIs(2)
                .hasValidationErrorForField("password")
                .hasValidationErrorForField("passwordVerification");
    }

    @Test(expected = ValidationException.class)
    public void passwordsNotEmpty_InvalidTriggerField_ShouldThrowException() {
        final InvalidTriggerFieldDTO invalid = new InvalidTriggerFieldDTO();

        validator.validate(invalid);
    }

    @Test(expected = ValidationException.class)
    public void passwordsNotEmpty_InvalidPasswordField_ShouldThrowException() {
        final InvalidPasswordFieldDTO invalid = new InvalidPasswordFieldDTO();

        validator.validate(invalid);
    }

    @Test(expected = ValidationException.class)
    public void passwordsNotEmpty_InvalidPasswordVerificationField_ShouldThrowException() {
        final InvalidPasswordVerificationFieldDTO invalid = new InvalidPasswordVerificationFieldDTO();

        validator.validate(invalid);
    }

    @PasswordsNotEmpty(
            triggerFieldName = "trigger",
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification")
    private class InvalidTriggerFieldDTO {
        @SuppressWarnings("unused")
        private String password;

        @SuppressWarnings("unused")
        private String passwordVerification;
    }

    @PasswordsNotEmpty(
            triggerFieldName = "trigger",
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification")
    private class InvalidPasswordFieldDTO {
        @SuppressWarnings("unused")
        private String trigger;

        @SuppressWarnings("unused")
        private String passwordVerification;
    }

    @PasswordsNotEmpty(
            triggerFieldName = "trigger",
            passwordFieldName = "password",
            passwordVerificationFieldName = "passwordVerification")
    private class InvalidPasswordVerificationFieldDTO {
        @SuppressWarnings("unused")
        private String trigger;

        @SuppressWarnings("unused")
        private String password;
    }

}
