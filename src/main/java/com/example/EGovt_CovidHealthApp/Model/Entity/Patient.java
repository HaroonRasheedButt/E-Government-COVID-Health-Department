package com.example.EGovt_CovidHealthApp.Model.Entity;

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

import java.util.Date;


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
	@Column
	private Date updatedDate;
	@Column
	private boolean status;
	@Column(nullable = false)
	private boolean isAlive;
	@Column
	private boolean isCovid;
	@Column
	private boolean isVaccinated;
	@Column(nullable = false)
	private String contactNum;
	@Column(nullable = false, unique = true)
	private String cnic;
	@Column(nullable = false)
	private int age;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String province;
	@Column
	private String postalCode;
	
	@OneToMany(targetEntity = PatientReport.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientReport> patientReports = new ArrayList<PatientReport>();
    
    @OneToMany(targetEntity = PatientVaccination.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientVaccination> patientVaccination = new ArrayList<PatientVaccination>();

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

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isCovid() {
		return isCovid;
	}

	public void setCovid(boolean isCovid) {
		this.isCovid = isCovid;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public List<PatientReport> getPatientReports() {
		return patientReports;
	}

	public void setPatientReports(List<PatientReport> patientReports) {
		this.patientReports = patientReports;
	}

	public List<PatientVaccination> getPatientVaccination() {
		return patientVaccination;
	}

	public void setPatientVaccination(List<PatientVaccination> patientVaccination) {
		this.patientVaccination = patientVaccination;
	}

	
}
