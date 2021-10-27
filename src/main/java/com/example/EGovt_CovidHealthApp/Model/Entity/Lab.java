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
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lab")
public class Lab {
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
    private String address;
    @Column
    private String city;
    @Column
    private String province; 
    
    @OneToMany(targetEntity = CovidTest.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CovidTest> covidTests = new ArrayList<CovidTest>();
    
}
