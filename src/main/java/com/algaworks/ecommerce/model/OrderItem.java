package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id",
		foreignKey = @ForeignKey(name = "fk_orderitem_order_id"))
	private Order order;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id",
		foreignKey = @ForeignKey(name = "fk_orderitem_product_id"))
	private Product product;
	
	@Column(name = "col_quantity")
	private Double quantity;
	
	@Column(name = "col_subtotal")
	private BigDecimal subtotal;
	
	public BigDecimal calcSubTotal() {
		return this.product.getPrice()
				.multiply(BigDecimal.valueOf(this.quantity));
	}
	
}
