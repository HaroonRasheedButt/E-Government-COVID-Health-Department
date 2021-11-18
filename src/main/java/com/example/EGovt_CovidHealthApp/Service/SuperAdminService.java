//package com.example.EGovt_CovidHealthApp.Service;
//
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.stereotype.Service;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.PropertyValueException;
//
//import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//public class SuperAdminService {
//    private final SuperAdminRepository superAdminRepository;
//    private static final Logger LOG = LogManager.getLogger(SuperAdminService.class);
//
//    public SuperAdminService(SuperAdminRepository superAdminRepository) {
//        this.superAdminRepository = superAdminRepository;
//    }
//
//
//    /**
//     * Logs in the super admin
//     *
//     * @param email
//     * @param password
//     * @return
//     */
//    public ResponseEntity<String> loginAdmin(String email, String password) {
//        if (superAdminRepository.existsByEmailAndPasswordAndStatusTrue(email, password)) {
//            return ResponseEntity.ok().body(("Admin has been logged in..."));
//        } else {
//            return new ResponseEntity<>("Admin email or password maybe wrong.", HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//    /**
//     * @creationDate 28 October 2021
//     * @description This function gets all the superAdmins details in database.
//     * @throws Exception the exception
//     * @return Response Entity of type SuperAdmin
//     **/
//    public ResponseEntity<List<SuperAdmin>> getAllSuperAdmins() {
//        try {
//            Optional<List<SuperAdmin>> superAdmins = Optional
//                    .of(superAdminRepository.findAllByStatusTrueOrderByCreatedDateDesc());
//            if (superAdmins.isPresent()) {
//                LOG.info("SuperAdmins successfully Retrieved : " + superAdmins.get());
//                return ResponseEntity.ok().body(superAdmins.get());
//            } else {
//                LOG.info("SuperAdmins Not found in the database: " + superAdmins.get());
//                return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            LOG.info("Exception caught while retrieving SuperAdmins data : \n" + e.getMessage());
//            return new ResponseEntity("Error retrieving all superAdmins!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//    /**
//     * @creationDate 28 October 2021
//     * @description This function adds a superAdmin in database.
//     * @param superAdmin: A superAdmin object to be added
//     * @throws Exception the exception
//     * @return Response Entity of type SuperAdmin
//     **/
//    public ResponseEntity<SuperAdmin> addSuperAdmin(SuperAdmin superAdmin) {
//        try {
//            superAdmin.setCreatedDate(DateTimeUtil.getDate());
//            superAdmin.setStatus(true);
//            superAdminRepository.save(superAdmin);
//            LOG.info("SuperAdmins successfully added to the database: " + superAdmin);
//            return ResponseEntity.ok().body(superAdmin);
//        } catch (PropertyValueException e) {
//            LOG.info("The syntax of the superAdmin object is invalid : " + superAdmin + e.getMessage());
//            return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        } catch (ConstraintViolationException e) {
//            // TODO: handle exception
//            LOG.info("Error.... Duplicate entry for a unique value!! : " + superAdmin + "\n" + e.getMessage());
//            return new ResponseEntity("Error adding a patient into database!\n" + e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            // TODO: handle exception
//            LOG.info("Error while saving the superAdmin object to database  : " + superAdmin + e.getMessage());
//            return new ResponseEntity("Error adding a copmany into database!\n" + e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * @creationDate 28 October 2021
//     * @description This function updates a superAdmin in database.
//     * @param superAdmin: A superAdmin object to be updated
//     * @throws Exception the exception
//     * @return Response Entity of type SuperAdmin
//     **/
//    public ResponseEntity<SuperAdmin> updateSuperAdmin(SuperAdmin superAdmin) {
//        try {
//            Optional<SuperAdmin> exists = superAdminRepository.findById(superAdmin.getId());
//            if (exists.isPresent()) {
//                superAdmin.setUpdatedDate(DateTimeUtil.getDate());
//                superAdminRepository.save(superAdmin);
//                LOG.info("SuperAdmins successfully updated in the database: " + superAdmin);
//                return ResponseEntity.ok().body(superAdmin);
//            } else {
//                LOG.info("SuperAdmin could not be updated because the compnay id could not be found  : ");
//                return new ResponseEntity("Compnay of this id does not exist. Please update a existing record!",
//                        HttpStatus.ACCEPTED);
//            }
//        } catch (PropertyValueException e) {
//            LOG.info("The syntax of the superAdmin object is invalid : " + superAdmin + e.getMessage());
//            return new ResponseEntity("Please send a valid object to update from the databse!\n" + e.getMessage(),
//                    HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            // TODO: handle exception
//            LOG.info("Error while saving the superAdmin object to database  : " + superAdmin + e.getMessage());
//            return new ResponseEntity("Error updating copmany!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * @creationDate 28 October 2021
//     * @description This function deletes a superAdmin in database by changing its
//     *              status to false.
//     * @param superAdmins: List of superAdmins to be deleted
//     * @throws Exception the exception
//     * @return Response Entity of type String
//     **/
//    public ResponseEntity<String> deleteSuperAdmin(List<SuperAdmin> superAdmins) {
//        try {
//            for (SuperAdmin superAdmin : superAdmins) {
//                if (Objects.isNull(superAdmin.getId()))
//                    return new ResponseEntity("Please provide the ID of superAdmin, having Id : " + superAdmin.getId(),
//                            HttpStatus.PARTIAL_CONTENT);
//                superAdmin.setStatus(false);
//                superAdminRepository.save(superAdmin);
//            }
//            LOG.info("Compnaies deleted successfully bu turning their status to false!");
//            return ResponseEntity.ok().body("SuperAdmins successfully deleted");
//        } catch (Exception e) {
//            // TODO: handle exception
//            LOG.info("Error while deleting the superAdmin object from database  : " + e.getMessage());
//            return new ResponseEntity("Error while deleting superAdmins!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//}
