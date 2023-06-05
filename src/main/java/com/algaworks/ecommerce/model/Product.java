package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.algaworks.ecommerce.listener.GenericListener;
import com.algaworks.ecommerce.model.enums.ProductUnit;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
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
@SqlResultSetMappings(value = {
	@SqlResultSetMapping(name = "product_shop.Product",
		entities = { @EntityResult(entityClass = Product.class) }) })
@NamedQueries({
	@NamedQuery(name = "Product.listAll", query = "select p from Product p"),
	@NamedQuery(name = "Product.listByCategory",
		query = "select p1 from Product p1 where exists (select 1 from Category c2 join c2.products p2 where p2 = p1 and c2.id = :category)") })
@EntityListeners({ GenericListener.class })
@Entity
@Table(name = "tbl_products",
	uniqueConstraints = @UniqueConstraint(name = "uk_product__name",
		columnNames = { "col_name" }),
	indexes = @Index(name = "idx_product__name", columnList = "col_name"))
public class Product extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_name", length = 150, nullable = false)
	private String name;
	
//	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "col_description", columnDefinition = "mediumtext",
		nullable = false)
	private String description;
	
	@Column(name = "col_unit")
	private Byte unit;
	
	@Column(name = "col_unitprice",
		columnDefinition = "decimal(12,2) unsigned not null default 0")
	private BigDecimal unitPrice = BigDecimal.valueOf(0.00);
	
	@OneToMany(mappedBy = "product",
		cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	private List<OrderItem> orderItems;
	
	@ManyToMany(fetch = FetchType.LAZY,
		cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "tbl_product_category",
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_product_category__product_id",
				foreignKeyDefinition = "foreign key (product_id)"
					+ " references tbl_products(id) on delete cascade")),
		inverseJoinColumns = @JoinColumn(name = "category_id",
			foreignKey = @ForeignKey(name = "fk_product_category__category_id",
				foreignKeyDefinition = "foreign key (category_id)"
					+ " references tbl_categories(id) on delete cascade")))
	private Set<Category> categories;
	
	@OneToOne(mappedBy = "product")
	private ProductStock stock;
	
	@ElementCollection
	@CollectionTable(name = "tbl_product_tag",
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_product_tag__product_id")))
	@Column(name = "col_tag", length = 50, nullable = false)
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "tbl_product_attribute", joinColumns = @JoinColumn(
		name = "product_id",
		foreignKey = @ForeignKey(name = "fk_product_attribute__product_id")))
	private List<Attribute> attributes;
	
	@ElementCollection
	@CollectionTable(name = "tbl_product_image",
		joinColumns = @JoinColumn(name = "product_id",
			foreignKey = @ForeignKey(name = "fk_product_image__product_id")))
//				foreignKeyDefinition = "foreign key (product_id) "
//					+ "references tbl_products(id) on delete cascade")))
	private List<Image> images;
	
	@Column(name = "col_image", length = 5242880) // 5242880 Bytes = 5MB
	private byte[] image;
	
	public ProductUnit getUnit() {
		return ProductUnit.toEnum(this.unit);
	}
	
	public void setUnit(ProductUnit unit) {
		this.unit = unit.getCode();
	}
	
}
