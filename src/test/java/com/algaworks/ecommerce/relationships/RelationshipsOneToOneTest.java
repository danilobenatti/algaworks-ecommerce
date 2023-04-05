package com.algaworks.ecommerce.relationships;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.PaymentCreditCard;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.ProductStock;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

class RelationshipsOneToOneTest extends EntityManagerTest {
	
	@Test
	void verifyOneToOneRelationshipTest() {
		Order o = entityManager.find(Order.class, 2L);
		PaymentCreditCard pcc = new PaymentCreditCard();
		pcc.setOrder(o);
		pcc.setNumberOfInstallments(3);
		pcc.setStatus(PaymentStatus.PROCESSING);
		
		o.setPayment(pcc);
		
		entityManager.getTransaction().begin();
		o.getPayment().setStatus(PaymentStatus.RECEIVED);
		entityManager.persist(pcc);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, o.getId());
		assertNotNull(findOrder.getPayment());
		
	}
	
	@Test
	void verifyOneToOneDeleteTest() {
		Order o = entityManager.find(Order.class, 1L);
		
		entityManager.getTransaction().begin();
		entityManager.remove(o);
		entityManager.getTransaction().commit();
		
		Order findOrder = entityManager.find(Order.class, o.getId());
		assertNull(findOrder);
		
	}
	
	@Test
	void verifyOneToOneRelationshipProductStockTest() {
		Product product = entityManager.find(Product.class, 3L);
		product.setImage(Product.uploadImage(new File(
			"./src/main/java/com/algaworks/ecommerce/model/gopro_hero.jpg")));
		ProductStock stock = new ProductStock();
		stock.setProduct(product);
		stock.setQuantity(12d);
		
		entityManager.getTransaction().begin();
		entityManager.persist(stock);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		ProductStock findStock = entityManager.find(ProductStock.class,
			stock.getId());
		assertEquals(12d, findStock.getQuantity());
		
	}
}
