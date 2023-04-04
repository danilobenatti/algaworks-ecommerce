package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "tbl_order_items")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrderItemPk id;
	
	@MapsId(value = "orderId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id",
		foreignKey = @ForeignKey(name = "fk_orderitem_order_id"))
	private Order order;
	
	@MapsId(value = "productId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id",
		foreignKey = @ForeignKey(name = "fk_orderitem_product_id"))
	private Product product;
	
	@Column(name = "col_quantity", columnDefinition = "double default 0",
		nullable = false)
	private Double quantity = 0d;
	
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
		if (!product.getUnitPrice().equals(BigDecimal.ZERO)
			&& this.quantity > 0) {
			return this.product.getUnitPrice()
				.multiply(BigDecimal.valueOf(this.quantity));
		}
		return BigDecimal.ZERO;
	}
	
	@PrePersist
	public void onPersist() {
		setSubtotal();
	}
	
	@PreUpdate
	public void onUpdate() {
		setSubtotal();
	}
}
