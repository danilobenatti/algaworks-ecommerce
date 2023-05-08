package com.algaworks.ecommerce.relationships;

import static com.algaworks.ecommerce.model.Image.validFileExtension;
import static com.algaworks.ecommerce.model.Product.getByteArrayFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Image;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class RelationshipsManyToOneTest extends EntityManagerTest {
	
	@Test
	void verifyManyToOneRelationshipTest() {
		Person person = entityManager.find(Person.class, 1L);
		Product product1 = entityManager.find(Product.class, 1L);
		Product product3 = entityManager.find(Product.class, 3L);
		
		File imgP1 = new File("./src/main/resources/img/kindle_paperwhite.jpg");
		File imgP3 = new File("./src/main/resources/img/gopro_hero.jpg");
		
		product1.setImage(getByteArrayFromFile(imgP1));
		
		Image img1 = new Image(imgP1.getName(), getByteArrayFromFile(imgP1),
			validFileExtension(imgP1));
		product1.setImages(Arrays.asList(img1));
		
		product3.setImage(getByteArrayFromFile(imgP3));
		
		Image img3 = new Image(imgP3.getName(), getByteArrayFromFile(imgP3),
			validFileExtension(imgP3));
		product3.setImages(Arrays.asList(img3));
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setPerson(person);
		
		person.setOrders(Arrays.asList(order));
		
		OrderItem item1 = new OrderItem();
		item1.setId(new OrderItemPk());
		item1.setOrder(order);
		item1.setProduct(product1);
		item1.setQuantity(2d);
		
		OrderItem item2 = new OrderItem();
		item2.setId(new OrderItemPk());
		item2.setOrder(order);
		item2.setProduct(product3);
		item2.setQuantity(2d);
		
		order.setOrderitems(Arrays.asList(item1, item2));
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		findOrder.setStatus(OrderStatus.CANCELED);
		
		entityManager.getTransaction().begin();
		entityManager.merge(findOrder);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		OrderItem findItem1 = entityManager.find(OrderItem.class,
			new OrderItemPk(order.getId(), product1.getId()));
		OrderItem findItem2 = entityManager.find(OrderItem.class,
			new OrderItemPk(order.getId(), product3.getId()));
		
		assertEquals(order.getId(), findOrder.getId());
		assertEquals(order.getId(), findItem1.getOrder().getId());
		assertEquals(order.getId(), findItem2.getOrder().getId());
		
	}
	
}
