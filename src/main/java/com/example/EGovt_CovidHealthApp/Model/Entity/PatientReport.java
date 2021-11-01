package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;


@Data
@Entity
public class PatientReport {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private Date testDate;
	@Column
	private String testResults;
	
//	@OneToOne(targetEntity = Hospital.class, fetch = FetchType.LAZY, orphanRemoval = true)
//	private Hospital hospital;
//	@OneToOne(targetEntity = Lab.class, fetch = FetchType.LAZY, orphanRemoval = true)
//    private Lab lab;
    
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
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public String getTestResults() {
		return testResults;
	}
	public void setTestResults(String testResults) {
		this.testResults = testResults;
	}
//	public Hospital getHospital() {
//		return hospital;
//	}
//	public void setHospital(Hospital hospital) {
//		this.hospital = hospital;
//	}
//	public Lab getLab() {
//		return lab;
//	}
//	public void setLab(Lab lab) {
//		this.lab = lab;
//	}
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