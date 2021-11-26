package com.example.EGovt_CovidHealthApp.Controller;

import com.example.EGovt_CovidHealthApp.Model.Entity.Role;
import com.example.EGovt_CovidHealthApp.Service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

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

    /**
     * Instantiates a new Role controller. Initialized the constructor instead of  annotation.
     *
     * @param roleService the role service
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Gets all roles from the database.
     *
     * @return the all roles
     * @throws Exception the exception
     */
    @GetMapping("")
    public ResponseEntity<List<Role>> getAllRoles() throws Exception {

        return roleService.getAllRoles();
    }


    /**
     * Create role and adds it into the databse.
     *
     * @param role: The role to be added
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/addRole")
    public ResponseEntity<Role> createRole(@Validated @RequestBody Role role) throws Exception {
        return roleService.addRole(role);
    }

    /**
     * Deletes a role from the database.
     *
     * @param id the id
     * @return the response entity
     * @throws Exception the exception
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteRole(
            @PathVariable Long id) throws Exception {


        return roleService.deleteRole(id);
    }

    /**
     * Update role in the database.
     *
     * @param role the role
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/updateUserInfo")
    public ResponseEntity<Role> updateRole(
            @Validated @RequestBody Role role) throws Exception {

        return roleService.updateRole(role);
    }

}