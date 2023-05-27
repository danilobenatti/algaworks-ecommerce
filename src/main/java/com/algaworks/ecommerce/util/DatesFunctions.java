package com.algaworks.ecommerce.util;

import java.time.LocalDate;

public class DatesFunctions {
	
	private DatesFunctions() {
	}
	
	public static int getAge(LocalDate bornDate) {
		return LocalDate.now().minusYears(bornDate.getYear()).getYear();
	}
	
}
