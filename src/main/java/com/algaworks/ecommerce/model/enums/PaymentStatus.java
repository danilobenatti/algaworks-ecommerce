package com.algaworks.ecommerce.model.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
	
	PROCESSING((byte) 1, "Processing"), CANCELED((byte) 2, "Canceled"),
	RECEIVED((byte) 3, "Received");
	
	private Byte code;
	private String value;
	
	PaymentStatus(Byte code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static PaymentStatus toEnum(Byte code) {
		if (code == null)
			return null;
		for (PaymentStatus status : PaymentStatus.values())
			if (code.equals(status.getCode()))
				return status;
		throw new IllegalArgumentException("Invalid code: " + code);
	}
	
}
