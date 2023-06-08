package com.algaworks.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_product_stocks")
public class ProductStock extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_productstock__product_id"))
	private Product product;
	
	@PositiveOrZero(message = "Quantity to be greater than or equal to zero[0]")
	@NotNull(message = "Quantity it not be null")
	@Column(name = "col_quantity", columnDefinition = "double default 0",
		nullable = false)
	private Double quantity = 0d;
	
}
