package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.algaworks.ecommerce.dto.ProductDTO;
import com.algaworks.ecommerce.listener.GenericListener;
import com.algaworks.ecommerce.model.converter.BooleanYesOrNoConverter;
import com.algaworks.ecommerce.model.enums.ProductUnit;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FetchType;
import jakarta.persistence.FieldResult;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
		entities = { @EntityResult(entityClass = Product.class) }),
	@SqlResultSetMapping(name = "ecm_products.Product",
		entities = { @EntityResult(entityClass = Product.class,
			fields = { @FieldResult(name = "id", column = "prd_id"),
				@FieldResult(name = "version", column = "prd_version"),
				@FieldResult(name = "name", column = "prd_name"),
				@FieldResult(name = "description", column = "prd_description"),
				@FieldResult(name = "unit", column = "prd_unit"),
				@FieldResult(name = "unitPrice", column = "prd_unitprice"),
				@FieldResult(name = "image", column = "prd_image"),
				@FieldResult(name = "dateCreate", column = "prd_date_create"),
				@FieldResult(name = "dateUpdate", column = "prd_date_update"),
				@FieldResult(name = "active", column = "prd_active") }) }),
	@SqlResultSetMapping(name = "ecm_products.ProductDTO",
		classes = { @ConstructorResult(targetClass = ProductDTO.class,
			columns = { @ColumnResult(name = "prd_id", type = Long.class),
				@ColumnResult(name = "prd_name", type = String.class) }) }) })
@NamedQuery(name = "Product.listAll", query = "select p from Product p")
@NamedQuery(name = "Product.listByCategory",
	query = "select p1 from Product p1 where exists (select 1 from Category c2 "
		+ "join c2.products p2 where p2 = p1 and c2.id = :category)")
@NamedNativeQuery(name = "product_shop.listAll",
	query = "select id, col_version, col_name, col_description, col_image, col_unit, col_unitprice, "
		+ "col_date_create, col_date_update, col_active from tbl_product_shop",
	resultClass = Product.class)
@NamedNativeQuery(name = "ecm_products.listAll",
	query = "select * from tbl_ecm_products",
	resultSetMapping = "ecm_products.Product")
@EntityListeners({ GenericListener.class })
@Entity
@Table(name = "tbl_products",
	uniqueConstraints = @UniqueConstraint(name = "uk_product__name",
		columnNames = { "col_name" }),
	indexes = @Index(name = "idx_product__name", columnList = "col_name"))
public class Product extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Version
	@Column(name = "col_version")
	private Long version;
	
	@NotBlank(message = "Field [name] for Product is required")
	@Size(min = 2, max = 150,
		message = "Product name '${validatedValue}' must be between {min} and {max} characters")
	@Column(name = "col_name", length = 150, nullable = false)
	private String name;
	
	@NotBlank(message = "Field [description] for Product is required")
//	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "col_description", columnDefinition = "text",
		nullable = false)
	private String description;
	
	@Column(name = "col_unit", nullable = false)
	private Byte unit = ProductUnit.UNITY.getCode();
	
	@PositiveOrZero
	@Column(name = "col_unitprice", nullable = false,
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
	
	@Convert(converter = BooleanYesOrNoConverter.class)
	@NotNull
	@Column(name = "col_active", length = 3, nullable = false)
	private Boolean active = Boolean.FALSE;
	
	public ProductUnit getUnit() {
		return ProductUnit.toEnum(this.unit);
	}
	
	public void setUnit(ProductUnit unit) {
		this.unit = unit.getCode();
	}
	
}
