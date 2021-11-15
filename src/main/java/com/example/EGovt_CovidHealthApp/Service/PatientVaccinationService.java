package com.example.EGovt_CovidHealthApp.Service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientVaccinationRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

@Service
public class PatientVaccinationService {
	private final PatientVaccinationRepository patientVaccinationRepository;
	private static final Logger LOG = LogManager.getLogger(SuperAdminService.class);

	public PatientVaccinationService(PatientVaccinationRepository patientVaccinationRepository) {
		this.patientVaccinationRepository = patientVaccinationRepository;
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function gets all the patientVaccinations details in
	 *              database.
	 * @param N/A
	 * @throws Exception the exception
	 * @return Response Entity of type PatientVaccination
	 **/
	public ResponseEntity<List<PatientVaccination>> getAllPatientVaccinations() {
		try {
			Optional<List<PatientVaccination>> patientVaccinations = Optional
					.of(patientVaccinationRepository.findAllByStatusTrue());
			if (patientVaccinations.isPresent()) {
				LOG.info("PatientVaccinations successfully Retrieved : " + patientVaccinations.get());
				return ResponseEntity.ok().body(patientVaccinations.get());
			} else {
				LOG.info("PatientVaccinations Not found in the database: " + patientVaccinations.get());
				return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception caught while retrieving patientVaccinations data : \n" + e.getMessage());
			return new ResponseEntity("Error retrieving all patientVaccinations!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function adds a patientVaccination in database.
	 * @param PatientVaccination: A patientVaccination object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type PatientVaccination
	 **/
	public PatientVaccination addPatientVaccination(PatientVaccination patientVaccination) throws Exception {
		try {
			patientVaccination.setCreatedDate(DateTimeUtil.getDate());
			patientVaccination.setStatus(true);
			patientVaccination.setVaccinatedDate(DateTimeUtil.getDate());
			LOG.info("PatientVaccinations successfully added to the database: " + patientVaccination);
			return patientVaccination;
		} catch (PropertyValueException e) {
			LOG.info("The syntax of the patientVaccination object is invalid : " + patientVaccination + e.getMessage());
			throw new PropertyValueException("Please send a valid object to add into the databse!\n" + e.getMessage(),
					PatientReport.class.getClass().getName(), this.toString());
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patientVaccination object to database  : " + patientVaccination + e.getMessage());
			throw new Exception("Error adding a patientVaccination into database!\n" + e.getMessage());
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function updates a patientVaccination in database.
	 * @param PatientVaccination: A patientVaccination object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type PatientVaccination
	 **/
	public PatientVaccination updatePatientVaccination(PatientVaccination patientVaccination) throws Exception {
		try {
			
				patientVaccination.setUpdatedDate(DateTimeUtil.getDate());
				LOG.info("PatientVaccinations successfully updated in the database: " + patientVaccination);
				return patientVaccination;
			

		}catch (PropertyValueException e) {
			LOG.info("The syntax of the patientVaccination object is invalid : " + patientVaccination + e.getMessage());
			throw new PropertyValueException("Please send a valid object to add into the databse!\n" + e.getMessage(),
					PatientReport.class.getClass().getName(), this.toString());
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patient Vaccination object to database  : " + patientVaccination + e.getMessage());
			throw new Exception("Error adding a patient Vaccination into database!\n" + e.getMessage());
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function deletes a patientVaccination in database by
	 *              changing its status to false.
	 * @param Path Variable : The id of the the patientVaccination to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deletePatientVaccination(List<PatientVaccination> patientVaccinations) {
		try {
			for (PatientVaccination patientVaccination : patientVaccinations) {
				patientVaccination.setStatus(false);
				patientVaccinationRepository.save(patientVaccination);
			}
			LOG.info("Compnaies deleted successfully bu turning their status to false!");
			return ResponseEntity.ok().body("patientVaccinations successfully deleted");
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while deleting the patientVaccination object from database  : " + e.getMessage());
			return new ResponseEntity("Error while deleting patientVaccinations!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

}
