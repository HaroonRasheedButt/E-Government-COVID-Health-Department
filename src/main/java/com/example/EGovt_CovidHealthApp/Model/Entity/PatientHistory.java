package com.example.EGovt_CovidHealthApp.Model.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "patient")
public class PatientHistory {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String date;
	@OneToOne(cascade = CascadeType.ALL)
    private Hospital hospital;
	
}
