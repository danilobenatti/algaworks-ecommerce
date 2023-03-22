package com.algaworks.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_description")
	private String description;
	
	@Column(name = "col_value")
	private String value;
	
}
