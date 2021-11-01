package com.example.EGovt_CovidHealthApp.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Repostiory.HospitalRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

@Service
public class HospitalService {
	private final HospitalRepository hospitalRepository;
	private final PatientService patientService;
	private final PatientReportService patientReportService;
	private final PatientVaccinationService patientVaccinationService;
	private static final Logger LOG = LogManager.getLogger(AdminService.class);

	public HospitalService(HospitalRepository hospitalRepository, PatientReportService patientReportService,
			PatientVaccinationService patientVaccinationService, PatientService patientService) {
		this.hospitalRepository = hospitalRepository;
		this.patientService = patientService;
		this.patientReportService = patientReportService;
		this.patientVaccinationService = patientVaccinationService;
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function gets all the hospitals details in database.
	 * @param N/A
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<List<Hospital>> getAllHospitals() {
		try {
			Optional<List<Hospital>> hospitals = Optional.of(hospitalRepository.findAllByStatusTrue());
			if (hospitals.isPresent()) {
				LOG.info("Hospitals successfully Retrieved : " + hospitals.get());
				return ResponseEntity.ok().body(hospitals.get());
			} else {
				LOG.info("Hospitals Not found in the database: " + hospitals.get());
				return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception caught while retrieving hospitals data : \n" + e.getMessage());
			return new ResponseEntity("Error retrieving all hospitals!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 1st November 2021
	 * @description This function gets a hospital based on an id from database.
	 * @param N/A
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Hospital> getHospitalById(long hospitalId) {
		try {
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			if (hospital.isPresent()) {
				LOG.info("Hospital found by id.");
				return ResponseEntity.ok().body(hospital.get());
			} else {
				LOG.info("Hospital not found");
				return new ResponseEntity("Hospital Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Exception caught in get all hospital. Unable to get all hospitals.");
			return new ResponseEntity("Unable to get hospital by id\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function adds a hospital in database.
	 * @param Hospital: A hospital object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Hospital> addHospital(Hospital hospital) {
		try {
			hospital.setCreatedDate(DateTimeUtil.getDate());
			hospital.setStatus(true);
			hospitalRepository.save(hospital);
			LOG.info("Hospitals successfully added to the database: " + hospital);
			return ResponseEntity.ok().body(hospital);
		} catch (PropertyValueException e) {
			LOG.info("The syntax of the hospital object is invalid : " + hospital + e.getMessage());
			return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the hospital object to database  : " + hospital + e.getMessage());
			return new ResponseEntity("Error adding a hospital into database!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function updates a hospital in database.
	 * @param Hospital: A hospital object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Hospital> updateHospital(Hospital hospital) {
		try {
			Optional<Hospital> exists = hospitalRepository.findById(hospital.getId());
			if (exists.isPresent()) {
				hospital.setUpdatedDate(DateTimeUtil.getDate());
				hospitalRepository.save(hospital);
				LOG.info("Hospitals successfully updated in the database: " + hospital);
				return ResponseEntity.ok().body(hospital);
			} else {
				LOG.info("Copmany could not be updated because the compnay id could not be found  : ");
				return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
						HttpStatus.ACCEPTED);
			}

		} catch (PropertyValueException e) {
			LOG.info("The syntax of the hospital object is invalid : " + hospital + e.getMessage());
			return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the hospital object to database  : " + hospital + e.getMessage());
			return new ResponseEntity("Error updating hospital!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function deletes a hospital in database by changing its
	 *              status to false.
	 * @param Path Variable : The id of the the hospital to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deleteHospital(List<Hospital> hospitals) {
		try {
			for (Hospital hospital : hospitals) {
				hospital.setStatus(false);
				hospitalRepository.save(hospital);
			}
			LOG.info("Compnaies deleted successfully bu turning their status to false!");
			return ResponseEntity.ok().body("hospitals successfully deleted");
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while deleting the hospital object from database  : " + e.getMessage());
			return new ResponseEntity("Error while deleting hospitals!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function adds a patientReport in database.
	 * @param PatientReport: A patientReport object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type PatientReport
	 **/
	public ResponseEntity<Hospital> addPatientReport(PatientReport patientReport, long patientId, long hospitalId) {

		try {
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			if (hospital.isPresent()) {
				ResponseEntity<Patient> patient = patientService.getPatientById(patientId);
				if (patient.getBody() != null) {
					List<PatientReport> patientReports = patient.getBody().getCovidReports();
					patientReports.add(patientReport);
					// clear is done to avoid an error --> A collection with
					// cascade="all-delete-orphan" was no longer referenced by the owning entity
					// instance:
					patient.getBody().getCovidReports().clear();
					patient.getBody().getCovidReports().addAll(patientReports);

					patient = patientService.updatePatient(patient.getBody());
					List<Patient> patientsOfHospital = hospital.get().getPatients();

					int index = patientInHospitalExists(patientsOfHospital, patientId);
					if (index >= 0) {
						hospital.get().getPatients().set(index, patient.getBody());
						 return updateHospital(hospital.get());
						
					} else {
						hospital.get().getPatients().add(patient.getBody());
						hospital = Optional.ofNullable(hospitalRepository.save(hospital.get()));
						return ResponseEntity.ok().body(hospital.get());
					}

				} else {
					return new ResponseEntity("The patient of this id does not exist. Please enter a valid ID.",
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity(
					"Error while error adding the patient report to the database. \n" + e.getMessage(),
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
	public ResponseEntity<PatientVaccination> addPatientVaccination(PatientVaccination patientVaccination,
			long patientId) {
		return patientVaccinationService.addPatientVaccination(patientVaccination, patientId);
	}

	/**
	 * Checks if the patient id alread exist in the hospital or not
	 * 
	 * @param patients
	 * @param checkPatientId
	 * @return
	 */
	private int patientInHospitalExists(List<Patient> patients, long checkPatientId) {
		int index = 0;
		for (Patient patient : patients) {
			if (patient.getId() == checkPatientId)
				return index;

			index++;
		}
		return -1;
	}

}
