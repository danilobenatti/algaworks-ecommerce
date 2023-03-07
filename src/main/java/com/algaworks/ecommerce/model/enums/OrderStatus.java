package com.algaworks.ecommerce.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
	
	WAITING((byte) 1, "Waiting"), CANCELED((byte) 2, "Canceled"),
	PAID((byte) 3, "Paid");
	
	private Byte code;
	private String value;
	
	OrderStatus(Byte code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static OrderStatus toEnum(Byte code) {
		if (code == null)
			return null;
		for (OrderStatus status : OrderStatus.values())
			if (code.equals(status.getCode()))
				return status;
		throw new IllegalArgumentException("Invalid code: " + code);
	}
	
}
