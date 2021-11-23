package com.example.EGovt_CovidHealthApp.Model.Entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(indexes = {
        @Index(name = "createdDate_index", columnList = "createdDate"),
        @Index(name = "email_index", columnList = "email"),
        @Index(name = "cnic_index", columnList = "cnic")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank(message = "name can not be null / empty")
    private String name;
    @Column(nullable = false, unique = true)
    @NotBlank(message="name should not be empty / null")
    private String username;
    @Column(nullable = false, unique = true)
    @Email
    @NotBlank(message = "email can not be null / empty")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "password can not be null / empty")
    private String password;
    @Column(nullable = true)
    private Date createdDate;
    @Column
    private Date updatedDate;
    @Column
    private boolean status;
    @Column(nullable = false)
    private boolean isAlive;
    @Column(nullable = false)
    @NotBlank(message="contact number can not be empty or null")
    private String contactNum;
    @Column(nullable = false, unique = true)
    private String cnic;
    @Column(nullable = false)
    @Min(value=1)
    @Positive(message = "Age needs to be positive")
    @Digits(fraction = 0, integer = 3, message="Age can not be greater than 3 digits")
    private int age;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    @NotBlank(message="city number can not be empty or null")
    private String city;
    @Column(nullable = false)
    @NotBlank(message="province can not be empty or null")
    private String province;
    @Column
    private String postalCode;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name="user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles = new ArrayList<>();
    /*for patients*/
    @Column
    private boolean isCovid;
    @Column
    private boolean isVaccinated;

    @OneToMany(targetEntity = PatientReport.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientReport> patientReports = new ArrayList<PatientReport>();

    @OneToMany(targetEntity = PatientVaccination.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientVaccination> patientVaccination = new ArrayList<PatientVaccination>();

    /*for super admin*/
    @Column
    @NotBlank(message="name should not be empty / null")
    private String assignedPost;
    @Column
    @Positive(message = "It should be a positive integer!")
    @Digits(fraction = 0, integer = 2, message= "Wrong input of available Number of beds")
    @Max(22)
    private int gradeLevel;
}
