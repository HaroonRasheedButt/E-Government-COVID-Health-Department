package com.example.EGovt_CovidHealthApp.Model.Entity;

//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "hospital")
public class Hospital {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String createdDate;
    @Column(nullable = true)
    private String updatedDate;
    @Column
    private boolean status;
    @Column
    private int totalNumberOfBeds;
    @Column
    private int totalNumberOfVentilators;
    @Column
    private int availableNumberOfBeds;
    @Column
    private int availableNumberOfVentilators;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String province;
}
