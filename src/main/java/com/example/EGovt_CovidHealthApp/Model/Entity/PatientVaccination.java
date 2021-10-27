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
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "patient_vaccination")
public class PatientVaccination {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column
    private String vaccineName;
    @Column
    private Date vaccinatedDate;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Hospital hospital;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Lab lab;
	@Column
    private int totalDoses;
    @Column(nullable = true)
    private String createdDate;
    @Column(nullable = true)
    private String updatedDate;
    @Column
    private boolean status;
}



