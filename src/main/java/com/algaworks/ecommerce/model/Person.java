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
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.StoredProcedureParameter;
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
@NamedStoredProcedureQueries(
	value = { @NamedStoredProcedureQuery(name = "purchasesAboveAverageByYear",
		procedureName = "purchases_above_average_by_year",
		parameters = { @StoredProcedureParameter(name = "year_date",
			type = Integer.class, mode = ParameterMode.IN) },
		resultClasses = Person.class) })
@SecondaryTable(name = "tbl_person_detail",
	foreignKey = @ForeignKey(name = "fk_persondetail__person_id"),
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "person_id"))
@Entity
@Table(name = "tbl_persons",
	uniqueConstraints = @UniqueConstraint(name = "uk_person__taxidnumber",
		columnNames = { "col_taxidnumber" }),
	indexes = @Index(name = "idx_person__firstname",
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
	
	@Column(name = "col_gender", table = "tbl_person_detail", length = 6)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
	private List<Order> orders;
	
	@Column(name = "col_taxidnumber", length = 14, nullable = false)
	private String taxIdNumber;
	
	@ElementCollection
	@CollectionTable(name = "tbl_person_phones",
		joinColumns = @JoinColumn(name = "person_id",
			foreignKey = @ForeignKey(name = "fk_personphone__person_id")))
	@MapKeyColumn(name = "col_type", columnDefinition = "char(1)",
		nullable = false)
	@Column(name = "col_number", length = 20, nullable = false)
	private Map<Character, String> phones;
	
}
