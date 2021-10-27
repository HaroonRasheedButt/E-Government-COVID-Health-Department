package com.example.EGovt_CovidHealthApp.Repostiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EGovt_CovidHealthApp.Model.Entity.MobileVaccineCar;

public interface MobileVaccineCarRepository  extends JpaRepository<MobileVaccineCar, Long>{
	
}
