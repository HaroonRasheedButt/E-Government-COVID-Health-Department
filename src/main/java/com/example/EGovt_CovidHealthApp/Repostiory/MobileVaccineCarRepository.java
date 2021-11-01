package com.example.EGovt_CovidHealthApp.Repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.MobileVaccineCar;

@Repository
public interface MobileVaccineCarRepository  extends JpaRepository<MobileVaccineCar, Long>{
	
}
