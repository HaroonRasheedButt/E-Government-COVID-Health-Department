//package com.example.EGovt_CovidHealthApp.Controller;
//
//import com.example.EGovt_CovidHealthApp.Service.SuperAdminService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @author Haroon Rasheed
// * @version 1.1
// */
//@EnableSwagger2
//@RestController
//@Validated
//@RequestMapping("/admin")
//public class SuperAdminController {
//    private static final Logger LOG = LogManager.getLogger(SuperAdminController.class);
//    private final SuperAdminService superAdminService;
//
//    public SuperAdminController(SuperAdminService superAdminService) {
//        this.superAdminService = superAdminService;
//    }
//
//
//    /**
//     * Logs in the admin
//     *
//     * @param authToken: the authorization token
//     * @return String object telling whether the patient has been verified or not
//     * @throws Exception the exception
//     * @creationDate 29 October 2021
//     **/
//    @GetMapping("/loginSuperAdmin")
//    public ResponseEntity<String> loginPatient(@RequestParam("email") String email,
//                                               @RequestParam("password") String password)
//            throws Exception {
//
//        return superAdminService.loginAdmin(email, password);
//    }
//
//}
