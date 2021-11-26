package com.example.EGovt_CovidHealthApp.Controller;

import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientReport;
import com.example.EGovt_CovidHealthApp.Model.Entity.PatientVaccination;
import com.example.EGovt_CovidHealthApp.Service.LabService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;


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
     * @param lab: A lab object to be added
     * @return Response Entity of type Lab
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function adds a lab in database.
     **/
    @PostMapping("/lab/addLab")
    public ResponseEntity<Lab> addLab(
            @Valid @RequestBody Lab lab) {


        return labService.addLab(lab);
    }

    /**
     * @param lab: A lab object to be added
     * @return Response Entity of type Lab
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a lab in database.
     **/
    @PutMapping("/updateLab")
    public ResponseEntity<Lab> updateLab(
            @Valid @RequestBody Lab lab) {


        return labService.updateLab(lab);
    }

    /**
     * @param labs: The labs to be deleted
     * @return Response Entity of type String
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a lab in database.
     **/
    @DeleteMapping("/deleteLab")
    public ResponseEntity<String> deleteLab(
            @Valid @RequestBody List<Lab> labs) {


        return labService.deleteLab(labs);
    }

    /**
     * @return list of labs
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function retrieves all the labs which are saved in database.
     **/
    @GetMapping("/getAllLabs")
    public ResponseEntity<List<Lab>> getAllLabs() {

        return labService.getAllLabs();
    }

    /**
     * @param patientReport: A PatientReport object to be added
     * @return Response Entity of type Lab
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function adds a PatientReport in database.
     **/
    @PostMapping("/{labId}/addPatientReport/{patientCnic}")
    public ResponseEntity<Lab> addPatientReport(
            @PathVariable(value = "labId") long labId,
            @PathVariable(value = "patientCnic") String patientCnic,
            @Valid @RequestBody PatientReport patientReport) {


        return labService.addPatientReport(patientReport, patientCnic, labId);
    }

    /**
     * @param patientVaccination: A PatientVaccination object to be added
     * @return Response Entity of type Lab
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function adds a PatientVaccination in database.
     **/
    @PostMapping("/{labId}/addPatientVaccination/{patientCnic}")
    public ResponseEntity<Lab> addPatientVaccination(
            @PathVariable(value = "labId") long labId,
            @PathVariable(value = "patientCnic") String patientCnic,
            @Valid @RequestBody PatientVaccination patientVaccination) {


        return labService.addPatientVaccination(patientVaccination, patientCnic, labId);
    }

}
