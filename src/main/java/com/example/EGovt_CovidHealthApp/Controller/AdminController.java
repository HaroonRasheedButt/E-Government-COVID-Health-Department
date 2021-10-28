package com.example.EGovt_CovidHealthApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.EGovt_CovidHealthApp.Authorization;
import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Service.AdminService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Haroon Rasheed
 */
@EnableSwagger2
@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger LOG = LogManager.getLogger(AdminController.class);
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

    /**
     * @creationDate 28 October 2021
     * @description This function retrieves all the companies which are saved in database.
     * @param Optional String:  the authorization token
     * @throws Exception the exception
     * @return list of companies
     **/
	@GetMapping("/company/getAllcompanies")
	public ResponseEntity<List<Company>> getAllCompanies(@RequestHeader("Authorization") Optional<String> authToken)

			throws Exception {
		try {
			Authorization.authorized(authToken);
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
     * @param Optional String:  the authorization token
     * @param Company: A company object to be added
     * @throws Exception the exception
     * @return Response Entity of type Company
     **/
	@PostMapping("/company/addCompany")
	public ResponseEntity<Company> addCompany(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestBody Company company) throws Exception {
		try {
			Authorization.authorized(authToken);
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
     * @param Optional String:  the authorization token
     * @param Company: A company object to be added
     * @throws Exception the exception
     * @return Response Entity of type Company
     **/
	@PutMapping("/company/updateCompany")
	public ResponseEntity<Company> updateCompany(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestBody Company company) throws Exception {
		try {
			Authorization.authorized(authToken);
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
     * @param Optional String:  the authorization token
     * @param Path Variable : The id of
     * @throws Exception the exception
     * @return Response Entity of type String
     **/
	@DeleteMapping("/company/deleteCompany")
	public ResponseEntity<String> deleteCompany(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestBody List<Company> companies) throws Exception {
		try {
			Authorization.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return adminService.deleteCompany(companies);
	}

}
