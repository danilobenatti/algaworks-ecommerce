package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "tbl_products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prod")
	@SequenceGenerator(name = "seq_prod", sequenceName = "tbl_products_seq",
		initialValue = 10, allocationSize = 50)
	@Column(name = "id_product")
	private Long id;
	
	@Column(name = "col_name")
	private String name;
	
	@Column(name = "col_description")
	private String description;
	
	@Column(name = "col_price")
	private BigDecimal price;
	
}
