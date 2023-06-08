package com.algaworks.ecommerce.advancedmapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.Gender;

class SavePersonTest extends EntityManagerTest {
	
	@Test
	void saveClient() {
		Person person = new Person();
		person.setFirstname("Pedro Independente");
		person.setTaxIdNumber("442.710.240-90");
		person.setGender(Gender.MALE);
		person.setBirthday(LocalDate.of(1822, Month.SEPTEMBER, 7));
		Map<Character, String> phones = new HashMap<Character, String>();
		phones.put('M', "9988887777");
		person.setPhones(phones);
		person.setEmail("pedro@mail.com");
		
		entityManager.getTransaction().begin();
		entityManager.persist(person);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person findPerson = entityManager.find(Person.class, person.getId());
		assertNotNull(findPerson.getGender());
		
	}
	
}
