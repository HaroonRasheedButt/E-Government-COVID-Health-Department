package com.example.EGovt_CovidHealthApp.Controller;


import com.example.EGovt_CovidHealthApp.Model.Interface.CovidStatsDto;
import com.example.EGovt_CovidHealthApp.Service.HomeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {
    private static final Logger LOG = LogManager.getLogger(HomeService.class);
    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing current COVID status of country
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of country.
     **/
    @GetMapping("/stats/currentCountryStats")
    public ResponseEntity<Long> currentCountryStatus() {
        return homeService.getCovidCountryStatus();
    }


    /**
     * @return ResponseEntity<Long>: A Long value showing current COVID status of city
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of city.
     **/
    @GetMapping("/stats/currentCityStats")
    public ResponseEntity<Long> currentCityStatus(@RequestHeader("city") String city) {
        return homeService.getCovidCityStatus(city);
    }

    /**
     * @return ResponseEnity<Long>: A Long value showing current COVID status of province
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of province.
     **/
    @GetMapping("/stats/currentProvinceStats")
    public ResponseEntity<Long> currentProvinceStatus(@RequestHeader("province") String province) {
        return homeService.getCovidProvinceStatus(province);
    }


    /*------Covid deaths---*/

    /**
     * @return ResponseEnity<Long>: A Long value showing current COVID deaths of country
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of country.
     **/
    @GetMapping("/stats/currentCountryDeaths")
    public ResponseEntity<Long> currentCountryDeaths() {
        return homeService.getCovidCountryDeaths();
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing COVID deaths of city
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of city.
     **/
    @GetMapping("/stats/currentCityDeaths")
    public ResponseEntity<Long> currentCityDeaths(@RequestHeader("city") String city) {
        return homeService.getCovidCityDeaths(city);
    }

    /**
     * @return ResponseEnity<Long>: A Long value showing current deaths of province
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of province.
     **/
    @GetMapping("/stats/currentProvinceDeaths")
    public ResponseEntity<Long> currentProvinceDeaths(@RequestHeader("province") String province) {
        return homeService.getCovidProvinceDeaths(province);
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing stats of country between a date range
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the date ranged COVID stats of province.
     **/
    @GetMapping("/stats/date/countryStats")
    public ResponseEntity<Long> getCovidCountryStatsBetweenDates(@RequestBody CovidStatsDto covidStatsDto) {
        return homeService.getCovidCountryStatsBetweenDates(covidStatsDto.getAfter(), covidStatsDto.getBefore());
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing stats of city between a date range
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the date ranged COVID status.
     **/
    @GetMapping("/stats/date/cityStats")
    public ResponseEntity<Long> getCovidCityStatsBetweenDates(@RequestBody() CovidStatsDto covidStatsDto) {
        return homeService.getCovidCityStatsBetweenDates(covidStatsDto.getCity(), covidStatsDto.getAfter(), covidStatsDto.getBefore());
    }

    /**
     * @return ResponseEnity<Long>: A Long value showing stats of province between a date range
     * @ the exception
     * @creationDate 7 November 2021
     * @description Gets the date ranged COVID status.
     **/
    @GetMapping("/stats/date/provinceStats")
    public ResponseEntity<Long> getCovidProvinceStatsBetweenDates(@RequestBody() CovidStatsDto covidStatsDto) {
        return homeService.getCovidProvinceStatsBetweenDates(covidStatsDto.getProvince(), covidStatsDto.getAfter(), covidStatsDto.getBefore());
    }
}
