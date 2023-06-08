package com.algaworks.ecommerce.ddl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Attribute;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.ProductStock;
import com.algaworks.ecommerce.model.enums.Gender;
import com.algaworks.ecommerce.model.enums.ProductUnit;

class DDLTest extends EntityManagerTest {
	
	@Test
	void generatorDDL() {
		Person person = new Person();
		person.setFirstname("Peter");
		person.setTaxIdNumber("056.303.858-60");
		person.setBirthday(LocalDate.of(1987, Month.MARCH, 5));
		person.setGender(Gender.MALE);
		HashMap<Character, String> phones = new HashMap<Character, String>();
		phones.put('H', "7788889999");
		person.setPhones(phones);
		person.setEmail("peter@mail.com");
		
		Product product = new Product();
		product.setName("Product Test");
		product.setUnit(ProductUnit.UNITY);
		product.setUnitPrice(BigDecimal.valueOf(50.89));
		product.setDescription("Product Description Test");
		product.setAttributes(
			Arrays.asList(new Attribute("First attribute", "Blue color"),
				new Attribute("Second attribute", "Small size")));
		
		ProductStock stock = new ProductStock();
		stock.setProduct(product);
		
		product.setStock(stock);
		
		entityManager.getTransaction().begin();
		entityManager.persist(person);
		entityManager.persist(product);
		entityManager.persist(stock);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person findPerson = entityManager.find(Person.class, person.getId());
		Product findProduct = entityManager.find(Product.class,
			product.getId());
		ProductStock findStock = entityManager.find(ProductStock.class,
			stock.getProduct().getId());
		
		assertEquals(findPerson.getBirthday().getYear(),
			person.getBirthday().getYear());
		assertNotNull(findProduct.getName());
		assertEquals(findStock.getId(), stock.getId());
		
	}
	
	@Test
	void generatorDDL2() {
		assertNull(null);
	}
	
}
