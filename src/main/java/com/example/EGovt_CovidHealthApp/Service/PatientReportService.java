package com.example.EGovt_CovidHealthApp.Service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientReportRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

@Service
public class PatientReportService {
	private final PatientReportRepository patientReportRepository;
    private static final Logger LOG = LogManager.getLogger(AdminService.class);

	public PatientReportService(PatientReportRepository patientReportRepository) {
			this.patientReportRepository = patientReportRepository;
		}

    /**
     * @creationDate 31st October 2021
     * @description This function gets all the patientReports details in database.
     * @param N/A
     * @throws Exception the exception
     * @return Response Entity of type PatientReport
     **/
	public ResponseEntity<List<PatientReport>> getAllPatientReports() {
		try {
			Optional<List<PatientReport>> patientReports = Optional.of(patientReportRepository.findAllByStatusTrue());
			if (patientReports.isPresent()) {
				LOG.info("PatientReports successfully Retrieved : " + patientReports.get());
				return ResponseEntity.ok().body(patientReports.get());
			} else {
				LOG.info("PatientReports Not found in the database: " + patientReports.get());
				return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception caught while retrieving patientReports data : \n" + e.getMessage());
			return new ResponseEntity("Error retrieving all patientReports!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

    /**
     * @creationDate 31st October 2021
     * @description This function adds a patientReport in database.
     * @param PatientReport: A patientReport object to be added
     * @throws Exception the exception
     * @return Response Entity of type PatientReport
     **/
	public ResponseEntity<PatientReport> addPatientReport(PatientReport patientReport, long patientId, long hospitalId) {
		try {
			patientReport.setCreatedDate(DateTimeUtil.getDate());
			patientReport.setStatus(true);
//			patientReport.setHospital(hospital);
			patientReportRepository.save(patientReport);
			LOG.info("PatientReports successfully added to the database: " + patientReport);
			return ResponseEntity.ok().body(patientReport);
		}catch (PropertyValueException e) {
			LOG.info("The syntax of the patientReport object is invalid : " + patientReport + e.getMessage());
			return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patientReport object to database  : " + patientReport + e.getMessage());
			return new ResponseEntity("Error adding a patientReport into database!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

    /**
     * @creationDate 31st October 2021
     * @description This function updates a patientReport in database.
     * @param PatientReport: A patientReport object to be added
     * @throws Exception the exception
     * @return Response Entity of type PatientReport
     **/
	public ResponseEntity<PatientReport> updatePatientReport(PatientReport patientReport) {
		try {
			Optional<PatientReport> exists = patientReportRepository.findById(patientReport.getId());
			if (exists.isPresent()) {
				patientReport.setUpdatedDate(DateTimeUtil.getDate());
				patientReportRepository.save(patientReport);
				LOG.info("PatientReports successfully updated in the database: " + patientReport);
				return ResponseEntity.ok().body(patientReport);
			} else {
				LOG.info("Copmany could not be updated because the compnay id could not be found  : " );
				return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
						HttpStatus.ACCEPTED);
			}

		}catch (PropertyValueException e) {
			LOG.info("The syntax of the patientReport object is invalid : " + patientReport + e.getMessage());
			return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patientReport object to database  : " + patientReport + e.getMessage());
			return new ResponseEntity("Error updating patientReport!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
     * @creationDate 31st October 2021
     * @description This function deletes a patientReport in database by changing its status to false.
     * @param Path Variable : The id of the the patientReport to be deleted
     * @throws Exception the exception
     * @return Response Entity of type String
     **/
	public ResponseEntity<String> deletePatientReport(List<PatientReport> patientReports){
		try {
			for (PatientReport patientReport : patientReports) {
				patientReport.setStatus(false);
				patientReportRepository.save(patientReport);
			}
			LOG.info("Compnaies deleted successfully bu turning their status to false!");
			return ResponseEntity.ok().body("patientReports successfully deleted");
		}catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while deleting the patientReport object from database  : " + e.getMessage());
			return new ResponseEntity("Error while deleting patientReports!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
