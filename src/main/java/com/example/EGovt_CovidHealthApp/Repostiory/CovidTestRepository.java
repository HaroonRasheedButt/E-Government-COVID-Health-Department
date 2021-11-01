package com.example.EGovt_CovidHealthApp.Repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.CovidTest;

@Repository
public interface CovidTestRepository extends JpaRepository<CovidTest, Long> {

}
