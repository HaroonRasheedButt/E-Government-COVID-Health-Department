package com.example.EGovt_CovidHealthApp.Repostiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;

@Repository
public interface PatientVaccinationRepository extends JpaRepository<PatientVaccination, Long> {
	List<PatientVaccination> findAllByStatusTrue();
}
