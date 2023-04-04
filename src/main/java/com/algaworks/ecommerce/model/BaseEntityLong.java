package com.algaworks.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class BaseEntityLong {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "col_date_create", columnDefinition = "timestamp",
		updatable = false)
	private LocalDateTime dateCreate;
	
	@Column(name = "col_date_update", columnDefinition = "timestamp",
		insertable = false)
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
