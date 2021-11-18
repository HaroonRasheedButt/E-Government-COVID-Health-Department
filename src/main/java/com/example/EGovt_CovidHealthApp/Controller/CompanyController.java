package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Service.CompanyService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private static final Logger LOG = LogManager.getLogger(CompanyController.class);

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
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
        return companyService.getAllCompanies();
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
        return companyService.addCompany(company);
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
        return companyService.updateCompany(company);
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
        return companyService.deleteCompany(companies);
    }

    /**
     * @param authToken: the authorization token
     * @return An object of company
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function retrieves a company which is saved in
     * database.
     **/
    @GetMapping("/status")
    public ResponseEntity<Company> findByCompnayName(@RequestHeader("Authorization") String authToken,
                                                     @RequestHeader("companyName") String name)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return companyService.findCompanyByName(name);
    }
}
