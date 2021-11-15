package com.example.EGovt_CovidHealthApp.Service;


import com.example.EGovt_CovidHealthApp.Model.Entity.Role;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;
import com.example.EGovt_CovidHealthApp.Repostiory.RoleRepository;
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
public class RoleService {
    private static final Logger LOG = LogManager.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    //Initialized the constructor instead of autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * @return ResponseEntity which is a role list. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function retrieves all the Categories which are saved in database. The data from database
     * comes in list so rolelist.
     * @creationDate 12 October 2021
     */
    public ResponseEntity<List<Role>> getAllRoles() {
        try{
            Optional<List<Role>> roles = Optional.of(roleRepository.findAll());
            if (roles.isPresent()) {
                return ResponseEntity.ok().body(roles.get());
            } else {
                LOG.info("Chats returned as empty in getAllChats() : " + roles);
                return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity("Error retrieving all users!\n"+ e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    /**
     * @return A responseEntity with all the list of roles and an Http Status
     * @author Haroon Rahseed
     * @version 1.5
     * @description Save Role into database by getting values from controller
     * @creationDate 12 October 2021
     */
    public ResponseEntity<Role> addRole(Role role) {
        try {
            role.setCreatedDate(DateTimeUtil.getDate());
            role.setStatus(true);
            role = roleRepository.save(role);
            return ResponseEntity.ok().body(role);
        }catch (HttpMessageNotReadableException e){
            return new ResponseEntity("Please Provide a valid input format to Save a Role!\n"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity("Unable to Add Chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * @return ResponseEntity having Http status code a response body with particular message
     * @author Haroon Rasheed
     * @description Deletes Role from db
     * @creationDate 12 October 2021
     */
    public ResponseEntity<Object> deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
            return new ResponseEntity("Role Deleted!\n", HttpStatus.OK);
        } catch (DataAccessException e){
            return new ResponseEntity("The Role you want to delete does not exist!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity("Unable to delete Role!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @return ResponseEntity having Http status code a response body
     * @author Haroon Rasheed
     * @description Updates Role from database
     * @creationDate 12 October 2021
     */
    public ResponseEntity<Role> updateRole(Role role) {
        try {
            role.setUpdatedDate(DateTimeUtil.getDate());
            Role roleObj = roleRepository.save(role);
            return ResponseEntity.ok().body(roleObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update Role\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}