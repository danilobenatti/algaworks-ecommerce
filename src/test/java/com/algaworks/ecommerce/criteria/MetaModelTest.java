package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItem_;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

class MetaModelTest extends EntityManagerTest {
	
	@Test
	void usingMetaModel() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		
		query.select(root);
		
		query.where(builder.or(builder.like(root.get(Product_.name), "%c%"),
			builder.like(root.get(Product_.description), "%c%")));
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(query);
		List<Product> resultList = typedQuery.getResultList();
		
		resultList.forEach(p -> logger.info(String.format("%d - %s - %s",
			p.getId(), p.getName(), p.getDescription())));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void searchOrdersWithProductUsingMetaModel() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Join<OrderItem, Product> join = root.join(Order_.orderitems)
			.join(OrderItem_.product);
		
		query.select(root);
		query.where(builder.equal(join.get(Product_.id), 1L));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList
			.forEach(o -> logger.info(String.format("%d - %s - %s", o.getId(),
				o.getPerson().getFirstname(), o.getStatus().getValue())));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void makeLeftOuterJoinWithCriteriaUsingMetaModel() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		root.join(Order_.payment, JoinType.LEFT);
		
		query.select(root);
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger
			.info(String.format("%d - %s - %s", o.getId(), o.getStatus(),
				o.getPayment() != null ? o.getPayment().getStatus() : "N/A")));
		
		assertFalse(resultList.isEmpty());
		
	}
	
}
