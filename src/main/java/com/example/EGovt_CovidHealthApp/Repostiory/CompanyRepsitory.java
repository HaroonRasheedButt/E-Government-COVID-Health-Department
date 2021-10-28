package com.example.EGovt_CovidHealthApp.Repostiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;

public interface CompanyRepsitory extends JpaRepository<Company, Long>{
	List<Company> findAllByStatusTrue();
}
