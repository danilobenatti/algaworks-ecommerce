package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.algaworks.ecommerce.model.enums.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@SecondaryTable(name = "tbl_person_detail",
	foreignKey = @ForeignKey(name = "fk_persondetail_person_id"),
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "person_id"))
@Entity
@Table(name = "tbl_persons",
	uniqueConstraints = @UniqueConstraint(name = "uk_person_taxidnumber",
		columnNames = { "col_taxidnumber" }),
	indexes = @Index(name = "idx_person_firstname",
		columnList = "col_firstname"))
public class Person extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_firstname", length = 100, nullable = false)
	private String firstname;
	
	@Transient
	@Column(name = "col_lastname")
	private String lastname;
	
	@Column(name = "col_birthday", table = "tbl_person_detail")
	private LocalDate birthday;
	
	@Column(name = "col_gender", table = "tbl_person_detail")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
	private List<Order> orders;
	
	@Column(name = "col_taxidnumber", length = 14, nullable = false)
	private String taxIdNumber;
	
	@ElementCollection
	@CollectionTable(name = "tbl_person_phones",
		joinColumns = @JoinColumn(name = "person_id",
			foreignKey = @ForeignKey(name = "fk_personphone_person_id")))
	@MapKeyColumn(name = "col_type")
	@Column(name = "col_number")
	private Map<String, String> phones;
	
}
