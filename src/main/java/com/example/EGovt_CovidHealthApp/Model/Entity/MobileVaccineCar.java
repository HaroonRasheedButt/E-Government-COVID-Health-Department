package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(indexes = {
		@Index(name = "createdDate_index", columnList = "createdDate"),
		@Index(name = "carRegNumber_index", columnList = "carRegistrationNumber")
})
public class MobileVaccineCar {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	@NotBlank(message = "name can not be null/empty")
	private String name;
	@Column(nullable = false)
	@NotBlank(message = "location can not be null/empty")
	private String location;
	@Column(nullable = false)
	@NotBlank(message = "contact Num can not be null/empty")
	private String contactNumber;
	@Column(nullable = false)
	@NotBlank(message = "car reg number can not be null/empty")
	private String carRegistrationNumber;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getCarRegistrationNumber() {
		return carRegistrationNumber;
	}
	public void setCarRegistrationNumber(String carRegistrationNumber) {
		this.carRegistrationNumber = carRegistrationNumber;
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
