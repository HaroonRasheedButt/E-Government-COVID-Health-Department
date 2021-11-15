package com.example.EGovt_CovidHealthApp.Controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Service.CompanyService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private static final Logger LOG = LogManager.getLogger(CompanyController.class);

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
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
