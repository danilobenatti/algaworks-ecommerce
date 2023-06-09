package com.algaworks.ecommerce.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanYesOrNoConverter
	implements AttributeConverter<Boolean, String> {
	
	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		return Boolean.TRUE.equals(attribute) ? "yes" : "no";
	}
	
	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return switch (dbData) {
			case "yes" -> Boolean.TRUE;
			case "no" -> Boolean.FALSE;
			default -> throw new IllegalArgumentException(
				"Unexpected value: " + dbData);
		};
	}
	
}
