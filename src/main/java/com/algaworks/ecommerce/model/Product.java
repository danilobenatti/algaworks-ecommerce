package com.algaworks.ecommerce.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.algaworks.ecommerce.exception.FileNotFoundException;
import com.algaworks.ecommerce.listener.GenericListener;
import com.algaworks.ecommerce.model.enums.ProductUnit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
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
@EqualsAndHashCode(callSuper = true)
@EntityListeners({ GenericListener.class })
@Entity
@Table(name = "tbl_products",
	uniqueConstraints = @UniqueConstraint(name = "uk_product_name",
		columnNames = { "col_name" }),
	indexes = @Index(name = "idx_product_name", columnList = "col_name"))
public class Product extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_name", length = 150, nullable = false)
	private String name;
	
	@Column(name = "col_description", length = 275, nullable = false)
	private String description;
	
	@Column(name = "col_unit")
	private Byte unit;
	
	@Column(name = "col_unitprice",
		columnDefinition = "decimal(12,2) unsigned not null default 0")
	private BigDecimal unitPrice = BigDecimal.valueOf(0.00);
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
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
	
	@ElementCollection
	@CollectionTable(name = "tbl_product_tag",
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_producttag_product_id")))
	@Column(name = "col_tag", length = 50, nullable = false)
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "tbl_product_attribute",
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_productattribute_product_id")))
	private List<Attribute> attributes;
	
	@Column(name = "col_image", length = 5242880) // 5242880 Bytes = 5MB
	private byte[] image;
	
	public static byte[] uploadImage(File file) {
		try {
			return Product.class.getResourceAsStream(file.getName())
				.readAllBytes();
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found.", ex);
		}
	}
	
	public ProductUnit getUnit() {
		return ProductUnit.toEnum(this.unit);
	}
	
	public void setUnit(ProductUnit unit) {
		this.unit = unit.getCode();
	}
	
}
