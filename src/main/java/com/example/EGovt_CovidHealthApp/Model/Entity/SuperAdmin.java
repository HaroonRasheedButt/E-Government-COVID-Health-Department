package com.example.EGovt_CovidHealthApp.Model.Entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table
public class SuperAdmin extends User{
    @Column(nullable = false, unique = true)
    @NotBlank(message="name should not be empty / null")
    private String username;
    @Column(nullable = false, unique = true)
    @NotBlank(message="name should not be empty / null")
    private String assignedPost;
    @Column(nullable = false, unique = true)
    @Positive(message = "It should be a positive integer!")
    @Digits(fraction = 0, integer = 2, message= "Wrong input of available Number of beds")
    @Max(22)
    private int gradeLevel;
}

