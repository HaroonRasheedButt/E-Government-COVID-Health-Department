package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
@Entity
public class Hospital {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NotBlank(message = "name can not be null/empty")
	private String name;
    @Column(nullable = false, unique = true)
	@Email
	@NotBlank(message="name should not be empty / null")
    private String email;
    @Column(nullable = false)
	@NotBlank(message="password should not be empty / null")
    private String password;
    @Column(nullable = true)
    private Date createdDate;
    @Column(nullable = true)
    private Date updatedDate;
    @Column
    private boolean status;
    @Column
    @PositiveOrZero(message = "It should be either positive or zero integer!")
    @Digits(fraction = 0, integer = 5, message= "Wrong input of Total Number of beds")
    private int totalNumberOfBeds;
    @Column
    @PositiveOrZero
    @Digits(fraction = 0, integer = 5)
    private int totalNumberOfVentilators;
    @Column
    @PositiveOrZero(message = "It should be either positive or zero integer!")
    @Digits(fraction = 0, integer = 5, message= "Wrong input of available Number of beds")
    private int availableNumberOfBeds;
    @Column
    @PositiveOrZero(message = "It should be either positive or zero integer!")
    @Digits(fraction = 0, integer = 5, message= "Wrong input of available Number of ventilators")
    private int availableNumberOfVentilators;
    @Column
	@NotBlank(message="address should not be empty / null")
    private String address;
    @Column
	@NotBlank(message="city should not be empty / null")
    private String city;
    @Column
	@NotBlank(message="province should not be empty / null")
    private String province;


	@ManyToMany(targetEntity = Patient.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Patient> patients = new ArrayList<Patient>();
    
    @OneToMany(targetEntity = CovidTest.class,  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CovidTest> covidTests = new ArrayList<CovidTest>();
    
    @OneToMany(targetEntity = MobileVaccineCar.class,  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MobileVaccineCar> mobileVaccineCars = new ArrayList<MobileVaccineCar>();
    
    
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getTotalNumberOfBeds() {
		return totalNumberOfBeds;
	}

	public void setTotalNumberOfBeds(int totalNumberOfBeds) {
		this.totalNumberOfBeds = totalNumberOfBeds;
	}

	public int getTotalNumberOfVentilators() {
		return totalNumberOfVentilators;
	}

	public void setTotalNumberOfVentilators(int totalNumberOfVentilators) {
		this.totalNumberOfVentilators = totalNumberOfVentilators;
	}

	public int getAvailableNumberOfBeds() {
		return availableNumberOfBeds;
	}

	public void setAvailableNumberOfBeds(int availableNumberOfBeds) {
		this.availableNumberOfBeds = availableNumberOfBeds;
	}

	public int getAvailableNumberOfVentilators() {
		return availableNumberOfVentilators;
	}

	public void setAvailableNumberOfVentilators(int availableNumberOfVentilators) {
		this.availableNumberOfVentilators = availableNumberOfVentilators;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<CovidTest> getCovidTests() {
		return covidTests;
	}

	public void setCovidTests(List<CovidTest> covidTests) {
		this.covidTests = covidTests;
	}

	public List<MobileVaccineCar> getMobileVaccineCars() {
		return mobileVaccineCars;
	}

	public void setMobileVaccineCars(List<MobileVaccineCar> mobileVaccineCars) {
		this.mobileVaccineCars = mobileVaccineCars;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
}
