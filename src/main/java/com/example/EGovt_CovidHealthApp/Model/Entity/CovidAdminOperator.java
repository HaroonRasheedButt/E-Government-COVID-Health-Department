package com.example.EGovt_CovidHealthApp.Model.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class CovidAdminOperator extends User {

    @NotBlank(message="shift time should not be empty / null")
    private String shiftTime;
    @NotBlank(message="department should not be empty / null")
    private String department;
}
