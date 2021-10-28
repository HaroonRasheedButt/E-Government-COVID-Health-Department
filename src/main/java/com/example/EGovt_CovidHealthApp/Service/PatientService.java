package com.example.EGovt_CovidHealthApp.Service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;

@Service
public class PatientService {
	private PatientRepository patientRepository;

	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	public ResponseEntity<List<Patient>> getAllUsers(){
		try {
			 List<Patient> patients = patientRepository.findAll();
			 if(patients.isEmpty()) {
				 return new ResponseEntity("patients not found!\n", HttpStatus.OK);
			 }
			 return ResponseEntity.ok().body(patients);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity("Error while retrieving patients data!!\n"+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<Patient> addPatient(Patient patient){
		try {
			return ResponseEntity.ok().body(patientRepository.save(patient));
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity("Unable to add patient to database", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}
