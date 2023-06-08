package com.algaworks.ecommerce.importantdetails;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;

class ValidationTest extends EntityManagerTest {
	
//	@Test
	void PersonValidation() {
		entityManager.getTransaction().begin();
		
		Person person = new Person();
		person.setFirstname("L");
		person.setTaxIdNumber("123");
		
		if (!person.equals(null)) {
			entityManager.merge(person);
		}
		
		entityManager.getTransaction().commit();
		
		assertNull(person);
	}
	
	@Test
	void test() {
		assertNull(null);
	}
	
}
