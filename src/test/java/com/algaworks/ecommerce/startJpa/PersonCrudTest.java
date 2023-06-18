package com.algaworks.ecommerce.startJpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.Gender;

class PersonCrudTest extends EntityManagerTest {
	
	@Test
	void createPerson() {
		Person person = new Person();
		person.setFirstname("José Lucas");
		person.setTaxIdNumber("292.251.380-75");
		person.setBirthday(LocalDate.of(1985, Month.OCTOBER, 14));
		person.setGender(Gender.MALE);
		Map<Character, String> phones = new HashMap<Character, String>();
		phones.put('H', "8899995555");
		person.setPhones(phones);
		Set<String> emails = new HashSet<>();
		emails.add("jose@web.com");
		person.setEmails(emails);
		person.setEmail("jose@mail.com");
		
		entityManager.getTransaction().begin();
		entityManager.persist(person);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person createdPerson = entityManager.find(Person.class, person.getId());
		assertNotNull(createdPerson);
	}
	
	@Test
	void readPerson() {
		Person person = new Person();
		person.setId(1L);
		
		entityManager.getTransaction().begin();
		Person findPerson = entityManager.find(Person.class, person.getId());
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person readPerson = entityManager.find(Person.class,
			findPerson.getId());
		assertEquals(findPerson.getId(), readPerson.getId());
	}
	
	@Test
	void updatePerson() {
		Person person = entityManager.find(Person.class, 1L);
		person.setFirstname("Fernando Medeiros Júnior");
		person.setTaxIdNumber("246.851.120-40");
		person.setBirthday(LocalDate.of(1958, Month.NOVEMBER, 5));
		person.setGender(Gender.MALE);
		Map<Character, String> phones = new HashMap<Character, String>();
		phones.put('W', "8899998888");
		person.setPhones(phones);
		person.setEmail("fernando@mail.com");
		
		entityManager.getTransaction().begin();
		entityManager.merge(person);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person updatedPerson = entityManager.find(Person.class, 1);
		assertNotNull(updatedPerson);
		assertEquals("Fernando Medeiros Júnior", updatedPerson.getFirstname());
	}
	
	@Test
	void updatePersonManaged() {
		Person person = entityManager.find(Person.class, 1L);
		
		entityManager.getTransaction().begin();
		person.setBirthday(LocalDate.of(1958, Month.DECEMBER, 5));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person updatedPerson = entityManager.find(Person.class, 1L);
		assertNotNull(updatedPerson);
		assertEquals(LocalDate.of(1958, Month.DECEMBER, 5),
			updatedPerson.getBirthday());
	}
	
	@Test
	void deletePerson() {
		Person person = entityManager.find(Person.class, 2L);
		
		entityManager.getTransaction().begin();
		entityManager.remove(person);
		entityManager.getTransaction().commit();
		
		Person findPerson = entityManager.find(Person.class, 2L);
		assertNull(findPerson);
	}
}
