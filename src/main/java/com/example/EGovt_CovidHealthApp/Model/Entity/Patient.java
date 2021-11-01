package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;


@Data
@Entity
public class Patient {
	public Patient() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = true)
	private Date createdDate;
	@Column(nullable = true)
	private Date updatedDate;
	@Column
	private boolean status;
	@Column
	private boolean hasCovid;
	@Column
	private boolean isVaccinated;
	@Column(nullable = false)
	private String contactNum;
	@Column(nullable = false)
	private String cnic;
	@Column(nullable = false)
	private int age;
	@Column
	private String address;
	@Column
	private String currentCity;
	@Column
	private String province;
	@Column
	private String postalCode;
	
	@OneToMany(targetEntity = PatientReport.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientReport> covidReports = new ArrayList<PatientReport>();
    
    @OneToMany(targetEntity = PatientVaccination.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientVaccination> covidVaccines = new ArrayList<PatientVaccination>();
    
	
	public List<PatientReport> getCovidReports() {
		return covidReports;
	}

	public void setCovidReports(List<PatientReport> covidReports) {
		this.covidReports = covidReports;
	}

	public List<PatientVaccination> getCovidVaccines() {
		return covidVaccines;
	}

	public void setCovidVaccines(List<PatientVaccination> covidVaccines) {
		this.covidVaccines = covidVaccines;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isHasCovid() {
		return hasCovid;
	}

	public void setHasCovid(boolean hasCovid) {
		this.hasCovid = hasCovid;
	}

	public boolean isVaccinated() {
		return isVaccinated;
	}

	public void setVaccinated(boolean isVaccinated) {
		this.isVaccinated = isVaccinated;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

//	public List<PatientReport> getCovidReports() {
//		return covidReports;
//	}
//
//	public void setCovidReports(List<PatientReport> covidReports) {
//		this.covidReports = covidReports;
//	}
//
//	public List<PatientVaccination> getCovidVaccines() {
//		return covidVaccines;
//	}
//
//	public void setCovidVaccines(List<PatientVaccination> covidVaccines) {
//		this.covidVaccines = covidVaccines;
//	}

	
}
