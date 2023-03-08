package com.algaworks.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
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
@Table(name = "tbl_categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_generate")
	@TableGenerator(name = "seq_generate", table = "hibernate_sequences",
		pkColumnName = "sequence_name", pkColumnValue = "tbl_categories",
		valueColumnName = "next_val", initialValue = 10, allocationSize = 50)
	@Column(name = "id_category")
	private Long id;
	
	@Column(name = "col_name")
	private String name;
	
	@Column(name = "col_parent_category")
	private Integer parentCategory;
	
}
