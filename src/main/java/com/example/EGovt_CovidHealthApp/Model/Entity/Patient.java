package com.example.EGovt_CovidHealthApp.Model.Entity;

import lombok.Data;

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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.util.Date;

@Data
@Entity
public class Patient extends User{
	@Column
	private boolean isCovid;
	@Column
	private boolean isVaccinated;
	
	@OneToMany(targetEntity = PatientReport.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientReport> patientReports = new ArrayList<PatientReport>();
    
    @OneToMany(targetEntity = PatientVaccination.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientVaccination> patientVaccination = new ArrayList<PatientVaccination>();
	
}
