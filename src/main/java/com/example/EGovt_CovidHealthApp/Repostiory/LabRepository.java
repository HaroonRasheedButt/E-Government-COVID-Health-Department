package com.example.EGovt_CovidHealthApp.Repostiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {
	List<Lab> findAllByStatusTrue();
}
