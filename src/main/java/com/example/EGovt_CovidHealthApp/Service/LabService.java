package com.example.EGovt_CovidHealthApp.Service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Repostiory.LabRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

@Service
public class LabService {

	private final LabRepository labRepository;
	private final PatientRepository patientRepository;
	private final PatientService patientService;
	private final PatientReportService patientReportService;
	private final PatientVaccinationService patientVaccinationService;
	private static final Logger LOG = LogManager.getLogger(SuperAdminService.class);

	public LabService(LabRepository labRepository, PatientReportService patientReportService,
			PatientVaccinationService patientVaccinationService, PatientService patientService,
			PatientRepository patientRepository) {
		this.labRepository = labRepository;
		this.patientRepository = patientRepository;
		this.patientService = patientService;
		this.patientReportService = patientReportService;
		this.patientVaccinationService = patientVaccinationService;
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function gets all the labs details in database.
	 * @param N/A
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	public ResponseEntity<List<Lab>> getAllLabs() {
		try {
			Optional<List<Lab>> labs = Optional.of(labRepository.findAllByStatusTrue());
			if (labs.isPresent()) {
				LOG.info("Labs successfully Retrieved : " + labs.get());
				return ResponseEntity.ok().body(labs.get());
			} else {
				LOG.info("Labs Not found in the database: " + labs.get());
				return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception caught while retrieving labs data : \n" + e.getMessage());
			return new ResponseEntity("Error retrieving all labs!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 1st November 2021
	 * @description This function gets a lab based on an id from database.
	 * @param N/A
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	public ResponseEntity<Lab> getLabById(long labId) {
		try {
			Optional<Lab> lab = labRepository.findById(labId);
			if (lab.isPresent()) {
				LOG.info("Lab found by id.");
				return ResponseEntity.ok().body(lab.get());
			} else {
				LOG.info("Lab not found");
				return new ResponseEntity("Lab Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Exception caught in get all lab. Unable to get all labs.");
			return new ResponseEntity("Unable to get lab by id\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function adds a lab in database.
	 * @param Lab: A lab object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	public ResponseEntity<Lab> addLab(Lab lab) {
		try {
			lab.setCreatedDate(DateTimeUtil.getDate());
			lab.setStatus(true);
			labRepository.save(lab);
			LOG.info("Labs successfully added to the database: " + lab);
			return ResponseEntity.ok().body(lab);
		} catch (PropertyValueException e) {
			LOG.info("The syntax of the lab object is invalid : " + lab + e.getMessage());
			return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the lab object to database  : " + lab + e.getMessage());
			return new ResponseEntity("Error adding a lab into database!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function updates a lab in database.
	 * @param Lab: A lab object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	public ResponseEntity<Lab> updateLab(Lab lab) {
		try {
			Optional<Lab> exists = labRepository.findById(lab.getId());
			if (exists.isPresent()) {
				lab.setUpdatedDate(DateTimeUtil.getDate());
				labRepository.save(lab);
				LOG.info("Labs successfully updated in the database: " + lab);
				return ResponseEntity.ok().body(lab);
			} else {
				LOG.info("Lab could not be updated because the id could not be found  : ");
				return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
						HttpStatus.ACCEPTED);
			}

		} catch (PropertyValueException e) {
			LOG.info("The syntax of the lab object is invalid : " + lab + e.getMessage());
			return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the lab object to database  : " + lab + e.getMessage());
			return new ResponseEntity("Error updating lab!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function deletes a lab in database by changing its
	 *              status to false.
	 * @param Path Variable : The id of the the lab to be deleted
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	public ResponseEntity<String> deleteLab(List<Lab> labs) {
		try {
			for (Lab lab : labs) {
				lab.setStatus(false);
				labRepository.save(lab);
			}
			LOG.info("Labs deleted successfully bu turning their status to false!");
			return ResponseEntity.ok().body("labs successfully deleted");
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while deleting the lab object from database  : " + e.getMessage());
			return new ResponseEntity("Error while deleting labs!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 31st October 2021
	 * @description This function adds a patientReport in database.
	 * @param PatientReport: A patientReport object to be added
	 * @param PatientCnic:   A patient CNIC that is unique for every patient
	 * @param long:          A lab id
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	public ResponseEntity<Lab> addPatientReport(PatientReport patientReport, String patientCnic, long labId) {

		try {
			Optional<Lab> lab = labRepository.findById(labId);
			if (lab.isPresent()) {
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
					List<Patient> patientsOfLab = lab.get().getPatients();

					Integer index = patientInLabExists(patientsOfLab, patientCnic);
					if (index >= 0) {
						lab.get().getPatients().set(index, patient.get());
						return updateLab(lab.get());

					} else {
						lab.get().getPatients().add(patient.get());
						return updateLab(lab.get());
					}

				} else {
					return new ResponseEntity("The patient of this id does not exist. Please enter a valid ID.",
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity("The lab of this id does not exist. Please enter a valid ID.",
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
	public ResponseEntity<Lab> addPatientVaccination(PatientVaccination patientVaccination, String patientCnic,
			long labId) {
		try {
			Optional<Lab> lab = labRepository.findById(labId);
			if (lab.isPresent()) {
				Optional<Patient> patient = Optional.ofNullable(patientRepository.findByCnicAndStatusTrue(patientCnic));
				if (patient.isPresent()) {

					// this will set the created date and status of patient Report
					patientVaccination = patientVaccinationService.addPatientVaccination(patientVaccination);
					patient.get().getPatientVaccination().add(patientVaccination);
					patient.get().setVaccinated(true);
					patient = Optional.of(patientService.updatePatient(patient.get()).getBody());
					List<Patient> patientsOfLab = lab.get().getPatients();

					Integer index = patientInLabExists(patientsOfLab, patientCnic);
					if (index >= 0) {
						lab.get().getPatients().set(index, patient.get());
						return updateLab(lab.get());

					} else {
						lab.get().getPatients().add(patient.get());
						return updateLab(lab.get());
					}

				} else {
					return new ResponseEntity("The patient of this id does not exist. Please enter a valid ID.",
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity("The lab of this id does not exist. Please enter a valid ID.",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity(
					"Error while error adding the patient report to the database. \n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Checks if the patient id already exist in the lab or not
	 * 
	 * @param patients
	 * @param checkPatientId
	 * @return
	 */
	private Integer patientInLabExists(List<Patient> patients, String checkPatientCnic) {
		int index = 0;
		for (Patient patient : patients) {
			if (patient.getCnic().equals(checkPatientCnic))
				return index;

			index++;
		}
		return -1;
	}
}
