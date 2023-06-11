package com.algaworks.ecommerce.util;

import java.nio.charset.StandardCharsets;

public class StringsFunctions {
	
	private StringsFunctions() {
	}
	
	public static String getByteArraytoString(byte[] byteArray) {
		return new String(byteArray, StandardCharsets.UTF_8);
	}
}
