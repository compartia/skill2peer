package org.az.skill2peer.nuclei.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsNotEqualValidator implements ConstraintValidator<PasswordsNotEqual, Object> {

    private String passwordFieldName;

    private String passwordVerificationFieldName;

    @Override
    public void initialize(final PasswordsNotEqual constraintAnnotation) {
        this.passwordFieldName = constraintAnnotation.passwordFieldName();
        this.passwordVerificationFieldName = constraintAnnotation.passwordVerificationFieldName();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            final String password = (String)ValidatorUtil.getFieldValue(value, passwordFieldName);
            final String passwordVerification = (String)ValidatorUtil.getFieldValue(value,
                    passwordVerificationFieldName);

            if (passwordsAreNotEqual(password, passwordVerification)) {
                ValidatorUtil.addValidationError(passwordFieldName, context);
                ValidatorUtil.addValidationError(passwordVerificationFieldName, context);

                return false;
            }
        } catch (final Exception ex) {
            throw new RuntimeException("Exception occurred during validation", ex);
        }

        return true;
    }

    private boolean passwordsAreNotEqual(final String password, final String passwordVerification) {
        return !(password == null ? passwordVerification == null : password.equals(passwordVerification));
    }
}
