package com.algaworks.ecommerce.mappingenums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.Gender;

class MappingEnumsTest extends EntityManagerTest {
	
	@Test
	void enumTest() {
		Person person = new Person();
		person.setFirstname("Mary");
		person.setTaxIdNumber("436.841.000-99");
		person.setBirthday(LocalDate.of(1945, Month.JANUARY, 9));
		person.setGender(Gender.FEMALE);
		Map<Character, String> phones = new HashMap<Character, String>();
		phones.put('M', "7788889999");
		person.setPhones(phones);
		person.setEmail("mary@mail.com");
		
		entityManager.getTransaction().begin();
		entityManager.persist(person);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person personFind = entityManager.find(Person.class, person.getId());
		assertNotNull(personFind);
		assertEquals(person.getGender().getCode(),
			personFind.getGender().getCode());
		
	}
}
