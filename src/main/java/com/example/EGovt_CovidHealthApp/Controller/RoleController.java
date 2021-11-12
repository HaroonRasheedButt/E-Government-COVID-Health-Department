package com.example.EGovt_CovidHealthApp.Controller;

import com.example.EGovt_CovidHealthApp.Model.Entity.Role;
import com.example.EGovt_CovidHealthApp.Service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;

/**
 * The type Role controller.
 *
 * @author Talha Farooq
 * @version 0.3
 * @description This class is Controller class which just takes the data from frontend and give data to frontend. Authorization is check in this class. Also This class contains servlets path. It contains api list all role, find role and delete role.
 * @createdTime 11 October 2021
 */
@EnableSwagger2
@RestController
@RequestMapping("/role")
public class RoleController {

    private static final Logger LOG = LogManager.getLogger(RoleController.class);
    private final RoleService roleService;
    //an ID that is used to authenticate the request header authentication token
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    /**
     * Instantiates a new Role controller. Initialized the constructor instead of  annotation.
     *
     * @param roleService the role service
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * authorizes the authentication header in the request header
     *
     * @param authToken the authentication token to authenticate
     * @throws Exception
     */
    private void authorized(Optional<String> authToken) throws Exception {
        if (!authToken.isPresent()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        if (!authToken.get().equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Gets all roles from the database.
     *
     * @param authToken the auth token
     * @return the all roles
     * @throws Exception the exception
     */
    @GetMapping("")
    public ResponseEntity<List<Role>> getAllRoles(@RequestHeader("Authorization") Optional<String> authToken) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return roleService.getAllRoles();
    }


    /**
     * Create role and adds it into the databse.
     *
     * @param authToken  the auth token
     * @param roles the roles
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/addRole")
    public ResponseEntity<List<Role>> createRole(@RequestHeader("Authorization") Optional<String> authToken,
                                                         @Validated @RequestBody List<Role> roles) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return roleService.addRole(roles);
    }

    /**
     * Deletes a role from the database.
     *
     * @param authToken the auth token
     * @param id        the id
     * @return the response entity
     * @throws Exception the exception
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteRole(@RequestHeader("Authorization") Optional<String> authToken,
                                                 @PathVariable Long id) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return roleService.deleteRole(id);
    }

    /**
     * Update role in the database.
     *
     * @param authToken the auth token
     * @param role      the role
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/updateUserInfo")
    public ResponseEntity<Role> updateRole(@RequestHeader("Authorization") Optional<String> authToken,
                                                   @Validated @RequestBody Role role) throws Exception {
        try{
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return roleService.updateRole(role);
    }

}