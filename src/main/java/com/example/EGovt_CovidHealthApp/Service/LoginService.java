package com.example.EGovt_CovidHealthApp.Service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Repostiory.HospitalRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;

@Service
public class LoginService {

	private final PatientRepository patientRepository;
	private final HospitalRepository hospitalRepository;

	private static final Logger LOG = LogManager.getLogger(LoginService.class);

	public LoginService(PatientRepository patientRepository, HospitalRepository hospitalRepository) {
		this.patientRepository = patientRepository;
		this.hospitalRepository = hospitalRepository;

	}

	/**
	 * @creationDate 6 November 2021
	 * @description This function validates and retrieves patient/hospital details
	 *              after comparing email and password.
	 * @param Optional String: the authorization token
	 * @throws Exception the exception
	 * @return Object: An object of either patient or hospital
	 **/
	public ResponseEntity<Object> login(String email, String password, String userType) {
		try {
			if (userType.toLowerCase().equals("patient")) {
				Optional<Patient> patient = Optional
						.ofNullable(patientRepository.findByEmailAndPassword(email, password));
				if (patient.isPresent()) {
					if (patient.get().isStatus()) {
						LOG.info("Patient has been logged in...");
						return ResponseEntity.ok().body(patient.get());
					} else {
						LOG.info("Patient has not been verified yet...");
						return ResponseEntity.ok().body("Please Verify yourself first...");
					}
				} else {
					LOG.info("Patient credentials are wrong...");
					return ResponseEntity.ok().body("wrong email or password for user type Patient..");
				}

			} else if (userType.toLowerCase().equals("hospital")) {
				Optional<Hospital> hospital = Optional
						.ofNullable(hospitalRepository.findByEmailAndPassword(email, password));
				if (hospital.isPresent()) {
					LOG.info("Hospital has been logged in...");
					return ResponseEntity.ok().body(hospital.get());
				} else {
					LOG.info("Hospital Credentials are wrong...");
					return ResponseEntity.ok().body("wrong email or password for user type Hospital..");
				}
			} else {
				LOG.info("Provided User type does not exist...");
				return new ResponseEntity("Please enter a valid User Type.", HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			LOG.info("Error while validating the login credentials.\n" + e.getMessage());
			return new ResponseEntity("Error while validating the login credentials.\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
}
