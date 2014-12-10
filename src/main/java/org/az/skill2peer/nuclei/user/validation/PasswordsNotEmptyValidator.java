package org.az.skill2peer.nuclei.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsNotEmptyValidator implements ConstraintValidator<PasswordsNotEmpty, Object> {

    private String validationTriggerFieldName;

    private String passwordFieldName;

    private String passwordVerificationFieldName;

    @Override
    public void initialize(final PasswordsNotEmpty constraintAnnotation) {
        validationTriggerFieldName = constraintAnnotation.triggerFieldName();
        passwordFieldName = constraintAnnotation.passwordFieldName();
        passwordVerificationFieldName = constraintAnnotation.passwordVerificationFieldName();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            final Object validationTrigger = ValidatorUtil.getFieldValue(value, validationTriggerFieldName);
            if (validationTrigger == null) {
                return passwordFieldsAreValid(value, context);
            }
        } catch (final Exception ex) {
            throw new RuntimeException("Exception occurred during validation", ex);
        }

        return true;
    }

    private boolean passwordFieldsAreValid(final Object value, final ConstraintValidatorContext context)
            throws NoSuchFieldException, IllegalAccessException {
        boolean passwordWordFieldsAreValid = true;

        final String password = (String)ValidatorUtil.getFieldValue(value, passwordFieldName);
        if (isNullOrEmpty(password)) {
            ValidatorUtil.addValidationError(passwordFieldName, context);
            passwordWordFieldsAreValid = false;
        }

        final String passwordVerification = (String)ValidatorUtil.getFieldValue(value, passwordVerificationFieldName);
        if (isNullOrEmpty(passwordVerification)) {
            ValidatorUtil.addValidationError(passwordVerificationFieldName, context);
            passwordWordFieldsAreValid = false;
        }

        return passwordWordFieldsAreValid;
    }

    private boolean isNullOrEmpty(final String field) {
        return field == null || field.trim().isEmpty();
    }
}
