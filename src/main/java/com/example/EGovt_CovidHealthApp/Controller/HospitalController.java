package com.example.EGovt_CovidHealthApp.Controller;

import com.example.EGovt_CovidHealthApp.Model.Entity.*;
import com.example.EGovt_CovidHealthApp.Model.Interface.DetailedCustomResponse;
import com.example.EGovt_CovidHealthApp.Service.HospitalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
     * @param hospital object to be added
     * @return Response Entity of type Hospital
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function adds a hospital in database.
     **/
    @PostMapping("/addHospital")
    public ResponseEntity<DetailedCustomResponse> addHospital(HttpServletRequest req,
                                                @Valid @RequestBody Hospital hospital) {


        return hospitalService.addHospital(req, hospital);
    }

    /**
     * @param hospital: A hospital object to be added
     * @return Response Entity of type Hospital
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a hospital in database.
     **/
    @PutMapping("/updateHospital")
    public ResponseEntity<DetailedCustomResponse> updateHospital(HttpServletRequest req,
                                                                 @Valid @RequestBody Hospital hospital) {


        return hospitalService.updateHospital(req, hospital);
    }

    /**
     * @param hospitals: The id of
     * @return Response Entity of type String
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a hospital in database.
     **/
    @DeleteMapping("/deleteHospital")
    public ResponseEntity<DetailedCustomResponse> deleteHospital(HttpServletRequest req,
                                                 @Valid @RequestBody List<Hospital> hospitals) {
        return hospitalService.deleteHospital(req, hospitals);
    }


    /**
     * @param authToken: the authorization token
     * @return list of hospitals
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function retrieves all the hospitals which are saved in
     * database.
     **/
    @GetMapping("/getAllHospitals")
    public ResponseEntity<DetailedCustomResponse> getAllHospitals(HttpServletRequest req, @RequestHeader("Authorization") String authToken) {


        return hospitalService.getAllHospitals(req);
    }

    /**
     * This function adds a PatientReport in database.
     *
     * @param patientReport: A PatientReport object to be added
     * @return Response Entity of type Hospital
     * @ the exception
     * @creationDate 28 October 2021
     **/
    @PostMapping("/{hospitalId}/addPatientReport/{userCnic}")
    public ResponseEntity<DetailedCustomResponse> addPatientReport(HttpServletRequest req,
                                                     @PathVariable(value = "hospitalId") long hospitalId,
                                                     @PathVariable(value = "userCnic") String userCnic, @Valid @RequestBody PatientReport patientReport) {


        return hospitalService.addPatientReport(req, patientReport, userCnic, hospitalId);
    }

    /**
     * @param patientReport: User Report object
     * @param userCnic:      User cnic
     * @return Response Entity of type PatientReport
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/user/update/patientReport")
    public ResponseEntity<DetailedCustomResponse> updatePatientReport(HttpServletRequest req,

                                                    @RequestHeader("userCnic") String userCnic,
                                                    @Valid @RequestBody PatientReport patientReport) {

        return hospitalService.updatePatientReport(req, userCnic, patientReport);
    }


    /**
     * @param patientVaccination: A PatientVaccination object to be added
     * @return Response Entity of type Hospital
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function adds a PatientVaccination in database.
     **/
    @PostMapping("/{hospitalId}/addPatientVaccination/{userCnic}")
    public ResponseEntity<DetailedCustomResponse> addPatientVaccination(HttpServletRequest req,
                                                          @PathVariable(value = "hospitalId") long hospitalId,
                                                          @PathVariable(value = "userCnic") String userCnic, @Valid @RequestBody PatientVaccination patientVaccination) {

        return hospitalService.addPatientVaccination(req, patientVaccination, userCnic, hospitalId);
    }

    /**
     * @param patientVaccination: User Vaccination Report
     * @param userCnic:           User cnic
     * @return Response Entity of type User
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/user/update/patientVaccination")
    public ResponseEntity<DetailedCustomResponse> updatePatientVaccination(HttpServletRequest req,

                                                         @RequestHeader("userCnic") String userCnic,
                                                         @Valid @RequestBody PatientVaccination patientVaccination) {

        return hospitalService.updatePatientVaccination(req, userCnic, patientVaccination);
    }


    /**
     * @param hospitalId: A MobileVaccineCar object to be added
     * @return Response Entity of type List of MobileVaccineCars
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function gets all MobileVaccineCar in database.
     **/
    @GetMapping("/{hospitalId}/getMobileVaccineCars")
    public ResponseEntity<DetailedCustomResponse> getMobileVaccineCars(HttpServletRequest req,

                                                                       @PathVariable(value = "hospitalId") long hospitalId) {


        return hospitalService.getMobileVaccineCars(req, hospitalId);
    }

    /**
     * @param mobileVaccineCar: A MobileVaccineCar object to be added
     * @return Response Entity of type Hospital
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function adds a MobileVaccineCar in database.
     **/
    @PostMapping("/{hospitalId}/addMobileVaccineCar")
    public ResponseEntity<DetailedCustomResponse> addMobileVaccineCar(HttpServletRequest req,

                                                                      @PathVariable(value = "hospitalId") long hospitalId, @Valid @RequestBody MobileVaccineCar mobileVaccineCar) {

        return hospitalService.addMobileVaccineCar(req, mobileVaccineCar, hospitalId);
    }

    /**
     * @param mobileVaccineCar: A MobileVaccineCar object to be added
     * @return Response Entity of type Hospital
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a MobileVaccineCar in database.
     **/
    @PostMapping("/{hospitalId}/updateMobileVaccineCar")
    public ResponseEntity<DetailedCustomResponse> updateMobileVaccineCar(HttpServletRequest req,
                                                                         @PathVariable(value = "hospitalId") long hospitalId, @Valid @RequestBody MobileVaccineCar mobileVaccineCar) {
        return hospitalService.updateMobileVaccineCar(req, mobileVaccineCar, hospitalId);
    }
}
