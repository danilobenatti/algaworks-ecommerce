package com.algaworks.ecommerce.model.enums;

import lombok.Getter;

@Getter
public enum ProductUnit {
	
	UNITY((byte) 1, "pc"), KILOGRAM((byte) 2, "kg"),
	METER((byte) 3, "m");
	
	private Byte code;
	private String value;
	
	ProductUnit(Byte code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static ProductUnit toEnum(Byte code) {
		if (code == null)
			return null;
		for (ProductUnit status : ProductUnit.values())
			if (code.equals(status.getCode()))
				return status;
		throw new IllegalArgumentException("Invalid code: " + code);
	}
	
}
