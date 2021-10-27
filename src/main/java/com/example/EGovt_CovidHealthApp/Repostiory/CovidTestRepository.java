package com.example.EGovt_CovidHealthApp.Repostiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EGovt_CovidHealthApp.Model.Entity.CovidTest;

public interface CovidTestRepository extends JpaRepository<CovidTest, Long> {

}
