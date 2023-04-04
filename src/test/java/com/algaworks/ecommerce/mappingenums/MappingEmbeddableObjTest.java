package com.algaworks.ecommerce.mappingenums;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Address;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class MappingEmbeddableObjTest extends EntityManagerTest {
	
	@Test
	void embeddableObjTest() {
		Address address = Address.builder().street("Street One Level").number("145")
				.complement("Ap5").district("Townsville").city("Newaldr")
				.estate("WD").zipCode("123456").build();
		
		Order order = new Order();
		order.setId(null);
		order.setStatus(OrderStatus.WAITING);
		order.setTotal(BigDecimal.valueOf(1500.54));
		order.setDeliveryAddress(address);
		order.setPerson(entityManager.find(Person.class, 1L));
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNotNull(findOrder);
		Assertions.assertEquals(OrderStatus.WAITING, findOrder.getStatus());
		Assertions.assertEquals(order.getDeliveryAddress().getCity(),
				findOrder.getDeliveryAddress().getCity());
	}
}
