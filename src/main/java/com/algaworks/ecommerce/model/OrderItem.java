package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SqlResultSetMappings(
	value = { @SqlResultSetMapping(name = "orderitem_product.OrderItem-Product",
		entities = { @EntityResult(entityClass = OrderItem.class),
			@EntityResult(entityClass = Product.class) }) })
@Entity
@Table(name = "tbl_order_items")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrderItemPk id;
	
	@NotNull
	@MapsId(value = "orderId")
	@ManyToOne(optional = false,
		cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	@JoinColumn(name = "order_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_orderitem__order_id"))
//			foreignKeyDefinition = "foreign key (order_id)"
//				+ " references tbl_orders(id) on delete cascade"))
	private Order order;
	
	@NotNull
	@MapsId(value = "productId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_orderitem__product_id"))
//			foreignKeyDefinition = "foreign key (product_id)"
//				+ " references tbl_products(id) on delete cascade"))
	private Product product;
	
	@PositiveOrZero(message = "Quantity to be greater than or equal to zero[0]")
	@NotNull(message = "Quantity it not be null")
	@Column(name = "col_quantity", columnDefinition = "double default 0",
		nullable = false)
	private Double quantity = 0d;
	
	@PositiveOrZero(message = "Subtotal to be greater than or equal to zero[0]")
	@NotNull(message = "Subtotal it not be null")
	@Column(name = "col_subtotal", nullable = false)
	private BigDecimal subtotal = BigDecimal.ZERO;
	
	public void setSubtotal() {
		this.subtotal = calcSubTotal(this.product, this.quantity);
	}
	
	public BigDecimal calcSubTotal(Product product, Double quantity) {
		if (!product.getUnitPrice().equals(BigDecimal.ZERO) && quantity > 0) {
			return product.getUnitPrice()
				.multiply(BigDecimal.valueOf(quantity));
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal calcSubTotal() {
		if (!this.product.getUnitPrice().equals(BigDecimal.ZERO)
			&& this.quantity > 0) {
			return this.product.getUnitPrice()
				.multiply(BigDecimal.valueOf(this.quantity));
		}
		return BigDecimal.ZERO;
	}
	
	@PrePersist
	public void itemPersist() {
		setSubtotal();
	}
	
	@PreUpdate
	public void itemUpdate() {
		if (this.order.isWaitting()) {
			setSubtotal();
		}
	}
}
