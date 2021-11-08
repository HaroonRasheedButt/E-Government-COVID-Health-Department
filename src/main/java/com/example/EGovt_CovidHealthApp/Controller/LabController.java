package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Service.LabService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@RestController
@RequestMapping("/lab")
public class LabController {

	private static final Logger LOG = LogManager.getLogger(LabController.class);
	private final LabService labService;

	public LabController(LabService labService) {
		this.labService= labService;
	}

    /**
     * @creationDate 28 October 2021
     * @description This function retrieves all the labs which are saved in database.
     * @param Optional String:  the authorization token
     * @throws Exception the exception
     * @return list of labs
     **/
	@GetMapping("")
	public ResponseEntity<List<Lab>> getAllLabs(@RequestHeader("Authorization") Optional<String> authToken)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return labService.getAllLabs();
	}

    /**
     * @creationDate 28 October 2021
     * @description This function adds a PatientReport in database.
     * @param Optional String:  the authorization token
     * @param PatientReport: A PatientReport object to be added
     * @throws Exception the exception
     * @return Response Entity of type Lab
     **/
	@PostMapping("/{labId}/addPatientReport/{patientCnic}")
	public ResponseEntity<Lab> addPatientReport(@RequestHeader("Authorization") Optional<String> authToken,
			@PathVariable(value = "labId") long labId,
			@PathVariable(value = "patientCnic") String patientCnic,
			@RequestBody PatientReport patientReport) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}

		return labService.addPatientReport(patientReport, patientCnic, labId);
	}
	
    /**
     * @creationDate 28 October 2021
     * @description This function adds a PatientVaccination in database.
     * @param Optional String:  the authorization token
     * @param PatientVaccination: A PatientVaccination object to be added
     * @throws Exception the exception
     * @return Response Entity of type Lab
     **/
	@PostMapping("/{labId}/addPatientVaccination/{patientCnic}")
	public ResponseEntity<Lab> addPatientVaccination(@RequestHeader("Authorization") Optional<String> authToken,
			@PathVariable(value = "labId") long labId,
			@PathVariable(value = "patientCnic") String patientCnic,
			@RequestBody PatientVaccination patientVaccination) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}

		return labService.addPatientVaccination(patientVaccination, patientCnic, labId);
	}

}
