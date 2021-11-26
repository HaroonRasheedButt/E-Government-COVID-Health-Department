package com.example.EGovt_CovidHealthApp.Controller;

import com.example.EGovt_CovidHealthApp.Model.Entity.Permission;
import com.example.EGovt_CovidHealthApp.Service.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * The type Permission controller.
 *
 * @author Haroon Rasheed
 * @version 1.6
 * @description This class is Controller class which just takes the data from frontend and give data to frontend. Authorization is check in this class. Also This class contains servlets path. It contains api list all privilege, find privilege and delete privilege.
 * @createdTime 12 October 2021
 */
@EnableSwagger2
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private static final Logger LOG = LogManager.getLogger(PermissionController.class);
    private final PermissionService permissionService;

    /**
     * Instantiates a new Permission controller. Initialized the constructor instead of  annotation.
     * @param permissionService the permission service
     */
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    /**
     * Gets all permissions.
     * @return the all permissions
     * @ the exception
     */
    @GetMapping("")
    public ResponseEntity<List<Permission>> getAllPermissions() {


        return permissionService.getAllPermissions();
    }

    /**
     * Create permission response entity.
     * @param permissions the permissions
     * @return the response entity
     * @ the exception
     */
    @PostMapping("/addPermission")
    public ResponseEntity<List<Permission>> createPermission(
            @Validated @RequestBody List<Permission> permissions) {


        return permissionService.addPermission(permissions);
    }

    /**
     * Delete permission from the database.
     * @param id the id
     * @return the response entity
     * @ the exception
     */
    @DeleteMapping("/deletePermission/{id}")
    public ResponseEntity<Object> deletePermission(
            @PathVariable Long id) {


        return permissionService.deletePermission(id);
    }

    /**
     * Updates permission from database.
     * @param permission the permission
     * @return the response entity
     * @ the exception
     */
    @PostMapping("/updatePermission")
    public ResponseEntity<Permission> updatePermission(
            @Validated @RequestBody Permission permission) {


        return permissionService.updatePermission(permission);
    }

}

