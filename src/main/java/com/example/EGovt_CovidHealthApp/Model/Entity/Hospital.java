package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
@Entity
@Table(indexes = {
        @Index(name = "createdDate_index", columnList = "createdDate")
})
public class Hospital {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NotBlank(message = "name can not be null/empty")
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
    @PositiveOrZero(message = "It should be either positive or zero integer!")
    @Digits(fraction = 0, integer = 5, message= "Wrong input of Total Number of beds")
    private int totalNumberOfBeds;
    @Column
    @PositiveOrZero
    @Digits(fraction = 0, integer = 5)
    private int totalNumberOfVentilators;
    @Column
    @PositiveOrZero(message = "It should be either positive or zero integer!")
    @Digits(fraction = 0, integer = 5, message= "Wrong input of available Number of beds")
    private int availableNumberOfBeds;
    @Column
    @PositiveOrZero(message = "It should be either positive or zero integer!")
    @Digits(fraction = 0, integer = 5, message= "Wrong input of available Number of ventilators")
    private int availableNumberOfVentilators;
    @Column
	@NotBlank(message="address should not be empty / null")
    private String address;
    @Column
	@NotBlank(message="city should not be empty / null")
    private String city;
    @Column
	@NotBlank(message="province should not be empty / null")
    private String province;


	@ManyToMany(targetEntity = User.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<User> patients = new ArrayList<User>();
    
    @OneToMany(targetEntity = CovidTest.class,  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CovidTest> covidTests = new ArrayList<CovidTest>();
    
    @OneToMany(targetEntity = MobileVaccineCar.class,  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MobileVaccineCar> mobileVaccineCars = new ArrayList<MobileVaccineCar>();

	@OneToMany(targetEntity = User.class,  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<User> covidAdminOperators = new ArrayList<User>();


}
