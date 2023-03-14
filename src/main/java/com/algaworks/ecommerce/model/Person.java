package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.algaworks.ecommerce.model.enums.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_persons")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "col_name")
	private String name;
	
	@Column(name = "col_birthday")
	private LocalDate birthday;
	
	@Column(name = "col_gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
	private List<Order> orders;
	
	@Column(name = "col_date_create")
	private LocalDateTime dateCreate;
	
	@Column(name = "col_date_update")
	private LocalDateTime dateUpdate;
	
	@PrePersist
	private void onPersist() {
		this.dateCreate = LocalDateTime.now();
	}
	
	@PreUpdate
	private void onUpdate() {
		this.dateUpdate = LocalDateTime.now();
	}
	
}
