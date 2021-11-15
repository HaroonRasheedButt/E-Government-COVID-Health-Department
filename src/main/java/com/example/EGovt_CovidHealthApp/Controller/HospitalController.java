package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.MobileVaccineCar;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
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
@Validated
@RequestMapping("/hospital")
public class HospitalController {
    private static final Logger LOG = LogManager.getLogger(HospitalController.class);
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    /**
     * @param authToken: the authorization token
     * @return list of hospitals
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function retrieves all the hospitals which are saved in
     * database.
     **/
    @GetMapping("")
    public ResponseEntity<List<Hospital>> getAllHospitals(@RequestHeader("Authorization") String authToken)
            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.getAllHospitals();
    }

    /**
     * This function adds a PatientReport in database.
     *
     * @param authToken: the authorization token
     * @param patientReport: A PatientReport object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     **/
    @PostMapping("/{hospitalId}/addPatientReport/{patientCnic}")
    public ResponseEntity<Hospital> addPatientReport(@RequestHeader("Authorization") String authToken,
                                                     @PathVariable(value = "hospitalId") long hospitalId,
                                                     @PathVariable(value = "patientCnic") String patientCnic, @Valid @RequestBody PatientReport patientReport)
            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.addPatientReport(patientReport, patientCnic, hospitalId);
    }

    /**
     * @param authToken: the authorization token
     * @param patientReport: Patient Report object
     * @param patientCnic: Patient cnic
     * @return Response Entity of type PatientReport
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/patient/update/patientReport")
    public ResponseEntity<Patient> updatePatientReport(
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("patientCnic") String patientCnic,
            @Valid @RequestBody PatientReport patientReport)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return hospitalService.updatePatientReport(patientCnic, patientReport);
    }


    /**
     * @param authToken: the authorization token
     * @param patientVaccination: A PatientVaccination object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a PatientVaccination in database.
     **/
    @PostMapping("/{hospitalId}/addPatientVaccination/{patientCnic}")
    public ResponseEntity<Hospital> addPatientVaccination(@RequestHeader("Authorization") String authToken,
                                                          @PathVariable(value = "hospitalId") long hospitalId,
                                                          @PathVariable(value = "patientCnic") String patientCnic, @Valid @RequestBody PatientVaccination patientVaccination)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return hospitalService.addPatientVaccination(patientVaccination, patientCnic, hospitalId);
    }

    /**
     * @param authToken: the authorization token
     * @param patientVaccination: Patient Vaccination Report
     * @param patientCnic: Patient cnic
     * @return Response Entity of type Patient
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/patient/update/patientVaccination")
    public ResponseEntity<Patient> updatePatientVaccination(
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("patientCnic") String patientCnic,
            @Valid @RequestBody PatientVaccination patientVaccination)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return hospitalService.updatePatientVaccination(patientCnic, patientVaccination);
    }


    /**
     * @param authToken: the authorization token
     * @param hospitalId: A MobileVaccineCar object to be added
     * @return Response Entity of type List of MobileVaccineCars
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function gets all MobileVaccineCar in database.
     **/
    @GetMapping("/{hospitalId}/getMobileVaccineCars")
    public ResponseEntity<List<MobileVaccineCar>> getMobileVaccineCars(
            @RequestHeader("Authorization") String authToken,
            @PathVariable(value = "hospitalId") long hospitalId) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.getMobileVaccineCars(hospitalId);
    }

    /**
     * @param authToken: the authorization token
     * @param mobileVaccineCar: A MobileVaccineCar object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a MobileVaccineCar in database.
     **/
    @PostMapping("/{hospitalId}/addMobileVaccineCar")
    public ResponseEntity<List<MobileVaccineCar>> addMobileVaccineCar(
            @RequestHeader("Authorization") String authToken,
            @PathVariable(value = "hospitalId") long hospitalId, @Valid @RequestBody MobileVaccineCar mobileVaccineCar)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return hospitalService.addMobileVaccineCar(mobileVaccineCar, hospitalId);
    }

    /**
     * @param authToken:        the authorization token
     * @param mobileVaccineCar: A MobileVaccineCar object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/{hospitalId}/updateMobileVaccineCar")
    public ResponseEntity<List<MobileVaccineCar>> updateMobileVaccineCar(
            @RequestHeader("Authorization") String authToken,
            @PathVariable(value = "hospitalId") long hospitalId, @Valid @RequestBody MobileVaccineCar mobileVaccineCar)
            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.updateMobileVaccineCar(mobileVaccineCar, hospitalId);
    }
}
