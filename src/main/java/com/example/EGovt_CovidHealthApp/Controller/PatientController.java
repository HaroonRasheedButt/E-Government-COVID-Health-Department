package com.example.EGovt_CovidHealthApp.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@GetMapping("/getPatients")
	public ResponseEntity<String> getPatients() {
		return ResponseEntity.ok().body("Hello world");
	}
}