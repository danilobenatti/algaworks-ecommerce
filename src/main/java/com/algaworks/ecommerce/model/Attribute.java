package com.algaworks.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
	
	@NotBlank(message = "Attribute [description] is mandatory")
	@Size(max = 100,
		message = "Field [description] maximum of {max} characters long.")
	@Column(name = "col_description", length = 100, nullable = false)
	private String description;
	
	@NotBlank(message = "Attribute value is required")
	@Size(max = 255,
		message = "Field [value] maximum of {max} characters long.")
	@Column(name = "col_value", nullable = false)
	private String value;
	
}
