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

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.MobileVaccineCar;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Repostiory.HospitalRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.MobileVaccineCarRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

@Service
public class HospitalService {
	private final HospitalRepository hospitalRepository;
	private final PatientRepository patientRepository;
	private final MobileVaccineCarRepository mobileVaccineCarRepository;
	private final PatientService patientService;
	private final PatientReportService patientReportService;
	private final PatientVaccinationService patientVaccinationService;
	private static final Logger LOG = LogManager.getLogger(HospitalService.class);

	public HospitalService(HospitalRepository hospitalRepository, PatientReportService patientReportService,
			PatientVaccinationService patientVaccinationService, PatientService patientService,
			MobileVaccineCarRepository mobileVaccineCarRepository, PatientRepository patientRepository) {
		this.hospitalRepository = hospitalRepository;
		this.patientRepository = patientRepository;
		this.mobileVaccineCarRepository = mobileVaccineCarRepository;
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
			Optional<List<Hospital>> hospitals = Optional.of(hospitalRepository.findAllByStatusTrueOrderByCreatedDateDesc());
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
				LOG.info("Hospital could not be updated because the id could not be found  : ");
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
				if(Objects.isNull(hospital.getId()))
					return new ResponseEntity("Please provide the ID of hospital, having email : "+ hospital.getEmail(),HttpStatus.PARTIAL_CONTENT);
				hospital.setStatus(false);
				hospitalRepository.save(hospital);
			}
			LOG.info("Hospitals deleted successfully bu turning their status to false!");
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
	 * @param PatientCnic:   A patient CNIC that is unique for every patient
	 * @param long:          A hospital id
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	public ResponseEntity<Hospital> addPatientReport(PatientReport patientReport, String patientCnic, long hospitalId) {

		try {
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			if (hospital.isPresent()) {
				Optional<Patient> patient = Optional.ofNullable(patientRepository.findByCnicAndStatusTrue(patientCnic));
				if (patient.isPresent()) {
					// this will set the created date and status of patient Report
					patientReport = patientReportService.addPatientReport(patientReport);
					patient.get().getPatientReports().add(patientReport);

					if (patientReport.getTestResults().toLowerCase().equals("positive")) {
						patient.get().setCovid(true);
					} else if (patientReport.getTestResults().toLowerCase().equals("negative")) {
						patient.get().setCovid(false);
					}
					patient = Optional.of(patientService.updatePatient(patient.get()).getBody());
					List<Patient> patientsOfHospital = hospital.get().getPatients();

					Integer index = patientInHospitalExists(patientsOfHospital, patientCnic);
					if (index >= 0) {
						LOG.info("Patient Exists in Hospital already... ");
						hospital.get().getPatients().set(index, patient.get());
						return updateHospital(hospital.get());

					} else {
						LOG.info("Patient does not Exists in Hospital. Adding... ");
						hospital.get().getPatients().add(patient.get());
						return updateHospital(hospital.get());
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
	public ResponseEntity<Hospital> addPatientVaccination(PatientVaccination patientVaccination, String patientCnic,
			long hospitalId) {
		try {
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			if (hospital.isPresent()) {
				Optional<Patient> patient = Optional.ofNullable(patientRepository.findByCnicAndStatusTrue(patientCnic));
				if (patient.isPresent()) {

					// this will set the created date and status of patient Report
					patientVaccination = patientVaccinationService.addPatientVaccination(patientVaccination);
					patient.get().getPatientVaccination().add(patientVaccination);
					patient.get().setVaccinated(true);
					patient = Optional.of(patientService.updatePatient(patient.get()).getBody());
					List<Patient> patientsOfHospital = hospital.get().getPatients();

					Integer index = patientInHospitalExists(patientsOfHospital, patientCnic);
					if (index >= 0) {
						LOG.info("Patient Exists in Hospital already... ");
						hospital.get().getPatients().set(index, patient.get());
						return updateHospital(hospital.get());

					} else {
						LOG.info("Patient Exists in Hospital already... ");
						hospital.get().getPatients().add(patient.get());
						return updateHospital(hospital.get());
					}

				} else {
					LOG.info("The patient of this id does not exist. Please enter a valid ID.");
					return new ResponseEntity("The patient of this id does not exist. Please enter a valid ID.",
							HttpStatus.NOT_FOUND);
				}
			} else {
				LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
				return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Error while error adding the patient report to the database. \n" + e.getMessage());
			return new ResponseEntity(
					"Error while error adding the patient report to the database. \n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Checks if the patient id already exist in the hospital or not
	 * 
	 * @param patients
	 * @param checkPatientId
	 * @return
	 */
	private Integer patientInHospitalExists(List<Patient> patients, String checkPatientCnic) {
		int index = 0;
		for (Patient patient : patients) {
			if (patient.getCnic().equals(checkPatientCnic))
				return index;

			index++;
		}
		return -1;
	}

	// -----------------------------Mobile Vaccine Cars
	
	/**
	 * @creationDate 31st October 2021
	 * @description This function gets the list of mobileVaccineCar againsta  aprticular hospital.
	 * @param long: a hospital id
	 * @throws Exception the exception
	 * @return Response Entity of List of type MobileVaccineCar
	 **/
	public ResponseEntity<List<MobileVaccineCar>> getMobileVaccineCars(long hospitalId) {
		try {
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			if (hospital.isPresent()) {
				return ResponseEntity.ok().body(hospital.get().getMobileVaccineCars());
			} else {
				LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
				return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Error while error adding the mobile vaccine car to the database. \n" + e.getMessage());
			return new ResponseEntity(
					"Error while error adding the  mobile vaccine car to the database. \n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * @creationDate 31st October 2021
	 * @description This function updates a Mobile Vaccine Car in database.
	 * @param MobileVaccineCar: A mobileVaccineCar object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type MobileVaccineCar
	 **/
	public ResponseEntity<List<MobileVaccineCar>> updateMobileVaccineCar(MobileVaccineCar mobileVaccineCar, long hospitalId) {
		try {
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			if (hospital.isPresent()) {
				Optional<MobileVaccineCar> existingCar = mobileVaccineCarRepository.findById(mobileVaccineCar.getId());
				if(existingCar.isPresent()) {
					mobileVaccineCar.setUpdatedDate(DateTimeUtil.getDate());
					mobileVaccineCarRepository.save(mobileVaccineCar);
					hospital.get().getMobileVaccineCars().add(mobileVaccineCar);
					hospital = Optional.of(updateHospital(hospital.get()).getBody());
					return ResponseEntity.ok().body(hospital.get().getMobileVaccineCars());
				}else {
					LOG.info("The Mobile vaccine car of this id does not exist. Please enter a valid ID.");
					return new ResponseEntity("The Mobile vaccine car of this id does not exist. Please enter a valid ID.",
							HttpStatus.NOT_FOUND);
				}
			} else {
				LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
				return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Error while error adding the mobile vaccine car to the database. \n" + e.getMessage());
			return new ResponseEntity(
					"Error while error adding the  mobile vaccine car to the database. \n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function adds a mobileVaccineCar in database.
	 * @param MobileVaccineCar: A patientVaccination object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type MobileVaccineCar
	 **/
	public ResponseEntity<List<MobileVaccineCar>> addMobileVaccineCar(MobileVaccineCar mobileVaccineCar, long hospitalId) {
		try {
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			if (hospital.isPresent()) {
				mobileVaccineCar.setCreatedDate(DateTimeUtil.getDate());
				mobileVaccineCar.setStatus(true);
				hospital.get().getMobileVaccineCars().add(mobileVaccineCar);
				hospital = Optional.of(updateHospital(hospital.get()).getBody());
				return ResponseEntity.ok().body(hospital.get().getMobileVaccineCars());
			} else {
				LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
				return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Error while error adding the mobile vaccine car to the database. \n" + e.getMessage());
			return new ResponseEntity(
					"Error while error adding the  mobile vaccine car to the database. \n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}


}

