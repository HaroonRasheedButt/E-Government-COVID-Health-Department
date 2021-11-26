package com.example.EGovt_CovidHealthApp.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.*;
import com.example.EGovt_CovidHealthApp.Model.Interface.DetailedCustomResponse;
import com.example.EGovt_CovidHealthApp.Repostiory.HospitalRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.MobileVaccineCarRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.UserRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HospitalService {
    private static final Logger LOG = LogManager.getLogger(HospitalService.class);
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final MobileVaccineCarRepository mobileVaccineCarRepository;
    private final UserService patientService;
    private final PatientReportService patientReportService;
    private final PatientVaccinationService patientVaccinationService;

    public HospitalService(HospitalRepository hospitalRepository, PatientReportService patientReportService,
                           PatientVaccinationService patientVaccinationService, UserService patientService,
                           MobileVaccineCarRepository mobileVaccineCarRepository, UserRepository userRepository) {
        this.hospitalRepository = hospitalRepository;
        this.mobileVaccineCarRepository = mobileVaccineCarRepository;
        this.patientService = patientService;
        this.patientReportService = patientReportService;
        this.patientVaccinationService = patientVaccinationService;
        this.userRepository = userRepository;
    }

    /**
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function gets all the hospitals details in database.
     **/
    public ResponseEntity<DetailedCustomResponse> getAllHospitals(HttpServletRequest req) {
        try {
            Optional<List<Hospital>> hospitals = Optional
                    .of(hospitalRepository.findAllByStatusTrueOrderByCreatedDateDesc());
            if (hospitals.isPresent()) {
                LOG.info("Hospitals successfully Retrieved : {}", hospitals.get());
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Hospitals successfully Retrieved", req.getRequestURI(), DateTimeUtil.getDate(), hospitals.get()));
            } else {
                LOG.info("Hospitals Not found in the database: {}", hospitals.get());
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Hospitals Not Found in database", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Exception caught while retrieving hospitals data : \n {}", e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error retrieving all hospitals!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 1st November 2021
     * @description This function gets a hospital based on an id from database.
     **/
    public ResponseEntity<DetailedCustomResponse> getHospitalById(HttpServletRequest req, long hospitalId) {
        try {
            Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
            if (hospital.isPresent()) {
                LOG.info("Hospital found by id.");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Hospital Found by id", req.getRequestURI(), DateTimeUtil.getDate(), hospital.get()));
            } else {
                LOG.info("Hospital not found");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "Hospital Not Found by id", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Exception caught in get all hospital. Unable to find hospital by id.");
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Exception caught in get all hospital. Unable to find hospital by id.\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * @param hospital: A hospital object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function adds a hospital in database.
     **/
    public ResponseEntity<DetailedCustomResponse> addHospital(HttpServletRequest req, Hospital hospital) {
        try {
            hospital.setCreatedDate(DateTimeUtil.getDate());
            hospital.setStatus(true);
            hospital = hospitalRepository.save(hospital);
            LOG.info("Hospitals successfully added to the database: {}", hospital);
            return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Hospitals successfully Added to Database", req.getRequestURI(), DateTimeUtil.getDate(), hospital));
        } catch (PropertyValueException e) {
            LOG.info("The syntax of the hospital object is invalid : {}", hospital + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Please send a valid object to add into the databse!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        } catch (DataIntegrityViolationException e) {
            // TODO: handle exception
            LOG.info("Error.... Duplicate entry for a unique value or something is maybe null!! : {} ", hospital + "\n" + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error adding a hospital into database. Duplicate entry for a unique value or something is maybe null!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error while saving the hospital object to database  : {}", hospital + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error adding a hospital into database!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * @param hospital: A hospital object to be added
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function updates a hospital in database.
     **/
    public ResponseEntity<DetailedCustomResponse> updateHospital(HttpServletRequest req, Hospital hospital) {
        try {
            Optional<Hospital> exists = hospitalRepository.findById(hospital.getId());
            if (exists.isPresent()) {
                hospital.setUpdatedDate(DateTimeUtil.getDate());
                hospitalRepository.save(hospital);
                LOG.info("Hospitals successfully updated in the database: {}", hospital);
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Hospitals successfully updated in Database", req.getRequestURI(), DateTimeUtil.getDate(), hospital));
            } else {
                LOG.info("Hospital could not be updated because the id could not be found  : ");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "Failed to update the hospital. The hospital of this id does not exist", req.getRequestURI(), DateTimeUtil.getDate(), hospital));
            }

        } catch (PropertyValueException e) {
            LOG.info("The syntax of the hospital object is invalid : {}", hospital + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Please send a valid object to update from the database!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error while saving the hospital object to database  : {}", hospital + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while saving the hospital object to database\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param hospitals: The id of the the hospital to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function deletes a hospital in database by changing its
     * status to false.
     **/
    public ResponseEntity<DetailedCustomResponse> deleteHospital(HttpServletRequest req, List<Hospital> hospitals) {
        try {
            for (Hospital hospital : hospitals) {
                if (Objects.isNull(hospital.getId()))
                    return new ResponseEntity(new DetailedCustomResponse(206, HttpStatus.PARTIAL_CONTENT, "Please provide the ID of hospital, having email : " + hospital.getEmail(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.PARTIAL_CONTENT);

                hospital.setStatus(false);
                hospitalRepository.save(hospital);
            }
            LOG.info("Hospitals deleted successfully bu turning their status to false!");
            return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Hospitals deleted successfully bu changing their status to false!", req.getRequestURI(), DateTimeUtil.getDate(), hospitals));
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error while deleting the hospital object from database  : \n{}", e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while deleting the hospital object from database\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param patientReport: A patientReport object to be added
     * @param patientCnic:   A patient CNIC that is unique for every patient
     * @param hospitalId:    A hospital id
     * @return Response Entity of type Hospital
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function adds a patientReport in database.
     **/
    public ResponseEntity<DetailedCustomResponse> addPatientReport(HttpServletRequest req, PatientReport patientReport, String patientCnic, long hospitalId) {

        try {
            Optional<Hospital> hospital = Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
            if (hospital.isPresent()) {
                Optional<User> patient = Optional.ofNullable(userRepository.findByCnicAndStatusTrue(patientCnic));
                if (patient.isPresent()) {
                    // this will set the created date and status of patient Report
                    patientReport = patientReportService.addPatientReport(patientReport);
                    patient.get().getPatientReports().add(patientReport);

                    if (patientReport.getTestResults().toLowerCase().equals("positive")) {
                        patient.get().setCovid(true);
                    } else if (patientReport.getTestResults().toLowerCase().equals("negative")) {
                        patient.get().setCovid(false);
                    }
                    // type casting the response object into the User class
                    patient = Optional.of((User) (patientService.updateUser(req, patient.get()).getBody().getData()));
                    List<User> patientsOfHospital = hospital.get().getPatients();

                    Integer index = patientInHospitalExists(patientsOfHospital, patientCnic);
                    if (index >= 0) {
                        LOG.info("User Exists in Hospital already... ");
                        hospital.get().getPatients().set(index, patient.get());
                        return updateHospital(req, hospital.get());

                    } else {
                        LOG.info("User does not Exists in Hospital. Adding... ");
                        hospital.get().getPatients().add(patient.get());
                        return updateHospital(req, hospital.get());
                    }

                } else {
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The patient of this id does not exist. Please enter a valid ID.", req.getRequestURI(), DateTimeUtil.getDate(), hospital));
                }
            } else {
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The patient of this id does not exist. Please enter a valid ID.", req.getRequestURI(), DateTimeUtil.getDate(), hospital));
            }
        } catch (Exception e) {
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while error adding the patient report to the database. \n" + e.getMessage() + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param patientCnic
     * @param patientReport
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 1st November 2021
     * @description This function gets a patient based on their CNIC from database.
     **/
    public ResponseEntity<DetailedCustomResponse> updatePatientReport(HttpServletRequest req, String patientCnic, PatientReport patientReport) {
        try {
            Optional<User> patient = Optional.ofNullable(userRepository.findByCnicAndStatusTrue(patientCnic));
            if (patient.isPresent()) {
                Integer index = patientReportExistsInUser(patient.get().getPatientReports(), patientReport.getId());
                if (index >= 0) {
                    patientReport = patientReportService.updatePatientReport(patientReport);
                    patient.get().getPatientReports().set(index, patientReport);
                    userRepository.save(patient.get());
                    return patientService.getUserByCnic(req, patientCnic);
                } else {
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The Report does not belong to this patient", req.getRequestURI(), DateTimeUtil.getDate(), null));
                }
            } else {
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The patient of this cnic does not exist", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while error updating the patient report. \n" + e.getMessage() + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks if the given report id is a report of the patient or not
     *
     * @param patientReprots
     * @param patientReportId
     * @return Integer showing
     */
    private Integer patientReportExistsInUser(List<PatientReport> patientReprots, long patientReportId) {
        Integer index = 0;
        for (PatientReport patientReport : patientReprots) {
            if (patientReportId == patientReport.getId())
                return index;
            index++;
        }

        return -1;
    }


    /**
     * @param patientVaccination: A patientVaccination object to be added
     * @return Response Entity of type PatientVaccination
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function adds a patientVaccination in database.
     **/
    public ResponseEntity<DetailedCustomResponse> addPatientVaccination(HttpServletRequest req, PatientVaccination patientVaccination, String patientCnic,
                                                                        long hospitalId) {
        try {
            Optional<Hospital> hospital = Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
            if (hospital.isPresent()) {
                Optional<User> patient = Optional.ofNullable(userRepository.findByCnicAndStatusTrue(patientCnic));
                if (patient.isPresent()) {

                    // This will set the created date and status of patient Report
                    patientVaccination = patientVaccinationService.addPatientVaccination(patientVaccination);
                    patient.get().getPatientVaccination().add(patientVaccination);
                    patient.get().setVaccinated(true);
                    patient = Optional.of((User) patientService.updateUser(req, patient.get()).getBody().getData());
                    List<User> patientsOfHospital = hospital.get().getPatients();

                    Integer index = patientInHospitalExists(patientsOfHospital, patientCnic);
                    if (index >= 0) {
                        LOG.info("User Exists in Hospital already... ");
                        hospital.get().getPatients().set(index, patient.get());
                        return updateHospital(req, hospital.get());

                    } else {
                        LOG.info("User Exists in Hospital already... ");
                        hospital.get().getPatients().add(patient.get());
                        return updateHospital(req, hospital.get());
                    }

                } else {
                    LOG.info("The patient of this id does not exist. Please enter a valid ID.");
                    return new ResponseEntity("The patient of this id does not exist. Please enter a valid ID.",
                            HttpStatus.NOT_FOUND);
                }
            } else {
                LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
                return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.info("Error while error adding the patient report to the database. \n" + e.getMessage());
            return new ResponseEntity(
                    "Error while error adding the patient report to the database. \n" + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param patientCnic
     * @param patientVaccination
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 1st November 2021
     * @description This function gets a patient based on their CNIC from database.
     **/
    public ResponseEntity<DetailedCustomResponse> updatePatientVaccination(HttpServletRequest req, String patientCnic, PatientVaccination patientVaccination) {
        try {
            Optional<User> patient = Optional.ofNullable(userRepository.findByCnicAndStatusTrue(patientCnic));
            if (patient.isPresent()) {
                Integer index = patientVaccinationExistsInUser(patient.get().getPatientVaccination(), patientVaccination.getId());
                if (index >= 0) {
                    patientVaccination = patientVaccinationService.updatePatientVaccination(patientVaccination);
                    patient.get().getPatientVaccination().set(index, patientVaccination);
                    return patientService.getUserByCnic(req, patientCnic);
                } else {
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The Report does not belong to this patient", req.getRequestURI(), DateTimeUtil.getDate(), null));
                }
            } else {
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The patient of this cnic does not exist", req.getRequestURI(), DateTimeUtil.getDate(), null));

            }
        } catch (Exception e) {
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while error adding the patient report to the database. \n" + e.getMessage() + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks if the patient vaccination exist in patient or not
     *
     * @param patientVaccinations
     * @param patientVaccinationId
     * @return
     */
    private Integer patientVaccinationExistsInUser(List<PatientVaccination> patientVaccinations, long patientVaccinationId) {
        Integer index = 0;
        for (PatientVaccination patientReport : patientVaccinations) {
            if (patientVaccinationId == patientReport.getId())
                return index;
            index++;
        }

        return -1;
    }


    /**
     * Checks if the patient id already exist in the hospital or not
     *
     * @param patients
     * @param checkUserCnic
     * @return
     */
    private Integer patientInHospitalExists(List<User> patients, String checkUserCnic) {
        int index = 0;
        for (User patient : patients) {
            if (patient.getCnic().equals(checkUserCnic))
                return index;

            index++;
        }
        return -1;
    }


    /**
     * @param hospitalId: a hospital id
     * @return Response Entity of List of type MobileVaccineCar
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function gets the list of mobileVaccineCar against a particular hospital.
     **/
    public ResponseEntity<DetailedCustomResponse> getMobileVaccineCars(HttpServletRequest req, long hospitalId) {
        try {
            Optional<Hospital> hospital = Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
            if (hospital.isPresent()) {
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Mobile vaccine Cars Found", req.getRequestURI(), DateTimeUtil.getDate(), hospital.get().getMobileVaccineCars()));
            } else {
                LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The hospital of this id does not exist. Please enter a valid ID.", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Error while error adding the mobile vaccine car to the database. \n{}", e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while error adding the mobile vaccine car to the database. \n" + e.getMessage() + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param mobileVaccineCar: A mobileVaccineCar object to be added
     * @return Response Entity of type MobileVaccineCar
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function updates a Mobile Vaccine Car in database.
     **/
    public ResponseEntity<DetailedCustomResponse> updateMobileVaccineCar(HttpServletRequest req, MobileVaccineCar mobileVaccineCar,
                                                                         long hospitalId) {
        try {
            Optional<Hospital> hospital = Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
            if (hospital.isPresent()) {
                Optional<MobileVaccineCar> existingCar = mobileVaccineCarRepository.findById(mobileVaccineCar.getId());
                if (existingCar.isPresent()) {
                    mobileVaccineCar.setUpdatedDate(DateTimeUtil.getDate());
                    mobileVaccineCarRepository.save(mobileVaccineCar);
                    hospital.get().getMobileVaccineCars().add(mobileVaccineCar);
                    hospital = Optional.of((Hospital) updateHospital(req, hospital.get()).getBody().getData());
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Mobile Vaccine cars updated.", req.getRequestURI(), DateTimeUtil.getDate(), hospital.get().getMobileVaccineCars()));
                } else {
                    LOG.info("The Mobile vaccine car of this id does not exist. Please enter a valid ID.");
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The Mobile vaccine car of this id does not exist. Please enter a valid ID.", req.getRequestURI(), DateTimeUtil.getDate(), null));
                }
            } else {
                LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The hospital of this id does not exist. Please enter a valid ID.", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Error while error updating the mobile vaccine car in the database. \n{}" , e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while error updating the mobile vaccine car in the database. \n" + e.getMessage() + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * @param mobileVaccineCar: A patientVaccination object to be added
     * @return Response Entity of type MobileVaccineCar
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function adds a mobileVaccineCar in database.
     **/
    public ResponseEntity<DetailedCustomResponse> addMobileVaccineCar(HttpServletRequest req, MobileVaccineCar mobileVaccineCar,
                                                                      long hospitalId) {
        try {
            Optional<Hospital> hospital = Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
            if (hospital.isPresent()) {
                mobileVaccineCar.setCreatedDate(DateTimeUtil.getDate());
                mobileVaccineCar.setStatus(true);
                hospital.get().getMobileVaccineCars().add(mobileVaccineCar);
                hospital = Optional.of((Hospital) updateHospital(req, hospital.get()).getBody().getData());
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Mobile Vaccine cars added.", req.getRequestURI(), DateTimeUtil.getDate(), hospital.get().getMobileVaccineCars()));
            } else {
                LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The hospital of this id does not exist. Please enter a valid ID.", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Error while error adding the mobile vaccine car to the database. \n" + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while error adding the mobile vaccine car in the database. \n" + e.getMessage() + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        }
    }


//    /*-------Hosptial Admin Operator Operations-----*/

//    /**
//     * @creationDate 31st October 2021
//     * @description This function gets the list of mobileVaccineCar against a particular hospital.
//     * @param hospitalId: a hospital id
//     * @throws Exception the exception
//     * @return Response Entity of List of type MobileVaccineCar
//     **/
//    public ResponseEntity<List<User>> getCovidAdminOperator(long hospitalId) {
//        try {
//            Optional<Hospital> hospital = Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
//            if (hospital.isPresent()) {
//                return ResponseEntity.ok().body(hospital.get().getCovidAdminOperators());
//            } else {
//                LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
//                return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
//                        HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            LOG.info("Error while error getting the  covid admin operator from the database. \n" + e.getMessage());
//            return new ResponseEntity(
//                    "Error while error getting the  covid admin operator from the database. \n" + e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * @creationDate 31st October 2021
//     * @description This function updates a Mobile Vaccine Car in database.
//     * @param covidAdminOperator: A mobileVaccineCar object to be added
//     * @param hospitalId: a hospital id
//     * @throws Exception the exception
//     * @return Response Entity of type MobileVaccineCar
//     **/
//    public ResponseEntity<List<User>> updateCovidAdminOperator(User covidAdminOperator,
//                                                                         long hospitalId) {
//        try {
//            Optional<Hospital> hospital =  Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
//            if (hospital.isPresent()) {
//                Optional<User> existingOperator = userRepository.findById(covidAdminOperator.getId());
//                if (existingOperator.isPresent()) {
//                    covidAdminOperator.setUpdatedDate(DateTimeUtil.getDate());
//                    userRepository.save(covidAdminOperator);
//                    hospital.get().getCovidAdminOperators().add(covidAdminOperator);
//                    hospital = Optional.of(updateHospital(hospital.get()).getBody());
//                    return ResponseEntity.ok().body(hospital.get().getCovidAdminOperators());
//                } else {
//                    LOG.info("The Covid Admin Operator of this id does not exist. Please enter a valid ID.");
//                    return new ResponseEntity(
//                            "The  Covid Admin Operator of this id does not exist. Please enter a valid ID.",
//                            HttpStatus.NOT_FOUND);
//                }
//            } else {
//                LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
//                return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
//                        HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            LOG.info("Error while error adding the Covid Admin Operator to the database. \n" + e.getMessage());
//            return new ResponseEntity(
//                    "Error while error adding the  Covid Admin Operator to the database. \n" + e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * @creationDate 31st October 2021
//     * @description This function adds a covidAdminOperator in database.
//     * @param covidAdminOperator: A patientVaccination object to be added
//     * @throws Exception the exception
//     * @return Response Entity of type CovidAdminOperator
//     **/
//    public ResponseEntity<List<User>> addCovidAdminOperator(User covidAdminOperator,
//                                                                      long hospitalId) {
//        try {
//            Optional<Hospital> hospital =  Optional.ofNullable(hospitalRepository.findByIdAndStatusTrue(hospitalId));
//            if (hospital.isPresent()) {
//                covidAdminOperator.setCreatedDate(DateTimeUtil.getDate());
//                covidAdminOperator.setStatus(true);
//                hospital.get().getCovidAdminOperators().add(covidAdminOperator);
//                hospital = Optional.of(updateHospital(hospital.get()).getBody());
//                return ResponseEntity.ok().body(hospital.get().getCovidAdminOperators());
//            } else {
//                LOG.info("The hospital of this id does not exist. Please enter a valid ID.");
//                return new ResponseEntity("The hospital of this id does not exist. Please enter a valid ID.",
//                        HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            LOG.info("Error while error adding the covid admin operator to the database. \n" + e.getMessage());
//            return new ResponseEntity(
//                    "Error while error adding the covid admin operator to the database. \n" + e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        }
//    }

}
