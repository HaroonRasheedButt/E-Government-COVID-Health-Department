package com.example.EGovt_CovidHealthApp.Repostiory;

import com.example.EGovt_CovidHealthApp.Model.Entity.CovidAdminOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidAdminOperatorRepository extends JpaRepository<CovidAdminOperator, Long> {
    Boolean existsByEmailAndPasswordAndStatusTrue(String email, String password);

}
