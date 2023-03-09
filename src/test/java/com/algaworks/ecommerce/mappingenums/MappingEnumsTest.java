package com.algaworks.ecommerce.mappingenums;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.Gender;

class MappingEnumsTest extends EntityManagerTest {
	
	@Test
	void enumTest() {
		Person p = new Person(null, "Alice",
				LocalDate.of(1945, Month.JANUARY, 9), Gender.FEMALE, null);
		
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Person personFind = entityManager.find(Person.class, p.getId());
		Assertions.assertNotNull(personFind);
		
	}
}
