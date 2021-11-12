package com.example.EGovt_CovidHealthApp.Model.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class CovidAdminOperator extends User {

    @Column(nullable = false, unique = true)
    @NotBlank(message="shift should not be empty / null")
    private String shiftTime;
    @Column(nullable = false, unique = true)
    @NotBlank(message="department should not be empty / null")
    private String department;
}
