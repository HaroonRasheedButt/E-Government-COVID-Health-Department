package com.example.EGovt_CovidHealthApp.Controller;

import com.example.EGovt_CovidHealthApp.Model.Entity.User;
import com.example.EGovt_CovidHealthApp.Model.Interface.DetailedCustomResponse;
import com.example.EGovt_CovidHealthApp.Model.Interface.JwtRequest;
import com.example.EGovt_CovidHealthApp.Model.Interface.JwtResponse;
import com.example.EGovt_CovidHealthApp.Security.JwtTokenUtil;
import com.example.EGovt_CovidHealthApp.Service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Haroon Rasheed
 * @version 1.1
 */
@EnableSwagger2
@RestController
@Validated
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LogManager.getLogger(UserController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;


    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * @return String object telling whether the user has been verified or not
     * @ the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    /**
     * @return list of users
     * @ the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @GetMapping("/getAllUsers")
    public ResponseEntity<DetailedCustomResponse> getAllUsers(HttpServletRequest req) {
        return userService.getAllUsers(req);
    }

    /**
     * @return list of users
     * @ the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @GetMapping("/getUserById")
    public ResponseEntity<DetailedCustomResponse> getUserById(
            HttpServletRequest req,
            @RequestHeader("userId") long userId) {

        return userService.getUserById(req, userId);
    }

    /**
     * @return list of users
     * @ the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @GetMapping("/getUserByCnic")
    public ResponseEntity<DetailedCustomResponse> getUserByCnic(HttpServletRequest req,
                                                                @RequestHeader("userCnic") String userCnic) {

        return userService.getUserByCnic(req, userCnic);
    }

    /**
     * @param user: A user object to be added
     * @return Response Entity of type User
     * @ the exception
     * @creationDate 29 October 2021
     * @description This function adds a user in database.
     **/
    @PostMapping("/addUser")
    public ResponseEntity<DetailedCustomResponse> addUser(HttpServletRequest req, @Valid @RequestBody User user) {
        return userService.addUser(req, user);
    }

    /**
     * Verify user response entity.
     *
     * @param userId     the user id
     * @param smsToken   the SMS token
     * @param emailToken the email token
     * @return the response entity
     * @ the exception
     */
    @GetMapping("/verifyUser")
    public ResponseEntity<DetailedCustomResponse> verifyUser(HttpServletRequest req,
                                             @RequestHeader long userId, @RequestHeader String smsToken, @RequestHeader String emailToken) {

        return userService.verifyUser(req, userId, smsToken, emailToken);
    }

    /**
     * @param users: The list of users to be deleted
     * @return Response Entity of type String
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function deletes a user in database.
     **/
    @DeleteMapping("/deleteUser")
    public ResponseEntity<DetailedCustomResponse> deleteUser(HttpServletRequest req,
                                             @Valid @RequestBody List<User> users) {


        return userService.deleteUser(req, users);
    }

    /**
     * @param user: A user object to be added
     * @return Response Entity of type User
     * @ the exception
     * @creationDate 28 October 2021
     * @description This function updates a user in database.
     **/
    @PostMapping("/updateUser")
    public ResponseEntity<DetailedCustomResponse> updateUser(HttpServletRequest req,
                                                             @Valid @RequestBody User user) throws Exception {


        return userService.updateUser(req, user);
    }

    /**
     * @param userCnic: the user's CNIC
     * @return boolean: user status
     * @ the exception
     * @creationDate 29 October 2021
     * @description This function checks if a user has COVID based on their CNIC
     * from database.
     **/
    @GetMapping("/covid/status")
    public ResponseEntity<DetailedCustomResponse> checkUserCovidStatus(HttpServletRequest req,
                                                        @RequestHeader("userCnic") String userCnic) throws Exception {

        return userService.checkUserCovidStatus(req, userCnic);
    }

    /**
     * @param userCnic: the user's CNIC
     * @return boolean: user status
     * @ the exception
     * @creationDate 29 October 2021
     * @description This function checks if a user has COVID based on their CNIC
     * from database.
     **/
    @GetMapping("/vaccine/status")
    public ResponseEntity<DetailedCustomResponse> checkUserVaccinationStatus(HttpServletRequest req,
                                                              @RequestHeader("userCnic") String userCnic) throws Exception {

        return userService.checkUserVaccinationStatus(req, userCnic);
    }

}