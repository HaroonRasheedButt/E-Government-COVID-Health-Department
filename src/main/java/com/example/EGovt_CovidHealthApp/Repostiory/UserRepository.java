package com.example.EGovt_CovidHealthApp.Repostiory;

import com.example.EGovt_CovidHealthApp.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByStatusTrueOrderByCreatedDateDesc();

    User findByUsername(String username);
    User findByCnicAndStatusTrue(String cnic);

    Long countByIsCovidTrue();
    Long countByIsCovidTrueAndCity(String city);
    Long countByIsCovidTrueAndProvince(String province);

    Long countByIsAliveFalse();
    Long countByIsAliveFalseAndCity(String city);
    Long countByIsAliveFalseAndProvince(String province);

    Long countByPatientReports_CreatedDateBetweenAndPatientReports_TestResultsIgnoreCase(Date after, Date before, String testResult);
    Long countByPatientReports_CreatedDateBetweenAndPatientReports_TestResultsIgnoreCaseAndCityIgnoreCase(Date after, Date before, String testResult, String city);
    Long countByPatientReports_CreatedDateBetweenAndPatientReports_TestResultsIgnoreCaseAndProvinceIgnoreCase(Date after, Date before, String testResult, String province);



}
