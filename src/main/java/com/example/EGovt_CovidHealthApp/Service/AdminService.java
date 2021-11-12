package com.example.EGovt_CovidHealthApp.Service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminService {
	private final CompanyService companyService;
	private final HospitalService hospitalService;
	private final PatientRepository patientRepository;
	private final LabService labService;
	private static final Logger LOG = LogManager.getLogger(AdminService.class);

	public AdminService(CompanyService companyService, HospitalService hospitalService,
			PatientRepository patientRepository, LabService labService) {
		this.companyService = companyService;
		this.hospitalService = hospitalService;
		this.patientRepository = patientRepository;
		this.labService = labService;
	}

	// ------------------------------------Admin Company
	// Operations------------------------------------ //
	/**
	 * @creationDate 28 October 2021
	 * @description This function gets all the companies details in database.
	 * @throws Exception the exception
	 * @return Response Entity of type Company
	 **/
	public ResponseEntity<List<Company>> getAllCompanies() {
		return companyService.getAllCompanies();
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a company in database.
	 * @param company: A company object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Company
	 **/
	public ResponseEntity<Company> addCompany(Company company) {
		return companyService.addCompany(company);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a company in database.
	 * @param company: A company object to be added
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
	 * @param companies : The id of the the company to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deleteCompany(List<Company> companies) {
		return companyService.deleteCompany(companies);
	}

	// ------------------------------------Admin Hospital
	// Operations------------------------------------ //

	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a hospital in database.
	 * @param hospital: A hospital object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Hospital> addHospital(Hospital hospital) {
		return hospitalService.addHospital(hospital);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a hospital in database.
	 * @param hospital: A hospital object to be updated
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
	 * @param hospitals : The id of the the hospital to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deleteHospital(List<Hospital> hospitals) {
		return hospitalService.deleteHospital(hospitals);
	}

	// -------------Admin Patient Operations-------------//

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a patient's detail in database.
	 * @param patient: A Patient object to be updated
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Patient> updatePatient(Patient patient) {
		try {
			Optional<Patient> exists = patientRepository.findById(patient.getId());
			if (exists.isPresent()) {
				patient.setUpdatedDate(DateTimeUtil.getDate());
				patientRepository.save(patient);
				LOG.info("Patient successfully updated in the database: " + patient);
				return ResponseEntity.ok().body(patient);
			} else {
				LOG.info("Patient could not be updated because the id could not be found  : ");
				return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
						HttpStatus.ACCEPTED);
			}

		} catch (PropertyValueException e) {
			LOG.info("The syntax of the patient object is invalid : " + patient + e.getMessage());
			return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patient object to database  : " + patient + e.getMessage());
			return new ResponseEntity("Error updating patient!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function deletes a patient in database by changing its
	 *              status to false.
	 * @param patients : The id of the the patient to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deletePatient(List<Patient> patients) {
		try {

			for (Patient patient : patients) {
				if (Objects.isNull(patient.getId()))
					return new ResponseEntity("Please provide the ID of patient, having email : " + patient.getEmail(),
							HttpStatus.PARTIAL_CONTENT);
				patient.setStatus(false);
				patientRepository.save(patient);
			}
			LOG.info("Patients deleted successfully bu turning their status to false!");
			return ResponseEntity.ok().body("patients successfully deleted");
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while deleting the patient object from database  : " + e.getMessage());
			return new ResponseEntity("Error while deleting patients!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}


	// ------------------------------------Admin Lab
	// Operations------------------------------------ //
	
	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a lab in database.
	 * @param lab: A lab object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	public ResponseEntity<Lab> addLab(Lab lab) {
		return labService.addLab(lab);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a lab in database.
	 * @param lab: A lab object to be updated
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	public ResponseEntity<Lab> updateLab(Lab lab) {
		return labService.updateLab(lab);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function deletes a lab in database by changing its
	 *              status to false.
	 * @param labs : The id of the the lab to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deleteLab(List<Lab> labs) {
		return labService.deleteLab(labs);
	}
	
}
