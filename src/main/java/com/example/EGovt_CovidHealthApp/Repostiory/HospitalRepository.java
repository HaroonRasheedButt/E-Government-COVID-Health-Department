
package com.example.EGovt_CovidHealthApp.Repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>{
	
}
