package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
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
