package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.algaworks.ecommerce.listener.GenericListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@EntityListeners({ GenericListener.class })
@Entity
@Table(name = "tbl_products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "col_name")
	private String name;
	
	@Column(name = "col_description")
	private String description;
	
	@Column(name = "col_unitprice")
	private BigDecimal unitPrice;
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems;
	
	@ManyToMany
	@JoinTable(name = "tbl_product_category",
		uniqueConstraints = @UniqueConstraint(name = "uk_productid_categoryid",
			columnNames = { "product_id", "category_id" }),
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_productcategory__product_id")),
		inverseJoinColumns = @JoinColumn(name = "category_id",
			foreignKey = @ForeignKey(name = "fk_productcategory__category_id")))
	private List<Category> categories;
	
	@OneToOne(mappedBy = "product")
	private ProductStock stock;
	
	@Column(name = "col_create_date", updatable = false)
	private LocalDateTime createDate;
	
	@Column(name = "col_update_date", insertable = false)
	private LocalDateTime updateDate;
	
}
