package com.example.EGovt_CovidHealthApp.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.Permission;
import com.example.EGovt_CovidHealthApp.Repostiory.PermissionRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    private static final Logger LOG = LogManager.getLogger(PermissionService.class);
    private final PermissionRepository permissionRepository;

    //Initialized the constructor instead of autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * @return ResponseEntity which is a permission list. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function retrieves all the Categories which are saved in database. The data from database
     * comes in list so permissionlist.
     * @creationDate 12 October 2021
     */
    public ResponseEntity<List<Permission>> getAllPermissions() {
        try{
            Optional<List<Permission>> categories = Optional.of(permissionRepository.findAll());
            if (categories.isPresent()) {
                return ResponseEntity.ok().body(categories.get());
            } else {
                LOG.info("Chats returned as empty in getAllChats() : " + categories);
                return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity("Error retrieving all users!\n"+ e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    /**
     * @return A responseEntity with all the list of categories and an Http Status
     * @author Haroon Rahseed
     * @version 1.5
     * @description Save Permission into database by getting values from controller
     * @creationDate 12 October 2021
     */
    public ResponseEntity<List<Permission>> addPermission(List<Permission> categories) {
        try {
            for(Permission permission: categories){
                permission.setCreatedDate(DateTimeUtil.getDate());
                permissionRepository.save(permission);
            }
            return ResponseEntity.ok().body(categories);
        }catch (HttpMessageNotReadableException e){
            return new ResponseEntity("Please Provide a valid input format to Save a Permission!\n"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity("Unable to Add Chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * @return ResponseEntity having Http status code a response body with particular message
     * @author Haroon Rasheed
     * @description Deletes Permission from db
     * @creationDate 12 October 2021
     */
    public ResponseEntity<Object> deletePermission(Long id) {
        try {
            permissionRepository.deleteById(id);
            return new ResponseEntity("Permission Deleted!\n", HttpStatus.OK);
        } catch (DataAccessException e){
            return new ResponseEntity("The Permission you want to delete does not exist!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity("Unable to delete Permission!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @return ResponseEntity having Http status code a response body
     * @author Haroon Rasheed
     * @description Updates Permission from database
     * @creationDate 12 October 2021
     */
    public ResponseEntity<Permission> updatePermission(Permission permission) {
        try {
            permission.setUpdatedDate(DateTimeUtil.getDate());
            Permission permissionObj = permissionRepository.save(permission);
            return ResponseEntity.ok().body(permissionObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update Permission\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
