package com.example.EGovt_CovidHealthApp.Controller;

import java.util.List;

import javax.validation.Valid;

import com.example.EGovt_CovidHealthApp.Model.Entity.User;
import com.example.EGovt_CovidHealthApp.Model.Interface.JwtRequest;
import com.example.EGovt_CovidHealthApp.Model.Interface.JwtResponse;
import com.example.EGovt_CovidHealthApp.Security.JwtTokenUtil;
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

import com.example.EGovt_CovidHealthApp.Util.AuthorizationUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.example.EGovt_CovidHealthApp.Service.UserService;

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

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private static final Logger LOG = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * @return String object telling whether the user has been verified or not
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

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
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers()

            throws Exception {
        return userService.getAllUsers();
    }

    /**
     * @param authToken: the authorization token
     * @return list of users
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @GetMapping("/getUserById")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String authToken,
                                            @RequestHeader("userId") long userId)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return userService.getUserById(userId);
    }

    /**
     * @param authToken: the authorization token
     * @return list of users
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function retrieves all the users which are saved in
     * database.
     **/
    @GetMapping("/getUserByCnic")
    public ResponseEntity<User> getUserByCnic(@RequestHeader("Authorization") String authToken,
                                              @RequestHeader("userCnic") String userCnic)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return userService.getUserByCnic(userCnic);
    }

    /**
     * @param authToken: the authorization token
     * @param user:   A user object to be added
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function adds a user in database.
     **/
    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestHeader("Authorization") String authToken,
                                        @Valid @RequestBody User user) throws Exception {
        AuthorizationUtil.authorized(authToken);
        return userService.addUser(user);
    }

    /**
     * Verify user response entity.
     *
     * @param authToken  the auth token
     * @param userId  the user id
     * @param smsToken   the SMS token
     * @param emailToken the email token
     * @return the response entity
     * @throws Exception the exception
     */
    @GetMapping("/verifyUser")
    public ResponseEntity<String> verifyUser(@RequestHeader("Authorization") String authToken,
                                             @RequestHeader long userId, @RequestHeader String smsToken, @RequestHeader String emailToken)
            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return userService.verifyUser(userId, smsToken, emailToken);
    }

    /**
     * @param authToken: the authorization token
     * @param users:  The list of users to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function deletes a user in database.
     **/
    @DeleteMapping("/user/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String authToken,
                                             @Valid @RequestBody List<User> users) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return userService.deleteUser(users);
    }

    /**
     * @param authToken: the authorization token
     * @param user:   A user object to be added
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a user in database.
     **/
    @PutMapping("/user/updateUser")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String authToken,
                                           @Valid @RequestBody User user) throws Exception {

        AuthorizationUtil.authorized(authToken);
        return userService.updateUser(user);
    }

    /**
     * @param authToken:   the authorization token
     * @param userCnic: the user's CNIC
     * @return boolean: user status
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function checks if a user has COVID based on their CNIC
     * from database.
     **/
    @GetMapping("/covid/status")
    public ResponseEntity<Boolean> checkUserCovidStatus(@RequestHeader("Authorization") String authToken,
                                                        @RequestHeader("userCnic") String userCnic)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return userService.checkUserCovidStatus(userCnic);
    }

    /**
     * @param authToken    String: the authorization token
     * @param userCnic: the user's CNIC
     * @return boolean: user status
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function checks if a user has COVID based on their CNIC
     * from database.
     **/
    @GetMapping("/vaccine/status")
    public ResponseEntity<Boolean> checkUserVaccinationStatus(
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("userCnic") String userCnic)

            throws Exception {
        AuthorizationUtil.authorized(authToken);
        return userService.checkUserVaccinationStatus(userCnic);
    }

}