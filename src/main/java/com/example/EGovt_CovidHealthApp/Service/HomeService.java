package com.example.EGovt_CovidHealthApp.Service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EGovt_CovidHealthApp.Repostiory.PatientReportRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;

@Service
public class HomeService {
	private final PatientRepository patientRepository;
	private final PatientReportRepository patientReportRepository;
	private static final Logger LOG = LogManager.getLogger(HomeService.class);

	public HomeService(PatientRepository patientRepository, PatientReportRepository patientReportRepository) {
		this.patientRepository = patientRepository;
		this.patientReportRepository = patientReportRepository;
	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of country.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current COVID status of
	 *         country
	 **/
	public ResponseEntity<Long> getCovidCountryStatus() {
		try {
			Long count = patientRepository.countByIsCovidTrue();
			if (count != null) {
				LOG.info("covid country current cases stats: " + count);
				return ResponseEntity.ok().body(count);
			} else {
				LOG.info("covid country current cases stats : " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the COVID stats!!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of city.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current COVID status of
	 *         city
	 **/
	public ResponseEntity<Long> getCovidCityStatus(String city) {
		try {
			Long count = patientRepository.countByIsCovidTrueAndCity(city);
			if (count != null) {
				LOG.info("covid city current cases stats: " + count);
				return ResponseEntity.ok().body(count);
			} else {
				LOG.info("covid city current cases stats : " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the COVID stats!!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of province.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current COVID status of
	 *         province
	 **/
	public ResponseEntity<Long> getCovidProvinceStatus(String province) {
		try {
			Long count = patientRepository.countByIsCovidTrueAndProvince(province);
			if (count != null) {
				LOG.info("covid province current cases stats: " + count);
				return ResponseEntity.ok().body(count);
			} else {
				LOG.info("covid province current cases stats : " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the COVID stats!!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID deaths of country.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current deaths of country
	 **/
	public ResponseEntity<Long> getCovidCountryDeaths() {
		try {
			Long count = patientRepository.countByIsAliveFalse();
			if (count != null) {
				LOG.info("covid country deaths stats: " + count);
				return ResponseEntity.ok().body(count);
			} else {
				LOG.info("covid country deaths stats: " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the COVID death stats!!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID deaths of city.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current deaths of city
	 **/
	public ResponseEntity<Long> getCovidCityDeaths(String city) {
		try {
			Long count = patientRepository.countByIsAliveFalseAndCity(city);
			if (count != null) {
				LOG.info("covid city deaths stats: " + count);
				return ResponseEntity.ok().body(count);
			} else {
				LOG.info("covid city deaths stats: " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the COVID death stats!!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID deaths of province.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current deaths of province
	 **/
	public ResponseEntity<Long> getCovidProvinceDeaths(String province) {
		try {
			Long count = patientRepository.countByIsAliveFalseAndProvince(province);
			if (count != null) {
				LOG.info("covid province deaths stats: " + count);
				return ResponseEntity.ok().body(count);
			} else {
				LOG.info("covid province deaths stats: " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the COVID death stats!!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the date ranged COVID stats of province.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing stats of country between a date range
	 **/
	public ResponseEntity<Long> getCovidCountryStatsBetweenDates(Date after, Date before) {
		try {
			Long count = patientRepository.countByPatientReports_CreatedDateBetweenAndPatientReports_TestResultsIgnoreCase(after, before, "positive");
			if (count != null) {
				LOG.info("covid date range stats : " + count);
				return ResponseEntity.ok().body(count);
			}else {
				LOG.info("covid date range stats : " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the covid date range stats !!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @creationDate 7 November 2021
	 * @description Gets the date ranged COVID status.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing stats of city between a date range
	 **/
	public ResponseEntity<Long> getCovidCityStatsBetweenDates(String city, Date after, Date before) {
		try {
			Long count = patientRepository.countByPatientReports_CreatedDateBetweenAndPatientReports_TestResultsIgnoreCaseAndCityIgnoreCase(after, before, "positive", city);
			if (count != null) {
				LOG.info("covid date range stats : " + count);
				return ResponseEntity.ok().body(count);
			}else {
				LOG.info("covid date range stats : " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the covid date range stats !!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}

	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the date ranged COVID status.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing stats of province between a date range
	 **/
	public ResponseEntity<Long> getCovidProvinceStatsBetweenDates(String province, Date after, Date before) {
		try {
			Long count = patientRepository.countByPatientReports_CreatedDateBetweenAndPatientReports_TestResultsIgnoreCaseAndProvinceIgnoreCase( after, before, "positive",  "Federal");
			if (count != null) {
				LOG.info("covid date range stats : " + count);
				return ResponseEntity.ok().body(count);
			}else {
				LOG.info("covid date range stats : " + count);
				return ResponseEntity.ok().body((long) 0);
			}
		} catch (Exception e) {
			LOG.info("Exception caught while getting the covid date range stats !!\n" + e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
}