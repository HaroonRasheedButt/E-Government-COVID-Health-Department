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
@Table(name = "patient")
public class PatientVaccination {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column
    private boolean status;
    @Column
    private String vaccineName;
    @Column
    private String vaccinatedDate;
    
}
