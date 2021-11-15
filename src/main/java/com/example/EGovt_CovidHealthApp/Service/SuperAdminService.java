package com.example.EGovt_CovidHealthApp.Service;

import com.example.EGovt_CovidHealthApp.Repostiory.SuperAdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;

import com.example.EGovt_CovidHealthApp.Model.Entity.Company;
import com.example.EGovt_CovidHealthApp.Model.Entity.Hospital;
import com.example.EGovt_CovidHealthApp.Model.Entity.Lab;
import com.example.EGovt_CovidHealthApp.Model.Entity.Patient;
import com.example.EGovt_CovidHealthApp.Repostiory.PatientRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SuperAdminService {
    private final CompanyService companyService;
    private final SuperAdminRepository superAdminRepository;
    private final HospitalService hospitalService;
    private final PatientRepository patientRepository;
    private final LabService labService;
    private static final Logger LOG = LogManager.getLogger(SuperAdminService.class);

    public SuperAdminService(CompanyService companyService, HospitalService hospitalService,
                             PatientRepository patientRepository, LabService labService, SuperAdminRepository superAdminRepository) {
        this.superAdminRepository = superAdminRepository;
        this.companyService = companyService;
        this.hospitalService = hospitalService;
        this.patientRepository = patientRepository;
        this.labService = labService;
    }


    /**
     * Logs in the super admin
     *
     * @param email
     * @param password
     * @return
     */
    public ResponseEntity<String> loginAdmin(String email, String password) {
        if (superAdminRepository.existsByEmailAndPasswordAndStatusTrue(email, password)) {
            return ResponseEntity.ok().body(("Admin has been logged in..."));
        } else {
            return new ResponseEntity<>("Admin email or password maybe wrong.", HttpStatus.NOT_FOUND);
        }
    }


    // ------------------------------------Admin Company Operations------------------------------------ //

    /**
     * @return Response Entity of type Company
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function gets all the companies details in database.
     **/
    public ResponseEntity<List<Company>> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    /**
     * @param company: A company object to be added
     * @return Response Entity of type Company
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a company in database.
     **/
    public ResponseEntity<Company> addCompany(Company company) {
        return companyService.addCompany(company);
    }

    /**
     * @param company: A company object to be added
     * @return Response Entity of type Company
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a company in database.
     **/
    public ResponseEntity<Company> updateCompany(Company company) {
        return companyService.updateCompany(company);
    }

    /**
     * @param companies : The id of the the company to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function deletes a company in database by changing its
     * status to false.
     **/
    public ResponseEntity<String> deleteCompany(List<Company> companies) {
        return companyService.deleteCompany(companies);
    }

    // -------------------------Admin Hospital Operations------------------------------------ //

    /**
     * @param hospital: A hospital object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a hospital in database.
     **/
    public ResponseEntity<Hospital> addHospital(Hospital hospital) {
        return hospitalService.addHospital(hospital);
    }

    /**
     * @param hospital: A hospital object to be updated
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a hospital in database.
     **/
    public ResponseEntity<Hospital> updateHospital(Hospital hospital) {
        return hospitalService.updateHospital(hospital);
    }

    /**
     * @param hospitals : The id of the the hospital to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function deletes a hospital in database by changing its
     * status to false.
     **/
    public ResponseEntity<String> deleteHospital(List<Hospital> hospitals) {
        return hospitalService.deleteHospital(hospitals);
    }

    // -------------Admin Patient Operations-------------//

    /**
     * @param patient: A Patient object to be updated
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a patient's detail in database.
     **/
    public ResponseEntity<Patient> updatePatient(Patient patient) {
        try {
            Optional<Patient> exists = patientRepository.findById(patient.getId());
            if (exists.isPresent()) {
                patient.setUpdatedDate(DateTimeUtil.getDate());
                patientRepository.save(patient);
                LOG.info("Patient successfully updated in the database: " + patient);
                return ResponseEntity.ok().body(patient);
            } else {
                LOG.info("Patient could not be updated because the id could not be found  : ");
                return new ResponseEntity("Company of this id does not exist. Please update a existing record!",
                        HttpStatus.ACCEPTED);
            }
        } catch (PropertyValueException e) {
            LOG.info("The syntax of the patient object is invalid : " + patient + e.getMessage());
            return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error while saving the patient object to database  : " + patient + e.getMessage());
            return new ResponseEntity("Error updating patient!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param patients : The id of the the patient to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function deletes a patient in database by changing its
     * status to false.
     **/
    public ResponseEntity<String> deletePatient(List<Patient> patients) {
        try {

            for (Patient patient : patients) {
                if (Objects.isNull(patient.getId()))
                    return new ResponseEntity("Please provide the ID of patient, having email : " + patient.getEmail(),
                            HttpStatus.PARTIAL_CONTENT);
                patient.setStatus(false);
                patientRepository.save(patient);
            }
            LOG.info("Patients deleted successfully bu turning their status to false!");
            return ResponseEntity.ok().body("patients successfully deleted");
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error while deleting the patient object from database  : " + e.getMessage());
            return new ResponseEntity("Error while deleting patients!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // ------------------------------------Admin Lab
    // Operations------------------------------------ //

    /**
     * @param lab: A lab object to be added
     * @return Response Entity of type Lab
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function adds a lab in database.
     **/
    public ResponseEntity<Lab> addLab(Lab lab) {
        return labService.addLab(lab);
    }

    /**
     * @param lab: A lab object to be updated
     * @return Response Entity of type Lab
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a lab in database.
     **/
    public ResponseEntity<Lab> updateLab(Lab lab) {
        return labService.updateLab(lab);
    }

    /**
     * @param labs : The id of the the lab to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function deletes a lab in database by changing its
     * status to false.
     **/
    public ResponseEntity<String> deleteLab(List<Lab> labs) {
        return labService.deleteLab(labs);
    }

}
