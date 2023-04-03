package com.algaworks.ecommerce.ddl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;

class DDLTest extends EntityManagerTest {
	
	@Test
	void generatorDDL() {
		Person person = new Person();
		person.setFirstname("Peter");
		person.setBirthday(LocalDate.of(1987, Month.MARCH, 5));
		
		assertEquals(1987, person.getBirthday().getYear());
	}
	
}
