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
	public PatientReport addPatientReport(PatientReport patientReport) throws Exception {
		try {
			patientReport.setCreatedDate(DateTimeUtil.getDate());
			patientReport.setStatus(true);
//			patientReportRepository.save(patientReport);
			LOG.info("PatientReports successfully added to the database: " + patientReport);
			return (patientReport);
		}catch (PropertyValueException e) {
			LOG.info("The syntax of the patientReport object is invalid : " + patientReport + e.getMessage());
			throw new PropertyValueException("Please send a valid object to add into the databse!\n" + e.getMessage(), PatientReport.class.getClass().getName(), this.toString());
		}
		catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patientReport object to database  : " + patientReport + e.getMessage());
			throw new Exception("Error adding a patientReport into database!\n" + e.getMessage());
		}
	}

    /**
     * @creationDate 31st October 2021
     * @description This function updates a patientReport in database.
     * @param PatientReport: A patientReport object to be added
     * @throws Exception the exception
     * @return Response Entity of type PatientReport
     **/
	public PatientReport updatePatientReport(PatientReport patientReport) throws Exception {
		try {
			Optional<PatientReport> exists = patientReportRepository.findById(patientReport.getId());
			if (exists.isPresent()) {
				patientReport.setUpdatedDate(DateTimeUtil.getDate());
				LOG.info("PatientReports successfully updated in the database: " + patientReport);
				return patientReport;
			} else {
				LOG.info("PatientReport could not be updated because the compnay id could not be found  : " );
				throw new Exception("PatientReport of this id does not exist in database!\n" );
			}

		}catch (PropertyValueException e) {
			LOG.info("The syntax of the patientReport object is invalid : " + patientReport + e.getMessage());
			throw new PropertyValueException("Please send a valid object to add into the databse!\n" + e.getMessage(), PatientReport.class.getClass().getName(), this.toString());
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patientReport object to database  : " + patientReport + e.getMessage());
			throw new Exception("Error updating a patientReport into database!\n" + e.getMessage());
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
			LOG.info("Compnaies deleted successfully by turning their status to false!");
			return ResponseEntity.ok().body("patientReports successfully deleted");
		}catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while deleting the patientReport object from database  : " + e.getMessage());
			return new ResponseEntity("Error while deleting patientReports!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
