package com.example.EGovt_CovidHealthApp.Repostiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;

public interface LabRepository extends JpaRepository<Lab, Long> {

}
