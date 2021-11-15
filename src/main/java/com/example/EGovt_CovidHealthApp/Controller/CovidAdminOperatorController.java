package com.example.EGovt_CovidHealthApp.Controller;


import com.example.EGovt_CovidHealthApp.Service.CovidAdminOperatorService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@Validated
@RequestMapping("/covidAdminOperator")
public class CovidAdminOperatorController {

    public final CovidAdminOperatorService covidAdminOperatorService;
    private static final Logger LOG = LogManager.getLogger(CovidAdminOperatorController.class);

    public CovidAdminOperatorController(CovidAdminOperatorService covidAdminOperatorService) {
        this.covidAdminOperatorService = covidAdminOperatorService;
    }

    /**
     * @param authToken: the authorization token
     * @return String object telling whether the covid Admin operator has been verified or not
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the patients which are saved in
     * database.
     **/
    @GetMapping("/loginCovidAdminOperator")
    public ResponseEntity<String> loginPatient(@RequestHeader("Authorization") String authToken,
                                               @RequestParam("email") String email,
                                               @RequestParam("password") String password)
            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return covidAdminOperatorService.loginCovidAdminOperator(email, password);
    }
}
