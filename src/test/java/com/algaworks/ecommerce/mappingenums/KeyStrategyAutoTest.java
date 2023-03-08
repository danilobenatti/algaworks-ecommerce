package com.algaworks.ecommerce.mappingenums;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class KeyStrategyAutoTest extends EntityManagerTest {
	
	@Test
	void KeyStrategyTest() {
		var p = Product.builder().name("iPad mini 6")
				.description("A experiência completa do iPad na palma da mão.")
				.price(BigDecimal.valueOf(5999.00)).build();
		
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		Product insertedProduct = entityManager.find(Product.class, p.getId());
		Assertions.assertNotNull(insertedProduct);
		
	}
	
}
