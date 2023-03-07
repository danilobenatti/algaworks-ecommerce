package com.algaworks.ecommerce.model.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Gender {
	
	MALE((byte) 1, "Masculine"), FEMALE((byte) 2, "Feminine");
	
	private Byte code;
	private String value;
	
	Gender(Byte code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static Gender toEnum(Byte code) {
		if (code == null)
			return null;
		for (Gender gender : Gender.values())
			if (code.equals(gender.getCode()))
				return gender;
		throw new IllegalArgumentException("Invalid code: " + code);
	}
	
}
