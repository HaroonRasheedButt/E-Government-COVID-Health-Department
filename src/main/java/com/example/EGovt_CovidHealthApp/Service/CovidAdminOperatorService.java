package com.example.EGovt_CovidHealthApp.Service;

import com.example.EGovt_CovidHealthApp.Repostiory.CovidAdminOperatorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CovidAdminOperatorService {
    public final CovidAdminOperatorRepository covidAdminOperatorRepository;
    private static final Logger LOG = LogManager.getLogger(CovidAdminOperatorService.class);

    public CovidAdminOperatorService(CovidAdminOperatorRepository covidAdminOperatorRepository) {
        this.covidAdminOperatorRepository = covidAdminOperatorRepository;
    }

    /**
     * Logs in the patient
     *
     * @param email
     * @param password
     * @return
     */
    public ResponseEntity<String> loginCovidAdminOperator(String email, String password){
        if(covidAdminOperatorRepository.existsByEmailAndPasswordAndStatusTrue(email, password)){
            return ResponseEntity.ok().body(("covid admin Operator has been logged in..."));
        }else{
            return new ResponseEntity<>("covid admin Operator email or password maybe wrong.", HttpStatus.NOT_FOUND);
        }
    }

}
