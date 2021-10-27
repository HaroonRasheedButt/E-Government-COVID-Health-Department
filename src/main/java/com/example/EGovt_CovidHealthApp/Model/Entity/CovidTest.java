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
@Table(name = "covid_test")
public class CovidTest {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String description;
    @Column(nullable = true)
    private String createdDate;
    @Column(nullable = true)
    private String updatedDate;
    @Column
    private boolean status;
}
