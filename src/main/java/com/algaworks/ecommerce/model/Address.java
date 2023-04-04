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
	
	@Column(name = "col_street", length = 50)
	private String street;
	
	@Column(name = "col_number", length = 10)
	private String number;
	
	@Column(name = "col_complement", length = 20)
	private String complement;
	
	@Column(name = "col_district", length = 20)
	private String district;
	
	@Column(name = "col_city", length = 20)
	private String city;
	
	@Column(name = "col_estate", length = 2)
	private String estate;
	
	@Column(name = "col_zipcode", length = 10)
	private String zipCode;
	
}
