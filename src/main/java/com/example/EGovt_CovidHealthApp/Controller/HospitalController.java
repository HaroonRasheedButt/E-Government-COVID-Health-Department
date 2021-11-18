package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;

import javax.validation.Valid;

import com.example.EGovt_CovidHealthApp.Model.Entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.MobileVaccineCar;
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
     * @param hospital   object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a hospital in database.
     **/
    @PostMapping("/hospital/addHospital")
    public ResponseEntity<Hospital> addHospital(@RequestHeader("Authorization") String authToken,
                                                @Valid @RequestBody Hospital hospital) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.addHospital(hospital);
    }

    /**
     * @param authToken: the authorization token
     * @param hospital:  A hospital object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a hospital in database.
     **/
    @PutMapping("/hospital/updateHospital")
    public ResponseEntity<Hospital> updateHospital(@RequestHeader("Authorization") String authToken,
                                                   @Valid @RequestBody Hospital hospital) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.updateHospital(hospital);
    }

    /**
     * @param authToken: the authorization token
     * @param hospitals: The id of
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a hospital in database.
     **/
    @DeleteMapping("/hospital/deleteHospital")
    public ResponseEntity<String> deleteHospital(@RequestHeader("Authorization") String authToken,
                                                 @Valid @RequestBody List<Hospital> hospitals) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.deleteHospital(hospitals);
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
    @PostMapping("/{hospitalId}/addPatientReport/{userCnic}")
    public ResponseEntity<Hospital> addPatientReport(@RequestHeader("Authorization") String authToken,
                                                     @PathVariable(value = "hospitalId") long hospitalId,
                                                     @PathVariable(value = "userCnic") String userCnic, @Valid @RequestBody PatientReport patientReport)
            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return hospitalService.addPatientReport(patientReport, userCnic, hospitalId);
    }

    /**
     * @param authToken: the authorization token
     * @param patientReport: User Report object
     * @param userCnic: User cnic
     * @return Response Entity of type PatientReport
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/user/update/patientReport")
    public ResponseEntity<User> updatePatientReport(
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("userCnic") String userCnic,
            @Valid @RequestBody PatientReport patientReport)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return hospitalService.updatePatientReport(userCnic, patientReport);
    }


    /**
     * @param authToken: the authorization token
     * @param patientVaccination: A PatientVaccination object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a PatientVaccination in database.
     **/
    @PostMapping("/{hospitalId}/addPatientVaccination/{userCnic}")
    public ResponseEntity<Hospital> addPatientVaccination(@RequestHeader("Authorization") String authToken,
                                                          @PathVariable(value = "hospitalId") long hospitalId,
                                                          @PathVariable(value = "userCnic") String userCnic, @Valid @RequestBody PatientVaccination patientVaccination)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return hospitalService.addPatientVaccination(patientVaccination, userCnic, hospitalId);
    }

    /**
     * @param authToken: the authorization token
     * @param patientVaccination: User Vaccination Report
     * @param userCnic: User cnic
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/user/update/patientVaccination")
    public ResponseEntity<User> updatePatientVaccination(
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("userCnic") String userCnic,
            @Valid @RequestBody PatientVaccination patientVaccination)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return hospitalService.updatePatientVaccination(userCnic, patientVaccination);
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
