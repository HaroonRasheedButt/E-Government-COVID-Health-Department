package com.example.EGovt_CovidHealthApp.Controller;

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

import com.example.EGovt_CovidHealthApp.Service.LoginService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	private final LoginService loginService;
	private static final Logger LOG = LogManager.getLogger(LoginController.class);

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * @creationDate 6 November 2021
	 * @description This function validates and retrieves patient/hospital details
	 *              after comparing email and password.
	 * @param Optional String: the authorization token
	 * @throws Exception the exception
	 * @return Object: An object of either patient or hospital
	 **/
	@GetMapping("")
	public ResponseEntity<Object> login(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("email") String email, @RequestHeader("password") String password,
			@RequestHeader("userType") String userType)

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
		return loginService.login(email, password, userType);
	}
	
	
	
	
	
	

}
