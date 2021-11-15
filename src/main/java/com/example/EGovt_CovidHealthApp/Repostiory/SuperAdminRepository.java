package com.example.EGovt_CovidHealthApp.Repostiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.SuperAdmin;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long>{
	List<SuperAdmin> findAllByStatusTrue();
	Boolean existsByEmailAndPasswordAndStatusTrue(String email, String password);

}
