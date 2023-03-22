package com.algaworks.ecommerce.advancedmapping;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Attribute;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;

class ElementCollectionsTest extends EntityManagerTest {
	
	@Test
	void applyingTags() {
		entityManager.getTransaction().begin();
		
		Product product = entityManager.find(Product.class, 1L);
		product.setTags(Arrays.asList("e-Book", "Digital Book"));
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class,
				product.getId());
		Assertions.assertFalse(findProduct.getTags().isEmpty());
		
	}
	
	@Test
	void applyingAttributes() {
		entityManager.getTransaction().begin();
		
		Product product = entityManager.find(Product.class, 1L);
		product.setAttributes(
				Arrays.asList(new Attribute("Screen size", "5 inches"),
						new Attribute("Battery life", "4 hours")));
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class,
				product.getId());
		Assertions.assertFalse(findProduct.getAttributes().isEmpty());
		
	}
	
	@Test
	void applyingMapKey() {
		entityManager.getTransaction().begin();
		
		Person person = entityManager.find(Person.class, 1L);
		person.setPhones(Collections.singletonMap("mobile", "123456789"));
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person findPerson = entityManager.find(Person.class, person.getId());
		Assertions.assertEquals(person.getPhones().get("mobile"),
				findPerson.getPhones().get("mobile"));
		
	}
}
