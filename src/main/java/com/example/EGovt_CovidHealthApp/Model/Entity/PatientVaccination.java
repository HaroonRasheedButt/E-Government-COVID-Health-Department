package com.example.EGovt_CovidHealthApp.Model.Entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
@Entity
@Table(indexes = {
		@Index(name = "createdDate_index", columnList = "createdDate")
})
public class PatientVaccination {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column(nullable = false)
	@NotBlank(message = "vaccine name can not be null/empty")
    private String vaccineName;
    @Column(nullable = false)
    private Date vaccinatedDate;
	@Column(nullable = false)
	@Min(value=1)
	@Positive(message = "num of doses need to be positive")
    @Digits(fraction = 0, integer = 3, message="num of doeses can not be greater than 3 digits")
    private int totalDoses;
    @Column(nullable = true)
    private Date createdDate;
    @Column(nullable = true)
    private Date updatedDate;
    @Column
    private boolean status;
}



