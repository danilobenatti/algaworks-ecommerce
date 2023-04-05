package com.algaworks.ecommerce.relationships;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Product;

class RelationshipsManyToManyTest extends EntityManagerTest {
	
	@Test
	void verifyManyToManyRelationshipTest() {
		Product p1 = entityManager.find(Product.class, 1L);
		Category c1 = entityManager.find(Category.class, 1L);
		
		entityManager.getTransaction().begin();
//		c1.setProducts(Arrays.asList(p1));
		p1.setCategories(Arrays.asList(c1));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Category findCategory = entityManager.find(Category.class, c1.getId());
		assertFalse(findCategory.getProducts().isEmpty());
		
	}
	
	@Test
	void deleteManyToManyRelationshipText() {
		Category c = entityManager.find(Category.class, 2L);
		
		entityManager.getTransaction().begin();
		entityManager.remove(c);
		entityManager.getTransaction().commit();
		
		Category findCategory = entityManager.find(Category.class, 2L);
		assertNull(findCategory);
	}
}
