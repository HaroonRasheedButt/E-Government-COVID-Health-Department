package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(indexes = {
		@Index(name = "createdDate_index", columnList = "createdDate")
})
public class Lab {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NotBlank
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

    @OneToMany(targetEntity = CovidTest.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CovidTest> covidTests = new ArrayList<CovidTest>();

	@OneToMany(targetEntity = User.class,  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<User> covidAdminOperators = new ArrayList<User>();



}
