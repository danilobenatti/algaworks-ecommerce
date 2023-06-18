package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
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
@Entity
@Table(name = "tbl_categories",
	uniqueConstraints = @UniqueConstraint(name = "uk_categories__name",
		columnNames = { "col_name" }))
public class Category extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Version
	@Column(name = "col_version")
	private Long version;
	
	@NotBlank(message = "Field [name] for Category is required")
	@Size(max = 100,
		message = "Category '${validatedValue}' to must be max {max} characters long")
	@Column(name = "col_name", length = 100, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "parent_category_id",
		foreignKey = @ForeignKey(name = "fk_category__category_id",
			foreignKeyDefinition = "foreign key (parent_category_id) "
				+ "references tbl_categories(id) on delete cascade"))
	private Category parentCategory;
	
	@OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
	private List<Category> categories;
	
	@ManyToMany(mappedBy = "categories",
		cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	private List<Product> products;
	
}
