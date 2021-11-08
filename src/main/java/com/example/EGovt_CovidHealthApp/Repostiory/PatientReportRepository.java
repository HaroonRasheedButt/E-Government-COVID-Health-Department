package com.example.EGovt_CovidHealthApp.Repostiory;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;

@Repository
public interface PatientReportRepository extends JpaRepository<PatientReport, Long> {
	
	List<PatientReport> findAllByStatusTrue();
	Long countByCreatedDateBetweenAndTestResultsIgnoreCase(Date after, Date before, String testResult);	
}