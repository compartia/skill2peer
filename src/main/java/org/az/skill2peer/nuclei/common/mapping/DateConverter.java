package org.az.skill2peer.nuclei.common.mapping;

import org.az.skill2peer.nuclei.common.controller.rest.dto.DateTimeEditDto;
import org.dozer.CustomConverter;
import org.joda.time.DateTime;

public class DateConverter implements CustomConverter {

    @Override
    public Object convert(final Object existingDestinationFieldValue,
            final Object sourceFieldValue,
            final Class<?> destinationClass,
            final Class<?> sourceClass) {

        if (sourceClass.isAssignableFrom(DateTimeEditDto.class)) {
            final DateTimeEditDto dto = (DateTimeEditDto)sourceFieldValue;
            return dto.toDateTime();
        } else {
            final DateTimeEditDto dto = new DateTimeEditDto((DateTime)sourceFieldValue);
            return dto;
        }

    }

}
