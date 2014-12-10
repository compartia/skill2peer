package org.az.skill2peer.nuclei.user.validation;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidatorContext;

public class ValidatorUtil {

    public static void addValidationError(final String field, final ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode(field)
                .addConstraintViolation();
    }

    public static Object getFieldValue(final Object object, final String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        final Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }
}
