package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PatientVaccination {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column
    private String vaccineName;
    @Column
    private Date vaccinatedDate;
	@Column
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



