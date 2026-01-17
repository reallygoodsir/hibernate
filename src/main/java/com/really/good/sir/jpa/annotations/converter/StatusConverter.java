package com.really.good.sir.jpa.annotations.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
    public class StatusConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        // Example conversion: uppercase value stored in DB
        return attribute == null ? null : attribute.toUpperCase();
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // Example conversion: lowercase value exposed in entity
        return dbData == null ? null : dbData.toLowerCase();
    }
}
