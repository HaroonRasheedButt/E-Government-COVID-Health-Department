package com.example.EGovt_CovidHealthApp.Repostiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
	List<Company> findAllByStatusTrue();
	List<Company> findAllByStatusTrueOrderByCreatedDateDesc();
	Company findByNameIgnoreCaseAndStatusTrue(String name);
	
}
