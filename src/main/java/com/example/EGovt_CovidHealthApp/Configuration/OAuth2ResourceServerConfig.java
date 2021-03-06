package com.example.EGovt_CovidHealthApp.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final static List<String> PATIENT_APIS = Arrays.asList(
            "/user/getUserById",
            "/user/getUserByCnic",
            "/user/updateUser",
            "/hospital/getAllHospitals"
    );
    private final static List<String> COVID_ADMIN_APIS = Arrays.asList(
            "/hospital/user/update/patientReport",
            "/hospital/user/update/patientVaccination",
            "/hospital/{hospitalId}/addMobileVaccineCar",
            "/hospital/{hospitalId}/addMobileVaccineCar",
            "/hospital/{hospitalId}/addPatientReport/{userCnic}",
            "/hospital/{hospitalId}/addPatientVaccination/{userCnic}",
            "/hospital/{hospitalId}/getMobileVaccineCars",
            "/hospital/{hospitalId}/updateMobileVaccineCar",
            "/hospital/updateHospital",
            "/lab/getAllLabs",
            "/lab/deleteLab",
            "/lab/lab/updateLab",
            "/lab/{labId}/addPatientReport/{patientCnic}",
            "/lab/{labId}/addPatientVaccination/{patientCnic}"
    );
    private final static List<String> SUPER_ADMIN_APIS = Arrays.asList(
            "/user/deleteUser",
            "/user/getUserById",
            "/user/getUserByCnic",
            "/user/updateUser",
            "company/addCompany",
            "company/deleteCompany",
            "company/updateCompany",
            "company/getAllCompanies",
            "/hospital/getAllHospitals",
            "/lab/addLab,"
    );
    private final static List<String> UNRESTRICTED_APIS = Arrays.asList(
            "/user/loginUser",
            "/user/verifyUser",
            "/user/addUser",
            "/user/vaccine/status",
            "/company/status",
            "/home/stats/currentCityDeaths",
            "/home/stats/currentCityStats",
            "/home/stats/currentCountryDeaths",
            "/home/stats/currentCountryStats",
            "/home/stats/currentProvinceDeaths",
            "/home/stats/currentProvinceStats",
            "/home/stats/date/cityStats",
            "/home/stats/date/countryStats",
            "/home/stats/date/provinceStats",
            "/permission",
            "/permission/addPermission",
            "/permission/deletePermission/{id}",
            "/permission/updatePermission",
            "/role",
            "/role/addRole",
            "/role/deleteUser/{id}",
            "/role/updateUserInfo"
    );

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("api");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers(String.valueOf(SUPER_ADMIN_APIS)).hasAuthority("ROLE_SUPER_ADMIN")
                .antMatchers(String.valueOf(PATIENT_APIS)).hasAuthority("ROLE_PATIENT")
                .antMatchers(String.valueOf(COVID_ADMIN_APIS)).hasAuthority("ROLE_COVID_ADMIN")
                .antMatchers(String.valueOf(UNRESTRICTED_APIS)).permitAll()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
