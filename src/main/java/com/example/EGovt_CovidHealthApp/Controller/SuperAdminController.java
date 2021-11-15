package com.example.EGovt_CovidHealthApp.Controller;

import com.example.EGovt_CovidHealthApp.Service.SuperAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;
import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Haroon Rasheed
 * @version 1.1
 */
@EnableSwagger2
@RestController
@Validated
@RequestMapping("/admin")
public class SuperAdminController {
    private static final Logger LOG = LogManager.getLogger(SuperAdminController.class);
    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }


    /**
     * Logs in the admin
     *
     * @param authToken: the authorization token
     * @return String object telling whether the patient has been verified or not
     * @throws Exception the exception
     * @creationDate 29 October 2021
     **/
    @GetMapping("/loginSuperAdmin")
    public ResponseEntity<String> loginPatient(@RequestHeader("Authorization") String authToken,
                                               @RequestParam("email") String email,
                                               @RequestParam("password") String password)
            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.loginAdmin(email, password);
    }



    /**
     * This function retrieves all the companies which are saved in database.
     *
     * @param authToken: the authorization token
     * @return list of companies
     * @throws Exception the exception
     * @creationDate 28 October 2021
     **/
    @GetMapping("/company/getAllcompanies")
    public ResponseEntity<List<Company>> getAllCompanies(@RequestHeader("Authorization") String authToken) throws Exception {
        AuthorizationUtil.authorized(authToken);
        return superAdminService.getAllCompanies();
    }

    /**
     * @param authToken: the authorization token
     * @param company:   A company object to be added
     * @return Response Entity of type Company
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a company in database.
     **/
    @PostMapping("/company/addCompany")
    public ResponseEntity<Company> addCompany(@RequestHeader("Authorization") String authToken,
                                              @Valid @RequestBody Company company) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.addCompany(company);
    }

    /**
     * @param authToken: the authorization token
     * @param company:   A company object to be added
     * @return Response Entity of type Company
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a company in database.
     **/
    @PutMapping("/company/updateCompany")
    public ResponseEntity<Company> updateCompany(@RequestHeader("Authorization") String authToken,
                                                 @Valid @RequestBody Company company) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.updateCompany(company);
    }

    /**
     * @param authToken: the authorization token
     * @param companies  : List of all companies
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a company in database.
     **/
    @DeleteMapping("/company/deleteCompany")
    public ResponseEntity<String> deleteCompany(@RequestHeader("Authorization") String authToken,
                                                @Valid @RequestBody List<Company> companies) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.deleteCompany(companies);
    }


    // -----------------------------------------Admin Controller for Hospital
    // Operations

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
        return superAdminService.addHospital(hospital);
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
        return superAdminService.updateHospital(hospital);
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
        return superAdminService.deleteHospital(hospitals);
    }

//-----------------------------------------Admin Controller for Lab Operations

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
        return superAdminService.addLab(lab);
    }

    /**
     * @param authToken: the authorization token
     * @param lab:       A lab object to be added
     * @return Response Entity of type Lab
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a lab in database.
     **/
    @PutMapping("/lab/updateLab")
    public ResponseEntity<Lab> updateLab(@RequestHeader("Authorization") String authToken,
                                         @Valid @RequestBody Lab lab) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.updateLab(lab);
    }

    /**
     * @param authToken: the authorization token
     * @param labs:      The labs to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a lab in database.
     **/
    @DeleteMapping("/lab/deleteLab")
    public ResponseEntity<String> deleteLab(@RequestHeader("Authorization") String authToken,
                                            @Valid @RequestBody List<Lab> labs) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.deleteLab(labs);
    }

    // ----------------Admin Patient Operations-----//

    /**
     * @param authToken: the authorization token
     * @param patient:   A patient object to be added
     * @return Response Entity of type Patient
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a patient in database.
     **/
    @PutMapping("/patient/updatePatient")
    public ResponseEntity<Patient> updatePatient(@RequestHeader("Authorization") String authToken,
                                                 @Valid @RequestBody Patient patient) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.updatePatient(patient);
    }

    /**
     * @param authToken: the authorization token
     * @param patients:  The list of patients to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function deletes a patient in database.
     **/
    @DeleteMapping("/patient/deletePatient")
    public ResponseEntity<String> deletePatient(@RequestHeader("Authorization") String authToken,
                                                @Valid @RequestBody List<Patient> patients) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return superAdminService.deletePatient(patients);
    }


}
