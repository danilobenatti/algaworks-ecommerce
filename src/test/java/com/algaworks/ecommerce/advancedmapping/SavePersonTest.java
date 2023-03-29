package com.algaworks.ecommerce.advancedmapping;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.Gender;

class SavePersonTest extends EntityManagerTest {
	
	@Test
	void saveClient() {
		Person person = new Person();
		person.setFirstname("Pedro Independente");
		person.setGender(Gender.MALE);
		person.setBirthday(LocalDate.of(1822, Month.SEPTEMBER, 7));
		
		entityManager.getTransaction().begin();
		entityManager.persist(person);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person findPerson = entityManager.find(Person.class, person.getId());
		Assertions.assertNotNull(findPerson.getGender());
		
	}
	
}
