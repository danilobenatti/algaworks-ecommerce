package com.algaworks.ecommerce.mappingenums;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.ProductUnit;

class KeyStrategyAutoTest extends EntityManagerTest {
	
	@Test
	void KeyStrategyTest() {
		Product p = new Product();
		p.setName("iPad mini 6");
		p.setDescription("A experiência completa do iPad na palma da mão.");
		p.setUnit(ProductUnit.UNITY);
		p.setUnitPrice(BigDecimal.valueOf(5999.00));
		Category c = new Category();
		c.setName("Jardinagem");
		
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.persist(c);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product insertProduct = entityManager.find(Product.class, p.getId());
		Category insertCategory = entityManager.find(Category.class, c.getId());
		Assertions.assertNotNull(insertProduct);
		Assertions.assertNotNull(insertCategory);
		
	}
	
}
