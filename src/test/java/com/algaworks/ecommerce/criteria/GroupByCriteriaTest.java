package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Category_;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItem_;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Person_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;

class GroupByCriteriaTest extends EntityManagerTest {
	
	@Test
	void groupResult1() {
		// total of sales by category
		/*
		 * SQL = select c.col_name as 'category', sum(i.col_subtotal) as 'total'
		 * from tbl_orders o join tbl_order_items i on i.order_id = o.id join
		 * tbl_products p on p.id = i.product_id join tbl_product_category pc on
		 * pc.product_id = p.id join tbl_categories c on c.id = pc.category_id
		 * group by c.id
		 */
		/*
		 * JPQL = select c.name, sum(i.subtotal) from OrderItem i join i.product
		 * p join p.categories c group by c.id
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
		Join<OrderItem, Product> joinProduct = root.join(OrderItem_.product);
		Join<Product, Category> joinCategory = joinProduct
			.join(Product_.categories);
		
		criteriaQuery.multiselect(joinCategory.get(Category_.name),
			criteriaBuilder.sum(root.get(OrderItem_.subtotal)));
		
		criteriaQuery.groupBy(joinCategory.get(Category_.id));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(c -> logger
			.info(new StringBuilder().append("Category: ").append(c[0])
				.append("; Subtotal: ").append(currency.format(c[1]))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void groupResult2() {
		// quantity of products by category
		/*
		 * SQL = select c.id, c.col_name as 'Category name', count(p.id) as 'Qtd
		 * Products' from tbl_categories c left join tbl_product_category pc on
		 * pc.category_id = c.id left join tbl_products p on p.id =
		 * pc.product_id group by c.id;
		 */
		/*
		 * JPQL = select c.name, count(p.id) from Category c join c.products p
		 * group by c.id
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Category> root = criteriaQuery.from(Category.class);
		ListJoin<Category, Product> join = root.join(Category_.products,
			JoinType.LEFT);
		
		criteriaQuery.multiselect(root.get(Category_.id),
			root.get(Category_.name),
			criteriaBuilder.count(join.get(Product_.id)));
		
		criteriaQuery.groupBy(root.get(Category_.id));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(c -> logger.info(new StringBuilder()
			.append("Category ID: ").append(c[0]).append("; Category: ")
			.append(c[1]).append("; Qtd Products: ").append(c[2])));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void groupResult3Exercise() {
		// Total of sales per person
		/*
		 * JPQL = select p.firstname, sum(i.subtotal) from OrderItem i join
		 * i.order o join o.person p group by p.id
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
		Join<OrderItem, Order> joinOrder = root.join(OrderItem_.order);
		Join<Order, Person> joinPerson = joinOrder.join(Order_.person);
		
		criteriaQuery.multiselect(joinPerson.get(Person_.firstname),
			criteriaBuilder.sum(root.get(OrderItem_.subtotal)));
		
		criteriaQuery.groupBy(joinPerson.get(Person_.id));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(
			c -> logger.info(new StringBuilder().append("Person: ").append(c[0])
				.append("; Total of sales: ").append(currency.format(c[1]))));
		
		assertFalse(list.isEmpty());
	}
	
}
