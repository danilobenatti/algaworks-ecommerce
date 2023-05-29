package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItem_;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Person_;
import com.algaworks.ecommerce.model.Product_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;

class PathExpressionsTest extends EntityManagerTest {
	
	@Test
	void usingPathExpression() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder
			.like(root.get(Order_.person).get(Person_.firstname), "M%"));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Order> list = typedQuery.getResultList();
		
		list.forEach(o -> logger
			.info(new StringBuilder().append("Order ID: ").append(o.getId())));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchOrdersWithSpecificProduct() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		ListJoin<Order, OrderItem> join = root.join(Order_.orderitems);
		
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder
			.equal(join.get(OrderItem_.product).get(Product_.id), 1));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Order> list = typedQuery.getResultList();
		
		list.forEach(o -> logger
			.info(new StringBuilder().append("Order ID: ").append(o.getId())));
		
		assertFalse(list.isEmpty());
	}
	
}
