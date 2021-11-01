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

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Service.HospitalService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Haroon Rasheed
 * @version 1.1
 */
@EnableSwagger2
@RestController
@RequestMapping("/hospital")
public class HospitalController {
	private static final Logger LOG = LogManager.getLogger(HospitalController.class);
	private final HospitalService hospitalService;

	public HospitalController(HospitalService hospitalService) {
		this.hospitalService= hospitalService;
	}

    /**
     * @creationDate 28 October 2021
     * @description This function retrieves all the hospitals which are saved in database.
     * @param Optional String:  the authorization token
     * @throws Exception the exception
     * @return list of hospitals
     **/
	@GetMapping("")
	public ResponseEntity<List<Hospital>> getAllHospitals(@RequestHeader("Authorization") Optional<String> authToken)

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
		return hospitalService.getAllHospitals();
	}

    /**
     * @creationDate 28 October 2021
     * @description This function adds a PatientReport in database.
     * @param Optional String:  the authorization token
     * @param PatientReport: A PatientReport object to be added
     * @throws Exception the exception
     * @return Response Entity of type PatientReport
     **/
	@PostMapping("/{hospitalId}/addPatientReport/{patientId}")
	public ResponseEntity<Hospital> addPatientReport(@RequestHeader("Authorization") Optional<String> authToken,
			@PathVariable(value = "hospitalId") long hospitalId,
			@PathVariable(value = "patientId") long patientId,
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

		return hospitalService.addPatientReport(patientReport, patientId, hospitalId);
	}
	
//    /**
//     * @creationDate 28 October 2021
//     * @description This function adds a PatientVaccination in database.
//     * @param Optional String:  the authorization token
//     * @param PatientVaccination: A PatientVaccination object to be added
//     * @throws Exception the exception
//     * @return Response Entity of type PatientVaccination
//     **/
//	@PostMapping("/{hospitalId}/addPatientVaccination/{patientId}")
//	public ResponseEntity<Hospital> addPatientVaccination(@RequestHeader("Authorization") Optional<String> authToken,
//			@PathVariable(value = "hospitalId") long hospitalId,
//			@PathVariable(value = "patientId") long patientId,
//			@RequestBody PatientVaccination patientVaccination) throws Exception {
//		try {
//			AuthorizationUtil.authorized(authToken);
//		} catch (HttpClientErrorException e) {
//			LOG.info("Unable to Authorize : " + e.getMessage());
//			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
//				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
//			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
//				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
//		}
//
//		return hospitalService.addPatientVaccination(patientVaccination, patientId);
//	}

}
