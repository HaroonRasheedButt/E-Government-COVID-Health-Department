package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(indexes = {
		@Index(name = "createdDate_index", columnList = "createdDate"),
		@Index(name = "companyName_index", columnList = "name")
})
public class Company {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true)
	@NotEmpty
	private String name;
	@Column(nullable = false)
	@Min(value=2)
	@Positive
    @Digits(fraction = 0, integer = 10)
	private int totalEmployees;
	@Column(nullable = false)
	@PositiveOrZero
    @Digits(fraction = 0, integer = 10)
	private int vaccinatedEmployees;
	@Column(nullable = true)
	@PositiveOrZero
    @Digits(fraction = 0, integer = 10)
	private int nonVaccinatedEmployees;
	@Column(nullable = true)
	@PositiveOrZero
    @Digits(fraction = 4, integer = 10)
	private double vaccinationPercentage;
    @Column(nullable = true)
    private Date createdDate;
    @Column(nullable = true)
    private Date updatedDate;
    @Column
    private boolean status;
    
    
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalEmployees() {
		return totalEmployees;
	}
	public void setTotalEmployees(int totalEmployees) {
		this.totalEmployees = totalEmployees;
	}
	public int getVaccinatedEmployees() {
		return vaccinatedEmployees;
	}
	public void setVaccinatedEmployees(int vaccinatedEmployees) {
		this.vaccinatedEmployees = vaccinatedEmployees;
	}
	public int getNonVaccinatedEmployees() {
		return nonVaccinatedEmployees;
	}
	public void setNonVaccinatedEmployees(int nonVaccinatedEmployees) {
		this.nonVaccinatedEmployees = nonVaccinatedEmployees;
	}
	public double getVaccinationPercentage() {
		return vaccinationPercentage;
	}
	public void setVaccinationPercentage(double vaccinationPercentage) {
		this.vaccinationPercentage = vaccinationPercentage;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
    
}
