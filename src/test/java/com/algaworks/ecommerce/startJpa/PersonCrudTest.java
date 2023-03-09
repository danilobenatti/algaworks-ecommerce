package com.algaworks.ecommerce.startJpa;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.Gender;

class PersonCrudTest extends EntityManagerTest {
	
	@Test
	void createPerson() {
		Person person = new Person(null, "José Lucas",
				LocalDate.of(1985, Month.OCTOBER, 14), Gender.MALE, null);
		
		entityManager.getTransaction().begin();
		entityManager.persist(person);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Person createdPerson = entityManager.find(Person.class, person.getId());
		Assertions.assertNotNull(createdPerson);
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
		Assertions.assertEquals(findPerson.getId(), readPerson.getId());
	}
	
	@Test
	void updatePerson() {
		Person person = new Person(1L, "Fernando Medeiros Júnior",
				LocalDate.of(1958, Month.NOVEMBER, 5), Gender.MALE, null);
		
		entityManager.getTransaction().begin();
		entityManager.merge(person);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Person updatedPerson = entityManager.find(Person.class, 1);
		Assertions.assertNotNull(updatedPerson);
		Assertions.assertEquals("Fernando Medeiros Júnior",
				updatedPerson.getName());
	}
	
	@Test
	void updatePersonManaged() {
		Person person = entityManager.find(Person.class, 1);
		
		entityManager.getTransaction().begin();
		person.setBirthday(LocalDate.of(1958, Month.DECEMBER, 5));
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Person updatedPerson = entityManager.find(Person.class, 1);
		Assertions.assertNotNull(updatedPerson);
		Assertions.assertEquals(LocalDate.of(1958, Month.DECEMBER, 5),
				updatedPerson.getBirthday());
	}
	
	@Test
	void deletePerson() {
		Person person = entityManager.find(Person.class, 2);
		
		entityManager.getTransaction().begin();
		entityManager.remove(person);
		entityManager.getTransaction().commit();
		
		Person deletedPerson = entityManager.find(Person.class, 2);
		Assertions.assertNull(deletedPerson);
	}
}
