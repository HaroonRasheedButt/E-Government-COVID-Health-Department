package com.example.EGovt_CovidHealthApp.Model.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "copmany")
public class Company {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int totalEmployees;
	@Column(nullable = false)
	private int vaccinatedEmployees;
	@Column(nullable = true)
	private int nonVaccinatedEmployees;
	@Column(nullable = true)
	private int vaccinationPercentage;
    @Column(nullable = true)
    private String createdDate;
    @Column(nullable = true)
    private String updatedDate;
    @Column
    private boolean status;
}
