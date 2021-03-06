package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;


@Data
@Entity
@Table(indexes = {
		@Index(name = "createdDate_index", columnList = "createdDate")
})
public class PatientReport {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private Date testDate;
	@Column(nullable = false)
	private String testResults;
	@Column
    private Date createdDate;
    @Column
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