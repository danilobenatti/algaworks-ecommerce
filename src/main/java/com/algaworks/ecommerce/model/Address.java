package com.algaworks.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_street")
	private String street;
	
	@Column(name = "col_number")
	private String number;
	
	@Column(name = "col_complement")
	private String complement;
	
	@Column(name = "col_district")
	private String district;
	
	@Column(name = "col_city")
	private String city;
	
	@Column(name = "col_estate")
	private String estate;
	
	@Column(name = "col_zipcode")
	private String zipCode;
	
}
