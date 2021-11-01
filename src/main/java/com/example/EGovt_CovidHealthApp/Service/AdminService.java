package com.example.EGovt_CovidHealthApp.Service;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;

import java.util.List;

@Service
public class AdminService {
	private final CompanyService companyService;
	private final HospitalService hospitalService;
	private final PatientService patientService;
	private static final Logger LOG = LogManager.getLogger(AdminService.class);

	public AdminService(CompanyService companyService, HospitalService hospitalService,
			PatientService patientService) {
		this.companyService = companyService;
		this.hospitalService= hospitalService;
		this.patientService= patientService;
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function gets all the companies details in database.
	 * @param N/A
	 * @throws Exception the exception
	 * @return Response Entity of type Company
	 **/
	public ResponseEntity<List<Company>> getAllCompanies() {
		return companyService.getAllCompanies();
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a company in database.
	 * @param Company: A company object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Company
	 **/
	public ResponseEntity<Company> addCompany(Company company) {
		return companyService.addCompany(company);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a company in database.
	 * @param Company: A company object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Company
	 **/
	public ResponseEntity<Company> updateCompany(Company company) {
		return companyService.updateCompany(company);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function deletes a company in database by changing its
	 *              status to false.
	 * @param Path Variable : The id of the the company to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deleteCompany(List<Company> companies) {
		return companyService.deleteCompany(companies);
	}

	//------------------------------------ Admin Hospital Operations
	
	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a hospital in database.
	 * @param Hospital: A hospital object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Hospital> addHospital(Hospital hospital) {
		return hospitalService.addHospital(hospital);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a hospital in database.
	 * @param Hospital: A hospital object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Hospital> updateHospital(Hospital hospital) {
		return hospitalService.updateHospital(hospital);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function deletes a hospital in database by changing its
	 *              status to false.
	 * @param Path Variable : The id of the the hospital to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deleteHospital(List<Hospital> companies) {
		return hospitalService.deleteHospital(companies);
	}

	
}
