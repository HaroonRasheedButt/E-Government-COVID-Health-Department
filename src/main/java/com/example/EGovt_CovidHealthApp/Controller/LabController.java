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

import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Service.LabService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@RestController
@Validated
@RequestMapping("/lab")
public class LabController {

    private static final Logger LOG = LogManager.getLogger(LabController.class);
    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }





    /**
     * @param authToken: the authorization token
     * @param lab:       A lab object to be added
     * @return Response Entity of type Lab
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a lab in database.
     **/
    @PostMapping("/lab/addLab")
    public ResponseEntity<Lab> addLab(@RequestHeader("Authorization") String authToken,
                                      @Valid @RequestBody Lab lab) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return labService.addLab(lab);
    }

    /**
     * @param authToken: the authorization token
     * @param lab:       A lab object to be added
     * @return Response Entity of type Lab
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a lab in database.
     **/
    @PutMapping("/updateLab")
    public ResponseEntity<Lab> updateLab(@RequestHeader("Authorization") String authToken,
                                         @Valid @RequestBody Lab lab) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return labService.updateLab(lab);
    }

    /**
     * @param authToken: the authorization token
     * @param labs:      The labs to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a lab in database.
     **/
    @DeleteMapping("/deleteLab")
    public ResponseEntity<String> deleteLab(@RequestHeader("Authorization") String authToken,
                                            @Valid @RequestBody List<Lab> labs) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return labService.deleteLab(labs);
    }

    /**
     * @param authToken: the authorization token
     * @return list of labs
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function retrieves all the labs which are saved in database.
     **/
    @GetMapping("/getAllLabs")
    public ResponseEntity<List<Lab>> getAllLabs(@RequestHeader("Authorization") String authToken)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return labService.getAllLabs();
    }

    /**
     * @param authToken:  the authorization token
     * @param patientReport: A PatientReport object to be added
     * @return Response Entity of type Lab
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a PatientReport in database.
     **/
    @PostMapping("/{labId}/addPatientReport/{patientCnic}")
    public ResponseEntity<Lab> addPatientReport(@RequestHeader("Authorization") String authToken,
                                                @PathVariable(value = "labId") long labId,
                                                @PathVariable(value = "patientCnic") String patientCnic,
                                                @Valid @RequestBody PatientReport patientReport) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return labService.addPatientReport(patientReport, patientCnic, labId);
    }

    /**
     * @param authToken:  the authorization token
     * @param patientVaccination: A PatientVaccination object to be added
     * @return Response Entity of type Lab
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a PatientVaccination in database.
     **/
    @PostMapping("/{labId}/addPatientVaccination/{patientCnic}")
    public ResponseEntity<Lab> addPatientVaccination(@RequestHeader("Authorization") String authToken,
                                                     @PathVariable(value = "labId") long labId,
                                                     @PathVariable(value = "patientCnic") String patientCnic,
                                                     @Valid @RequestBody PatientVaccination patientVaccination) throws Exception {

        AuthorizationUtil.authorized(authToken);
		return labService.addPatientVaccination(patientVaccination, patientCnic, labId);
    }

}
