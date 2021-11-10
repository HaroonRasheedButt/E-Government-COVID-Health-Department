package com.example.EGovt_CovidHealthApp.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Repostiory.CompanyRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

@Service
public class CompanyService {
	private final CompanyRepository companyRepository;
    private static final Logger LOG = LogManager.getLogger(AdminService.class);

	public CompanyService(CompanyRepository companyRepository) {
			this.companyRepository = companyRepository;
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
			Optional<List<Company>> companies = Optional.of(companyRepository.findAllByStatusTrueOrderByCreatedDateDesc());
			if (companies.isPresent()) {
				LOG.info("Companies successfully Retrieved : " + companies.get());
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
			company.setCreatedDate(DateTimeUtil.getDate());
			company.setStatus(true);
			companyRepository.save(company);
			LOG.info("Companies successfully added to the database: " + company);
			return ResponseEntity.ok().body(company);
		}catch (PropertyValueException e) {
			LOG.info("The syntax of the company object is invalid : " + company + e.getMessage());
			return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the company object to database  : " + company + e.getMessage());
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
				company.setUpdatedDate(DateTimeUtil.getDate());
				this.calculateCompanyRemainingFields(company);
				companyRepository.save(company);
				LOG.info("Companies successfully updated in the database: " + company);
				return ResponseEntity.ok().body(company);
			} else {
				LOG.info("Copmany could not be updated because the compnay id could not be found  : " );
				return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
						HttpStatus.ACCEPTED);
			}
		}catch (PropertyValueException e) {
			LOG.info("The syntax of the company object is invalid : " + company + e.getMessage());
			return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the company object to database  : " + company + e.getMessage());
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
				if(Objects.isNull(company.getId()))
					return new ResponseEntity("Please provide the ID of company, having Id : "+ company.getId(),HttpStatus.PARTIAL_CONTENT);
				company.setStatus(false);
				companyRepository.save(company);
			}
			LOG.info("Compnaies deleted successfully bu turning their status to false!");
			return ResponseEntity.ok().body("copmanies successfully deleted");
		}catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while deleting the company object from database  : " + e.getMessage());
			return new ResponseEntity("Error while deleting companies!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
     * @creationDate 28 October 2021
     * @description This function gets all the companies details in database.
     * @param N/A
     * @throws Exception the exception
     * @return Response Entity of type Company
     **/
	public ResponseEntity<Company> findCompanyByName(String name) {
		try {
			Optional<Company> company = Optional.of(companyRepository.findByNameIgnoreCaseAndStatusTrue(name));
			if (company.isPresent()) {
				LOG.info("Company successfully Retrieved : " + company.get());
				return ResponseEntity.ok().body(company.get());
			} else {
				LOG.info("Copmany by this name could not found in the database: " + company.get());
				return new ResponseEntity("Company Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception caught while retrieving copmany data : \n" + e.getMessage());
			return new ResponseEntity("Error retrieving a company!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
		LOG.info("Copmnay remaining fileds have been calculated!!");
		return company;
	}
}
