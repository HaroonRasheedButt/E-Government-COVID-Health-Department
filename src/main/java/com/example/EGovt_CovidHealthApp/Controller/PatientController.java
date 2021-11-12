package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Service.PatientService;

/**
 * @author Haroon Rasheed
 * @version 1.1
 */
@EnableSwagger2
@RestController
@Validated
@RequestMapping("/patient")
public class PatientController {

	private static final Logger LOG = LogManager.getLogger(PatientController.class);
	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function retrieves all the patients which are saved in
	 *              database.
	 * @param Optional String: the authorization token
	 * @throws Exception the exception
	 * @return list of patients
	 **/
	@GetMapping("/getAllPatients")
	public ResponseEntity<List<Patient>> getAllPatients(@RequestHeader("Authorization") Optional<String> authToken)

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
		return patientService.getAllPatients();
	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function retrieves all the patients which are saved in
	 *              database.
	 * @param Optional String: the authorization token
	 * @throws Exception the exception
	 * @return list of patients
	 **/
	@GetMapping("/getPatientById")
	public ResponseEntity<Patient> getPatientById(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("patientId") long patientId)

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
		return patientService.getPatientById(patientId);
	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function retrieves all the patients which are saved in
	 *              database.
	 * @param Optional String: the authorization token
	 * @throws Exception the exception
	 * @return list of patients
	 **/
	@GetMapping("/getPatientByCnic")
	public ResponseEntity<Patient> getPatientByCnic(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("patientCnic") String patientCnic)

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
		return patientService.getPatientByCnic(patientCnic);
	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function adds a patient in database.
	 * @param Optional String: the authorization token
	 * @param Patient: A patient object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Patient
	 **/
	@PostMapping("/addPatient")
	public ResponseEntity<Patient> addPatient(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody Patient patient) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return patientService.addPatient(patient);
	}

	/**
	 * Verify patient response entity.
	 *
	 * @param authToken  the auth token
	 * @param patientId  the patient id
	 * @param smsToken   the SMS token
	 * @param emailToken the email token
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@GetMapping("/verifyPatient")
	public ResponseEntity<String> verifyPatient(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader long patientId, @RequestHeader String smsToken, @RequestHeader String emailToken)
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
		return patientService.verifyPatient(patientId, smsToken, emailToken);
	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function checks if a patient has COVID based on their CNIC
	 *              from database.
	 * @param Optional String: the authorization token
	 * @param String:  the patient's CNIC
	 * @throws Exception the exception
	 * @return boolean: patient status
	 **/
	@GetMapping("/covid/status")
	public ResponseEntity<Boolean> checkPatientCovidStatus(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("patientCnic") String patientCnic)

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
		return patientService.checkPatientCovidStatus(patientCnic);
	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function checks if a patient has COVID based on their CNIC
	 *              from database.
	 * @param Optional String: the authorization token
	 * @param String:  the patient's CNIC
	 * @throws Exception the exception
	 * @return boolean: patient status
	 **/
	@GetMapping("/vaccine/status")
	public ResponseEntity<Boolean> checkPatientVaccinationStatus(
			@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("patientCnic") String patientCnic)

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
		return patientService.checkPatientVaccinationStatus(patientCnic);
	}

}