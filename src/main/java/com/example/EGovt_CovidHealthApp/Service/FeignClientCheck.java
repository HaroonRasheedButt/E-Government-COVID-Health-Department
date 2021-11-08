package com.example.EGovt_CovidHealthApp.Service;

import java.util.List;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.EGovt_CovidHealthApp.Model.Interface.Tags;

//@FeignClient(name="PDSI-SYSTEM")
public interface FeignClientCheck {
	@GetMapping("/tags/all")
	public ResponseEntity<List<Tags>> getTags(@RequestHeader("Authorization") String Authorization);
}
