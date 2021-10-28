package com.example.EGovt_CovidHealthApp.Service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;

import com.example.EGovt_CovidHealthApp.DateTime;
import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Repostiory.CompanyRepsitory;
import com.example.EGovt_CovidHealthApp.Repostiory.HospitalRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
	private final CompanyRepsitory companyRepository;
	private final HospitalRepository hospitalRepository;
	private final PatientRepository patientRepository;
    private static final Logger LOG = LogManager.getLogger(AdminService.class);

	public AdminService(CompanyRepsitory companyRepsitory, HospitalRepository hospitalRepsitory,
				PatientRepository patientRepsitory) {
			this.companyRepository = companyRepsitory;
			this.hospitalRepository = hospitalRepsitory;
			this.patientRepository = patientRepsitory;
		}

    /**
     * @creationDate 28 October 2021
     * @description This function gets all the companies details in database.
     * @param N/A
     * @throws Exception the exception
     * @return Response Entity of type Company
     **/
	public ResponseEntity<List<Company>> getAllCompanies() {
		try {
			Optional<List<Company>> companies = Optional.of(companyRepository.findAllByStatusTrue());
			if (companies.isPresent()) {
				LOG.info("Copmanies successfully REtrieved : " + companies.get());
				return ResponseEntity.ok().body(companies.get());
			} else {
				LOG.info("Copmanies Not found in the database: " + companies.get());
				return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception caught while retrieving copmanies data : \n" + e.getMessage());
			return new ResponseEntity("Error retrieving all companies!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

    /**
     * @creationDate 28 October 2021
     * @description This function adds a company in database.
     * @param Company: A company object to be added
     * @throws Exception the exception
     * @return Response Entity of type Company
     **/
	public ResponseEntity<Company> addCompany(Company company) {
		try {
			this.calculateCompanyRemainingFields(company);
			company.setCreatedDate(DateTime.getDate());
			company.setStatus(true);
			companyRepository.save(company);
			return ResponseEntity.ok().body(company);
		}catch (PropertyValueException e) {
			return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity("Error adding a copmany into database!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

    /**
     * @creationDate 28 October 2021
     * @description This function updates a company in database.
     * @param Company: A company object to be added
     * @throws Exception the exception
     * @return Response Entity of type Company
     **/
	public ResponseEntity<Company> updateCompany(Company company) {
		try {
			Optional<Company> exists = companyRepository.findById(company.getId());
			if (exists.isPresent()) {
				company.setUpdatedDate(DateTime.getDate());
				this.calculateCompanyRemainingFields(company);
				companyRepository.save(company);
				return ResponseEntity.ok().body(company);
			} else {
				return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
						HttpStatus.ACCEPTED);
			}

		}catch (PropertyValueException e) {
			return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity("Error updating copmany!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
     * @creationDate 28 October 2021
     * @description This function deletes a company in database by changing its status to false.
     * @param Path Variable : The id of the the company to be deleted
     * @throws Exception the exception
     * @return Response Entity of type String
     **/
	public ResponseEntity<String> deleteCompany(List<Company> companies){
		try {
			for (Company company : companies) {
				company.setStatus(false);
				companyRepository.save(company);
			}
			return ResponseEntity.ok().body("copmanies successfully deleted");
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity("Error while deleting companies!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
     * @creationDate 28 October 2021
     * @description This function deletes a company in database by changing its status to false.
     * @param Copmany : A company object whose remaining fields are to be calculated
     * @return An object of company
     **/
	private Company calculateCompanyRemainingFields(Company company) {
		company.setNonVaccinatedEmployees(company.getTotalEmployees() - company.getVaccinatedEmployees());
		double percentage = ((double)company.getVaccinatedEmployees()/(double)company.getTotalEmployees())*100;
		company.setVaccinationPercentage(percentage);
		
		return company;
	}
}
