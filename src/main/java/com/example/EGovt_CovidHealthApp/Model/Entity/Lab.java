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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Lab {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NotBlank
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
    
    @OneToMany(targetEntity = CovidTest.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CovidTest> covidTests = new ArrayList<CovidTest>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
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
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}
