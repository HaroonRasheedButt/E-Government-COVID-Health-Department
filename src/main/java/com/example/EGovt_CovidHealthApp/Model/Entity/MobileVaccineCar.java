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
@Table(name = "mobile_vaccine_car")
public class MobileVaccineCar {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String location;
	@Column(nullable = false)
	private String contactNumber;
	@Column(nullable = false)
	private String carRegistrationNumber;
    @Column(nullable = true)
    private String createdDate;
    @Column(nullable = true)
    private String updatedDate;
    @Column
    private boolean status;
}
