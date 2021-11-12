package com.example.EGovt_CovidHealthApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;
import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Service.AdminService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Haroon Rasheed
 * @version 1.1
 */
@EnableSwagger2
@RestController
@Validated
@RequestMapping("/admin")
public class AdminController {
	private static final Logger LOG = LogManager.getLogger(AdminController.class);
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function retrieves all the companies which are saved in
	 *              database.
	 * @param Optional String: the authorization token
	 * @throws Exception the exception
	 * @return list of companies
	 **/
	@GetMapping("/company/getAllcompanies")
	public ResponseEntity<List<Company>> getAllCompanies(@RequestHeader("Authorization") Optional<String> authToken)

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
		return adminService.getAllCompanies();
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a company in database.
	 * @param Optional String: the authorization token
	 * @param Company: A company object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Company
	 **/
	@PostMapping("/company/addCompany")
	public ResponseEntity<Company> addCompany(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody Company company) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.addCompany(company);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a company in database.
	 * @param Optional String: the authorization token
	 * @param Company: A company object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Company
	 **/
	@PutMapping("/company/updateCompany")
	public ResponseEntity<Company> updateCompany(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody Company company) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.updateCompany(company);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a company in database.
	 * @param Optional String: the authorization token
	 * @param Path     Variable : The id of
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	@DeleteMapping("/company/deleteCompany")
	public ResponseEntity<String> deleteCompany(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody List<Company> companies) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.deleteCompany(companies);
	}

	// -----------------------------------------Admin Controller for Hospital
	// Operations

	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a hospital in database.
	 * @param Optional            String: the authorization token
	 * @param Hospitapatientpital object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	@PostMapping("/hospital/addHospital")
	public ResponseEntity<Hospital> addHospital(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody Hospital hospital) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.addHospital(hospital);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a hospital in database.
	 * @param Optional  String: the authorization token
	 * @param Hospital: A hospital object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Hospital
	 **/
	@PutMapping("/hospital/updateHospital")
	public ResponseEntity<Hospital> updateHospital(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody Hospital hospital) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}

		return adminService.updateHospital(hospital);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a hospital in database.
	 * @param Optional String: the authorization token
	 * @param Path     Variable : The id of
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	@DeleteMapping("/hospital/deleteHospital")
	public ResponseEntity<String> deleteHospital(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody List<Hospital> hopitals) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.deleteHospital(hopitals);
	}

//-----------------------------------------Admin Controller for Lab Operations

	/**
	 * @creationDate 28 October 2021
	 * @description This function adds a lab in database.
	 * @param Optional String: the authorization token
	 * @param Lab:     A lab object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	@PostMapping("/lab/addLab")
	public ResponseEntity<Lab> addLab(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody Lab lab) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}

		return adminService.addLab(lab);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a lab in database.
	 * @param Optional String: the authorization token
	 * @param Lab:     A lab object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Lab
	 **/
	@PutMapping("/lab/updateLab")
	public ResponseEntity<Lab> updateLab(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody Lab lab) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}

		return adminService.updateLab(lab);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a lab in database.
	 * @param Optional String: the authorization token
	 * @param Path     Variable : The id of
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	@DeleteMapping("/lab/deleteLab")
	public ResponseEntity<String> deleteLab(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody List<Lab> labs) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.deleteLab(labs);
	}

	// ----------------Admin Patient Operations-----//
	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a patient in database.
	 * @param Optional String: the authorization token
	 * @param Patient: A patient object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Patient
	 **/
	@PutMapping("/patient/updatePatient")
	public ResponseEntity<Patient> updatePatient(@RequestHeader("Authorization") Optional<String> authToken,
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

		return adminService.updatePatient(patient);
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function deletes a patient in database.
	 * @param Optional String: the authorization token
	 * @param Path     Variable : The id of
	 * @throws Exception the exception
	 * @return Response Entity of type String
	 **/
	@DeleteMapping("/patient/deletePatient")
	public ResponseEntity<String> deletePatient(@RequestHeader("Authorization") Optional<String> authToken,
			@Valid @RequestBody List<Patient> patients) throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.deletePatient(patients);
	}



}
