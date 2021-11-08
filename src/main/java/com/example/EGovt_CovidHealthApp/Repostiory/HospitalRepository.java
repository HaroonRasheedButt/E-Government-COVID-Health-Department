
package com.example.EGovt_CovidHealthApp.Repostiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>{
	List<Hospital> findAllByStatusTrue();
	Hospital findByEmailAndPassword(String email, String password);
	List<Hospital> findAllByStatusTrueOrderByCreatedDateDesc();
}
