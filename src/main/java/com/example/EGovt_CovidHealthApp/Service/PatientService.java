package com.example.EGovt_CovidHealthApp.Service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;
import com.example.EGovt_CovidHealthApp.Util.MailUtil;
import com.example.EGovt_CovidHealthApp.Util.SmsUtil;
import com.example.EGovt_CovidHealthApp.Util.TokenGenerationUtil;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Model.Entity.Token;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.TokenRepository;

/**
 * @author Haroon Rasheed
 * @version 1.1
 */
@Service
public class PatientService {
	private final JavaMailSender javaMailSender;
	private final PatientRepository patientRepository;
	private final TokenRepository tokenRepository;
	private final PatientReportService patientReportService;

//	private final FeignClientCheck feignClientCheck;
	private static final Logger LOG = LogManager.getLogger(PatientService.class);

	public PatientService(JavaMailSender javaMailSender, PatientRepository patientRepository,
			TokenRepository tokenRepository, PatientReportService patientReportService) {
		this.patientRepository = patientRepository;
//		this.feignClientCheck = feignClientCheck;
		this.javaMailSender = javaMailSender;
		this.tokenRepository = tokenRepository;
		this.patientReportService = patientReportService;
	}

//	public ResponseEntity<List<Tags>> getTags() {
//		try {
//			return feignClientCheck.getTags("40dc498b-e837-4fa9-8e53-c1d51e01af15");
//		} catch (Exception e) {
//
//			return null;
//		}
//	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function gets all the patients details from the database.
	 * @param NA
	 * @throws Exception the exception
	 * @return Response Entity of List of Patients
	 **/
	public ResponseEntity<List<Patient>> getAllPatients() {
		try {
			Optional<List<Patient>> patients = Optional
					.of(patientRepository.findAllByStatusTrueOrderByCreatedDateDesc());
			if (patients.isPresent()) {
				LOG.info("Patients successfully Retrieved : " + patients.get());
				return ResponseEntity.ok().body(patients.get());
			} else {
				LOG.info("Patients Not found in the database: " + patients.get());
				return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception caught while retrieving Patients data : \n" + e.getMessage());
			return new ResponseEntity("Error retrieving all patients!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 1st November 2021
	 * @description This function gets a patient based on an id from database.
	 * @param NA
	 * @throws Exception the exception
	 * @return Response Entity of type Patient
	 **/
	public ResponseEntity<Patient> getPatientById(long patientId) {
		try {
			Optional<Patient> patient = patientRepository.findById(patientId);
			if (patient.isPresent()) {
				LOG.info("Patient found by id.");
				return ResponseEntity.ok().body(patient.get());
			} else {
				LOG.info("Patient not found");
				return new ResponseEntity("Patient Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Exception caught in get all patient. Unable to get all patients.");
			return new ResponseEntity("Unable to get patient by id\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 29 October 2021
	 * @description This function adds a patient in database.
	 * @param Patient: A patient object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Patient
	 **/
	public ResponseEntity<Patient> addPatient(Patient patient) {
		Token token = new Token();
		MailUtil mailUtil = new MailUtil(javaMailSender);

		try {
			patient.setCreatedDate(DateTimeUtil.getDate());
			patient.setStatus(false);
			patient.setAlive(true);

			token.setSmsToken(TokenGenerationUtil.generateToken());
			SmsUtil.sendSms(patient.getContactNum(), token.getSmsToken());

			token.setEmailToken(TokenGenerationUtil.generateToken());
			mailUtil.sendEmail(patient.getEmail(), token.getEmailToken());

			// Saved the patient at this stage so that we can get the automatically
			// incrementing user ID from the database to set into token table
			patient = patientRepository.save(patient);
			LOG.info("Patient successfully saved into the database : " + patient);

			token.setCreatedDate(DateTimeUtil.getTimestamp());
			token.setUserType(this.getClass().getName());
			token.setUserId(patient.getId());

			tokenRepository.save(token);
			LOG.info("Token successfully saved into the database : " + token);

			return ResponseEntity.ok().body(patient);
		} catch (PropertyValueException e) {

			LOG.info("The syntax of the patient object is invalid. Some null properties can not be added to databse "
					+ patient);
			return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			LOG.info("Error.... Duplicate entry for a unique value!! : " + patient + "\n" + e.getMessage());
			return new ResponseEntity("Error adding a patient into database!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error generated while saving the patient into the database : " + patient + "\n" + e.getMessage());
			return new ResponseEntity("Error adding a patient into database!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 28 October 2021
	 * @description This function updates a patient in database.
	 * @param Patient: A patient object to be added
	 * @throws Exception the exception
	 * @return Response Entity of type Patient
	 **/
	public ResponseEntity<Patient> updatePatient(Patient patient) {
		try {
			Optional<Patient> exists = patientRepository.findById(patient.getId());
			if (exists.isPresent()) {
				patient.setUpdatedDate(DateTimeUtil.getDate());
				patientRepository.save(patient);
				LOG.info("Patients successfully updated in the database: " + patient);
				return ResponseEntity.ok().body(patient);
			} else {
				LOG.info("Copmany could not be updated because the compnay id could not be found  : ");
				return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
						HttpStatus.ACCEPTED);
			}

		} catch (PropertyValueException e) {
			LOG.info("The syntax of the patient object is invalid : " + patient + e.getMessage());
			return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error while saving the patient object to database  : " + patient + e.getMessage());
			return new ResponseEntity("Error updating copmany!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @return String which is a confirmation if the patient is verified or not.
	 * @author Haroon Rasheed
	 * @version 1.5
	 * @description This function takes in the SMS token and email token from the
	 *              patient to verify
	 * @creationDate 13 October 2021
	 */
	public ResponseEntity<String> verifyPatient(long patientId, String smsToken, String emailToken) {
		try {
			Optional<Token> token = Optional
					.ofNullable(tokenRepository.findByUserIdAndSmsTokenAndEmailToken(patientId, smsToken, emailToken));
			if (token.isPresent()) {
				LOG.info("SMS and Email token are correct !\n ");
				java.util.Date patientVerifyingDate = DateTimeUtil.getDate();
				token.get().setverifyingDate(patientVerifyingDate);

				long creatingMins = token.get().getCreatedDate().getMinutes();
				long verifyingMins = token.get().getverifyingDate().getMinutes();

				if (verifyingMins - creatingMins > TokenGenerationUtil.allowedDelayMinutes) {
					LOG.info("SMS and Email verifying time has been expired... !\n ");
					// deleting the patient saved in database so that a user with same details
					// doesn't get added again when it registers again.
					patientRepository.deleteById(patientId);
					tokenRepository.deleteById(token.get().getId());
					// 422 Unprocessable Entity response status code indicates that the server
					// understands the content type of the request entity, and the syntax of the
					// request entity is correct, but it was unable to process the contained
					// instructions.
					return ResponseEntity.unprocessableEntity().body(
							"Your verifying OTP code has been expired. Please again add a patient to obtain an OTP.");
					// RESET_CONTENT: Tells the user agent to reset the document which sent this
					// request.
				} else {
					Optional<Patient> patient = patientRepository.findById(patientId);
					if (patient.isPresent()) {
						patient.get().setStatus(true);
						patientRepository.save(patient.get());
						LOG.info("Patient has been verified and saved to database. !");
						return ResponseEntity.ok().body("Patient has been Verified");
					} else {
						LOG.info("The patient of this id does not exist!");
						return new ResponseEntity(
								"Patient of this ID does not eist. Make sure you have first registered yourself.",
								HttpStatus.NOT_FOUND);
					}
				}
			} else {
				return new ResponseEntity("Invalid OTP. The OTP you entered is wrong.", HttpStatus.NOT_FOUND);

			}

		} catch (Exception e) {
			return new ResponseEntity("Unable to verify patient\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 1st November 2021
	 * @description This function gets a patient based on their CNIC from database.
	 * @param NA
	 * @throws Exception the exception
	 * @return Response Entity of type Patient
	 **/
	public ResponseEntity<Patient> getPatientByCnic(String patientCnic) {
		try {
			Optional<Patient> patient = Optional.ofNullable(patientRepository.findByCnicAndStatusTrue(patientCnic));
			if (patient.isPresent()) {
				LOG.info("Patient found by id.");
				return ResponseEntity.ok().body(patient.get());
			} else {
				LOG.info("Patient not found");
				return new ResponseEntity("Patient Not Found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.info("Exception caught in get all patient. Unable to get all patients.");
			return new ResponseEntity("Unable to get patient by id\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 1st November 2021
	 * @description This function checks if a patient has COVID based on their CNIC
	 *              from database.
	 * @param NA
	 * @throws Exception the exception
	 * @return boolean: patient status
	 **/
	public ResponseEntity<Boolean> checkPatientCovidStatus(String patientCnic) throws Exception {
		try {
			Optional<Patient> patient = Optional.ofNullable(patientRepository.findByCnicAndStatusTrue(patientCnic));
			if (patient.isPresent()) {
				LOG.info("Patient found by cnic.");
				if (patient.get().isCovid())
					return ResponseEntity.ok().body(true);
				else
					return ResponseEntity.ok().body(false);
			} else {
				LOG.info("Patient not found");
				return ResponseEntity.ok().body(false);
			}
		} catch (Exception e) {
			LOG.info("Exception caught in finding a patient. Unable to find patient by their CNIC..");
			throw new Exception(
					"Exception caught while checking patient COVID status/ PLease try again later!" + e.getMessage());
		}
	}

	/**
	 * @creationDate 1st November 2021
	 * @description This function checks if a patient has been vaccinated based on
	 *              their CNIC from database.
	 * @param NA
	 * @throws Exception the exception
	 * @return boolean: patient status
	 **/
	public ResponseEntity<Boolean> checkPatientVaccinationStatus(String patientCnic) throws Exception {
		try {
			Optional<Patient> patient = Optional.ofNullable(patientRepository.findByCnicAndStatusTrue(patientCnic));
			if (patient.isPresent()) {
				LOG.info("Patient found by cnic.");
				if (patient.get().isVaccinated())
					return ResponseEntity.ok().body(true);
				else
					return ResponseEntity.ok().body(false);
			} else {
				LOG.info("Patient not found");
				return ResponseEntity.ok().body(false);
			}
		} catch (Exception e) {
			LOG.info("Exception caught in finding a patient. Unable to find patient by their CNIC.");
			throw new Exception("Exception caught while checking patient Vaccination status/ PLease try again later!"
					+ e.getMessage());
		}
	}

}
