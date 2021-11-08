package com.example.EGovt_CovidHealthApp.Controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.EGovt_CovidHealthApp.Model.Interface.CovidStatsDto;
import com.example.EGovt_CovidHealthApp.Service.HomeService;
import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

@RestController
@RequestMapping("/home")
public class HomeController {
	private final HomeService homeService;
	private static final Logger LOG = LogManager.getLogger(HomeService.class);
	
	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}
	
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of country.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current COVID status of country
	 **/
	@GetMapping("/stats/currentCountryStats")
	public ResponseEntity<Long> currentCountryStatus(@RequestHeader("Authorization") Optional<String> authToken)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidCountryStatus();
	}
	
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of city.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current COVID status of city
	 **/
	@GetMapping("/stats/currentCityStats")
	public ResponseEntity<Long> currentCityStatus(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("city") String city)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidCityStatus(city);
	}
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of province.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current COVID status of province
	 **/
	@GetMapping("/stats/currentProvinceStats")
	public ResponseEntity<Long> currentProvinceStatus(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("province") String province)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidProvinceStatus(province);
	}
	
	
	/*------Covid deaths---*/
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of country.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current COVID deaths of country
	 **/
	@GetMapping("/stats/currentCountryDeaths")
	public ResponseEntity<Long> currentCountryDeaths(@RequestHeader("Authorization") Optional<String> authToken)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidCountryDeaths();
	}
	
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of city.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing COVID deaths of city
	 **/
	@GetMapping("/stats/currentCityDeaths")
	public ResponseEntity<Long> currentCityDeaths(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("city") String city)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidCityDeaths(city);
	}
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the current COVID status of province.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing current deaths of province
	 **/
	@GetMapping("/stats/currentProvinceDeaths")
	public ResponseEntity<Long> currentProvinceDeaths(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestHeader("province") String province)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidProvinceDeaths(province);
	}
	
	
	
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the date ranged COVID stats of province.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing stats of country between a date range
	 **/
	@GetMapping("/stats/date/countryStats")
	public ResponseEntity<Long> getCovidCountryStatsBetweenDates(@RequestHeader("Authorization") Optional<String> authToken,
			 @RequestBody CovidStatsDto covidStatsDto)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidCountryStatsBetweenDates(covidStatsDto.getAfter(), covidStatsDto.getBefore());
	}
	
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the date ranged COVID status.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing stats of city between a date range
	 **/
	@GetMapping("/stats/date/cityStats")
	public ResponseEntity<Long> getCovidCityStatsBetweenDates(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestBody() CovidStatsDto covidStatsDto)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidCityStatsBetweenDates(covidStatsDto.getCity(), covidStatsDto.getAfter(), covidStatsDto.getBefore());
	}
	
	/**
	 * @creationDate 7 November 2021
	 * @description Gets the date ranged COVID status.
	 * @throws Exception the exception
	 * @return ResponseEnity<Long>: A Long value showing stats of province between a date range
	 **/
	@GetMapping("/stats/date/provinceStats")
	public ResponseEntity<Long> getCovidProvinceStatsBetweenDates(@RequestHeader("Authorization") Optional<String> authToken,
			@RequestBody() CovidStatsDto covidStatsDto)

			throws Exception {
		try {
			AuthorizationUtil.authorized(authToken);
		} catch (HttpClientErrorException e) {
			LOG.info("Unable to Authorize : " + e.getMessage());
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
				return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
		}
		return homeService.getCovidProvinceStatsBetweenDates(covidStatsDto.getProvince(), covidStatsDto.getAfter(), covidStatsDto.getBefore());
	}
}
