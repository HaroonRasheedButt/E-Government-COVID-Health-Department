package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
@Entity
public class PatientVaccination {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column(nullable = false)
	@NotBlank(message = "vaccine name can not be null/empty")
    private String vaccineName;
    @Column(nullable = false)
    private Date vaccinatedDate;
	@Column(nullable = false)
	@Min(value=1)
	@Positive(message = "num of doses need to be positive")
    @Digits(fraction = 0, integer = 3, message="num of doeses can not be greater than 3 digits")
    private int totalDoses;
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
	public String getVaccineName() {
		return vaccineName;
	}
	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	public Date getVaccinatedDate() {
		return vaccinatedDate;
	}
	public void setVaccinatedDate(Date vaccinatedDate) {
		this.vaccinatedDate = vaccinatedDate;
	}
	public int getTotalDoses() {
		return totalDoses;
	}
	public void setTotalDoses(int totalDoses) {
		this.totalDoses = totalDoses;
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



