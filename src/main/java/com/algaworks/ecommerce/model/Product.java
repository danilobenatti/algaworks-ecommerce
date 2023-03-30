package com.algaworks.ecommerce.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.algaworks.ecommerce.exception.FileNotFoundException;
import com.algaworks.ecommerce.listener.GenericListener;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "tbl_products")
public class Product extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
	
	@ElementCollection
	@CollectionTable(name = "tbl_product_tag",
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_producttag_product_id")))
	@Column(name = "col_tag")
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "tbl_product_attribute",
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_productattribute_product_id")))
	private List<Attribute> attributes;
	
	@Column(name = "col_image", length = 5242880) // 5242880 Bytes = 5MB
	private byte[] image;
	
	@Column(name = "col_create_date", updatable = false)
	private LocalDateTime createDate;
	
	@Column(name = "col_update_date", insertable = false)
	private LocalDateTime updateDate;
	
	public static byte[] uploadImage(File file) {
		try {
			return Product.class.getResourceAsStream(file.getName())
				.readAllBytes();
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found.", ex);
		}
		
	}
	
	@PrePersist
	public void onPersist() {
		this.createDate = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updateDate = LocalDateTime.now();
	}
	
}
