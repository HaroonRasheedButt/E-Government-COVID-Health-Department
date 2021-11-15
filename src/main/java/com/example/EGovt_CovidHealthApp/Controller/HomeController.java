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
     * @return ResponseEnity<Long>: A Long value showing current COVID status of country
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of country.
     **/
    @GetMapping("/stats/currentCountryStats")
    public ResponseEntity<Long> currentCountryStatus(@RequestHeader("Authorization") String authToken)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidCountryStatus();
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing current COVID status of city
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of city.
     **/
    @GetMapping("/stats/currentCityStats")
    public ResponseEntity<Long> currentCityStatus(@RequestHeader("Authorization") String authToken,
                                                  @RequestHeader("city") String city)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidCityStatus(city);
    }

    /**
     * @return ResponseEnity<Long>: A Long value showing current COVID status of province
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of province.
     **/
    @GetMapping("/stats/currentProvinceStats")
    public ResponseEntity<Long> currentProvinceStatus(@RequestHeader("Authorization") String authToken,
                                                      @RequestHeader("province") String province)

            throws Exception {

        AuthorizationUtil.authorized(authToken);

        return homeService.getCovidProvinceStatus(province);
    }


    /*------Covid deaths---*/

    /**
     * @return ResponseEnity<Long>: A Long value showing current COVID deaths of country
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of country.
     **/
    @GetMapping("/stats/currentCountryDeaths")
    public ResponseEntity<Long> currentCountryDeaths(@RequestHeader("Authorization") String authToken)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidCountryDeaths();
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing COVID deaths of city
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of city.
     **/
    @GetMapping("/stats/currentCityDeaths")
    public ResponseEntity<Long> currentCityDeaths(@RequestHeader("Authorization") String authToken,
                                                  @RequestHeader("city") String city)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidCityDeaths(city);
    }

    /**
     * @return ResponseEnity<Long>: A Long value showing current deaths of province
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the current COVID status of province.
     **/
    @GetMapping("/stats/currentProvinceDeaths")
    public ResponseEntity<Long> currentProvinceDeaths(@RequestHeader("Authorization") String authToken,
                                                      @RequestHeader("province") String province)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidProvinceDeaths(province);
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing stats of country between a date range
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the date ranged COVID stats of province.
     **/
    @GetMapping("/stats/date/countryStats")
    public ResponseEntity<Long> getCovidCountryStatsBetweenDates(@RequestHeader("Authorization") String authToken,
                                                                 @RequestBody CovidStatsDto covidStatsDto)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidCountryStatsBetweenDates(covidStatsDto.getAfter(), covidStatsDto.getBefore());
    }


    /**
     * @return ResponseEnity<Long>: A Long value showing stats of city between a date range
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the date ranged COVID status.
     **/
    @GetMapping("/stats/date/cityStats")
    public ResponseEntity<Long> getCovidCityStatsBetweenDates(@RequestHeader("Authorization") String authToken,
                                                              @RequestBody() CovidStatsDto covidStatsDto)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidCityStatsBetweenDates(covidStatsDto.getCity(), covidStatsDto.getAfter(), covidStatsDto.getBefore());
    }

    /**
     * @return ResponseEnity<Long>: A Long value showing stats of province between a date range
     * @throws Exception the exception
     * @creationDate 7 November 2021
     * @description Gets the date ranged COVID status.
     **/
    @GetMapping("/stats/date/provinceStats")
    public ResponseEntity<Long> getCovidProvinceStatsBetweenDates(@RequestHeader("Authorization") String authToken,
                                                                  @RequestBody() CovidStatsDto covidStatsDto)

            throws Exception {

        AuthorizationUtil.authorized(authToken);
        return homeService.getCovidProvinceStatsBetweenDates(covidStatsDto.getProvince(), covidStatsDto.getAfter(), covidStatsDto.getBefore());
    }
}
