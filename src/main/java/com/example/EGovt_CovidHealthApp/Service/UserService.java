package com.example.EGovt_CovidHealthApp.Service;

import com.example.EGovt_CovidHealthApp.Model.Entity.Token;
import com.example.EGovt_CovidHealthApp.Model.Entity.User;
import com.example.EGovt_CovidHealthApp.Model.Interface.DetailedCustomResponse;
import com.example.EGovt_CovidHealthApp.Repostiory.TokenRepository;
import com.example.EGovt_CovidHealthApp.Repostiory.UserRepository;
import com.example.EGovt_CovidHealthApp.Util.DateTimeUtil;
import com.example.EGovt_CovidHealthApp.Util.MailUtil;
import com.example.EGovt_CovidHealthApp.Util.SmsUtil;
import com.example.EGovt_CovidHealthApp.Util.TokenGenerationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOG = LogManager.getLogger(UserService.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PatientReportService patientReportService;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), getAuthority(user.get()));
    }

    /**
     * Fetching the roles of a user so that can give him the authorities for the apis
     *
     * @param user
     * @return
     */
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }


    /**
     * @return Response Entity of List of Users
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function gets all the users details from the database.
     **/
    public ResponseEntity<DetailedCustomResponse> getAllUsers(HttpServletRequest req) {
        try {
            List<User> users = userRepository.findAllByStatusTrueOrderByCreatedDateDesc();
            if (!users.isEmpty()) {
                LOG.info("Users successfully Retrieved : " + users);
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Users Found!", req.getRequestURI(), DateTimeUtil.getDate(), users));
            } else {
                LOG.info("Users Not found in the database: " + users);
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "Users Not Found!", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Exception caught while retrieving Users data : \n" + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error retrieving all users!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
//            return new ResponseEntity("Error retrieving all users!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param user: A user object to be added
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 29 October 2021
     * @description This function adds a user in database.
     **/
    public ResponseEntity<DetailedCustomResponse> addUser(HttpServletRequest req, User user) {
        Token token = new Token();
        MailUtil mailUtil = new MailUtil(javaMailSender);

        try {
            user.setCreatedDate(DateTimeUtil.getDate());
            user.setStatus(false);
            user.setAlive(true);
            user.setPassword(bcryptEncoder.encode(user.getPassword()));

            token.setSmsToken(TokenGenerationUtil.generateToken());
            SmsUtil.sendSms(user.getContactNum(), token.getSmsToken());

            token.setEmailToken(TokenGenerationUtil.generateToken());
            mailUtil.sendEmail(user.getEmail(), token.getEmailToken());

            // Saved the user at this stage so that we can get the automatically
            // incrementing user ID from the database to set into token table
            user = userRepository.save(user);
            LOG.info("User successfully saved into the database : " + user);

            token.setCreatedDate(DateTimeUtil.getTimestamp());
            token.setUserType(this.getClass().getName());
            token.setUserId(user.getId());

            tokenRepository.save(token);
            LOG.info("Token successfully saved into the database : " + token);

            return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User added to the database!", req.getRequestURI(), DateTimeUtil.getDate(), user));
        } catch (PropertyValueException e) {

            LOG.info("The syntax of the user object is invalid. Some null properties can not be added to database "
                    + user);
//            return new ResponseEntity("Please send a valid object to add into the databse!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Please send a valid object to add into the databse!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        } catch (DataIntegrityViolationException e) {
            // TODO: handle exception
            LOG.info("Error.... Duplicate entry for a unique value!! : " + user + "\n" + e.getMessage());
//            return new ResponseEntity("Error adding a user into database!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Data Integrity Violation while saving the object in Database!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error generated while saving the user into the database : " + user + "\n" + e.getMessage());
//            return new ResponseEntity("Error adding a user into database!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error generated while saving the user into the database : \n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * @param userId: the id od user
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 1st November 2021
     * @description This function gets a user based on an id from database.
     **/
    public ResponseEntity<DetailedCustomResponse> getUserById(HttpServletRequest req, long userId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                LOG.info("User found by id.");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User Found!", req.getRequestURI(), DateTimeUtil.getDate(), user));
            } else {
                LOG.info("User not found");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "User Not Found!", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Exception caught in getting a user by id.");
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error retrieving a user by id!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param user: A user object to be added
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 28 October 2021
     * @description This function updates a user in database.
     **/
    public ResponseEntity<DetailedCustomResponse> updateUser(HttpServletRequest req, User user) throws Exception {
        try {
            Optional<User> exists = userRepository.findById(user.getId());
            if (exists.isPresent()) {
                user.setUpdatedDate(DateTimeUtil.getDate());
                userRepository.save(user);
                LOG.info("User successfully updated in the database: " + user);
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User updated int the database!", req.getRequestURI(), DateTimeUtil.getDate(), user));
            } else {
                LOG.info("User could not be updated because the User id could not be found  : ");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "User could not be updated because the User id could not be found.", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }

        } catch (PropertyValueException e) {
            LOG.info("The syntax of the user object is invalid : " + user + e.getMessage());
//            return new ResponseEntity("Please send a valid object to update from the database!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "The syntax of the user object is invalid \n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            // TODO: handle exception
            LOG.info("Constraint Violation exception caught while updating the user object in database  : " + user + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Constraint Violation exception (DataIntegrity) caught while updating the user object in database  : \n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error while saving the user object to database  : " + user + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Error while saving the user object to database. \n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return String which is a confirmation if the user is verified or not.
     * @author Haroon Rasheed
     * @version 1.5
     * @description This function takes in the SMS token and email token from the
     * user to verify
     * @creationDate 13 October 2021
     */
    public ResponseEntity<DetailedCustomResponse> verifyUser(HttpServletRequest req, long userId, String smsToken, String emailToken) {
        try {
            Optional<Token> token = Optional
                    .ofNullable(tokenRepository.findByUserIdAndSmsTokenAndEmailToken(userId, smsToken, emailToken));
            if (token.isPresent()) {
                LOG.info("SMS and Email token are correct !\n ");
                java.util.Date userVerifyingDate = DateTimeUtil.getDate();
                token.get().setverifyingDate(userVerifyingDate);

                long creatingMins = token.get().getCreatedDate().getMinutes();
                long verifyingMins = token.get().getverifyingDate().getMinutes();

                if (verifyingMins - creatingMins > TokenGenerationUtil.allowedDelayMinutes) {
                    LOG.info("SMS and Email verifying time has been expired... !\n ");
                    // deleting the user saved in database so that a user with same details
                    // doesn't get added again when it registers again.
                    userRepository.deleteById(userId);
                    tokenRepository.deleteById(token.get().getId());
                    // 422 Unprocessable Entity response status code indicates that the server
                    // understands the content type of the request entity, and the syntax of the
                    // request entity is correct, but it was unable to process the contained
                    // instructions.
                    return ResponseEntity.unprocessableEntity().body(
                            new DetailedCustomResponse(205, HttpStatus.RESET_CONTENT, "Your verifying OTP code has been expired. Please again add a user to obtain an OTP.", req.getRequestURI(), DateTimeUtil.getDate(), null));
                    // RESET_CONTENT: Tells the user agent to reset the document which sent this
                    // request.
                } else {
                    Optional<User> user = userRepository.findById(userId);
                    if (user.isPresent()) {
                        user.get().setStatus(true);
                        userRepository.save(user.get());
                        LOG.info("User has been verified and saved to database. !");
                        return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User has been verified and saved to the database!", req.getRequestURI(), DateTimeUtil.getDate(), user));
                    } else {
                        LOG.info("The user of this id does not exist!");
                        return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "The user of this id does not exist!", req.getRequestURI(), DateTimeUtil.getDate(), null));

                    }
                }
            } else {
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "Invalid OTP. The OTP you entered is wrong.", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Unable to verify user\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * @param userCnic
     * @return Response Entity of type User
     * @throws Exception the exception
     * @creationDate 1st November 2021
     * @description This function gets a user based on their CNIC from database.
     **/
    public ResponseEntity<DetailedCustomResponse> getUserByCnic(HttpServletRequest req, String userCnic) {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.findByCnicAndStatusTrue(userCnic));
            if (user.isPresent()) {
                LOG.info("User found by cnic.");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User found by Cnic!", req.getRequestURI(), DateTimeUtil.getDate(), user));
            } else {
                LOG.info("User not found by the cnic");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User Could not be found by the given Cnic!", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Exception caught in get all user. Unable to get user by cnic.");
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Exception caught. Unable to get user by cnic.!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userCnic
     * @return boolean: user status
     * @throws Exception the exception
     * @creationDate 1st November 2021
     * @description This function checks if a user has COVID based on their CNIC
     * from database.
     **/
    public ResponseEntity<DetailedCustomResponse> checkUserCovidStatus(HttpServletRequest req, String userCnic) throws Exception {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.findByCnicAndStatusTrue(userCnic));
            if (user.isPresent()) {
                LOG.info("User found by cnic.");
                if (user.get().isCovid())
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User is covid positive", req.getRequestURI(), DateTimeUtil.getDate(), true));
                else
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User is covid negative", req.getRequestURI(), DateTimeUtil.getDate(), false));
            } else {
                LOG.info("User not found");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "User not found in this database", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Exception caught while finding a user.");
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Exception caught while finding a user.\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * @param userCnic
     * @return boolean: user status
     * @throws Exception the exception
     * @creationDate 1st November 2021
     * @description This function checks if a user has been vaccinated based on
     * their CNIC from database.
     **/
    public ResponseEntity<DetailedCustomResponse> checkUserVaccinationStatus(HttpServletRequest req, String userCnic) throws Exception {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.findByCnicAndStatusTrue(userCnic));
            if (user.isPresent()) {
                LOG.info("User found by cnic.");
                if (user.get().isVaccinated())
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User is Vaccinated", req.getRequestURI(), DateTimeUtil.getDate(), true));
                else
                    return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "User is Not Vaccinated", req.getRequestURI(), DateTimeUtil.getDate(), false));
            } else {
                LOG.info("User not found");
                return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.NOT_FOUND, "User not found in database", req.getRequestURI(), DateTimeUtil.getDate(), null));
            }
        } catch (Exception e) {
            LOG.info("Exception caught in finding a user. Unable to find user by their CNIC.");
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Exception caught while checking user Vaccination status. PLease try again later!\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * @param users : The id of the the user to be deleted
     * @return Response Entity of type String
     * @throws Exception the exception
     * @creationDate 31st October 2021
     * @description This function deletes a user in database by changing its
     * status to false.
     **/
    public ResponseEntity<DetailedCustomResponse> deleteUser(HttpServletRequest req, List<User> users) {
        try {

            for (User user : users) {
                if (Objects.isNull(user.getId()))
                    return new ResponseEntity(new DetailedCustomResponse(206, HttpStatus.PARTIAL_CONTENT, "Please provide the ID of user, having email : " + user.getEmail(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.PARTIAL_CONTENT);
                user.setStatus(false);
                userRepository.save(user);
            }
            LOG.info("Users deleted successfully by changing their status to false!");
            return ResponseEntity.ok(new DetailedCustomResponse(200, HttpStatus.OK, "Users deleted successfully by changing their status to false!", req.getRequestURI(), DateTimeUtil.getDate(), users));
        } catch (Exception e) {
            // TODO: handle exception
            LOG.info("Error while deleting the user objects from database  : " + e.getMessage());
            return new ResponseEntity(new DetailedCustomResponse(404, HttpStatus.BAD_REQUEST, "Exception caught while deleting the users from database.\n" + e.getMessage(), req.getRequestURI(), DateTimeUtil.getDate(), null), HttpStatus.BAD_REQUEST);
        }
    }


}
