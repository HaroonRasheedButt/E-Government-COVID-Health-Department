package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Service.CompanyService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

@RestController
@RequestMapping("/company")
public class CompanyController {
	private final CompanyService companyService;
	private static final Logger LOG = LogManager.getLogger(CompanyController.class);
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function retrieves a company which is saved in
	 *              database.
	 * @param Optional String: the authorization token
	 * @throws Exception the exception
	 * @return An object of company
	 **/
	@GetMapping("/status")
	public ResponseEntity<Company> findByCompnayName(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("companyName") String name)

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
		return companyService.findCompanyByName(name);
	}
}
