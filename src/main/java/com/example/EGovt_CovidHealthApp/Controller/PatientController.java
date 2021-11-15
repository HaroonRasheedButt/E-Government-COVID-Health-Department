package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
     * @param authToken: the authorization token
     * @return String object telling whether the patient has been verified or not
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the patients which are saved in
     * database.
     **/
    @GetMapping("/loginPatient")
    public ResponseEntity<String> loginPatient(@RequestHeader("Authorization") String authToken,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password)
            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return patientService.loginPatient(email, password);
    }


    /**
     * @param authToken: the authorization token
     * @return list of patients
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the patients which are saved in
     * database.
     **/
    @GetMapping("/getAllPatients")
    public ResponseEntity<List<Patient>> getAllPatients(@RequestHeader("Authorization") String authToken)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return patientService.getAllPatients();
    }

    /**
     * @param authToken: the authorization token
     * @return list of patients
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the patients which are saved in
     * database.
     **/
    @GetMapping("/getPatientById")
    public ResponseEntity<Patient> getPatientById(@RequestHeader("Authorization") String authToken,
                                                  @RequestHeader("patientId") long patientId)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return patientService.getPatientById(patientId);
    }

    /**
     * @param authToken: the authorization token
     * @return list of patients
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the patients which are saved in
     * database.
     **/
    @GetMapping("/getPatientByCnic")
    public ResponseEntity<Patient> getPatientByCnic(@RequestHeader("Authorization") String authToken,
                                                    @RequestHeader("patientCnic") String patientCnic)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return patientService.getPatientByCnic(patientCnic);
    }

    /**
     * @param authToken: the authorization token
     * @param patient:   A patient object to be added
     * @return Response Entity of type Patient
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function adds a patient in database.
     **/
    @PostMapping("/addPatient")
    public ResponseEntity<Patient> addPatient(@RequestHeader("Authorization") String authToken,
                                              @Valid @RequestBody Patient patient) throws Exception {
        AuthorizationUtil.authorized(authToken);
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
    public ResponseEntity<String> verifyPatient(@RequestHeader("Authorization") String authToken,
                                                @RequestHeader long patientId, @RequestHeader String smsToken, @RequestHeader String emailToken)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return patientService.verifyPatient(patientId, smsToken, emailToken);
    }

    /**
     * @param authToken:   the authorization token
     * @param patientCnic: the patient's CNIC
     * @return boolean: patient status
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function checks if a patient has COVID based on their CNIC
     * from database.
     **/
    @GetMapping("/covid/status")
    public ResponseEntity<Boolean> checkPatientCovidStatus(@RequestHeader("Authorization") String authToken,
                                                           @RequestHeader("patientCnic") String patientCnic)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return patientService.checkPatientCovidStatus(patientCnic);
    }

    /**
     * @param authToken    String: the authorization token
     * @param patientCnic: the patient's CNIC
     * @return boolean: patient status
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function checks if a patient has COVID based on their CNIC
     * from database.
     **/
    @GetMapping("/vaccine/status")
    public ResponseEntity<Boolean> checkPatientVaccinationStatus(
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("patientCnic") String patientCnic)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return patientService.checkPatientVaccinationStatus(patientCnic);
    }

}