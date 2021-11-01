package com.example.EGovt_CovidHealthApp.Util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.twilio.Twilio;
import com.twilio.exception.AuthenticationException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsUtil {
    private static final String ACCOUNT_SID = "AC81d02d144295cdbec7e24bd18e361909";
    private static final String AUTH_TOKEN = "366746fa03de81a25ea921190cb77f57";
    private static final String fromNumber = "+19803006396";
    


//    public static String sendSms(String toNumber, String message) {
//        try {
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), message).create();
//            return ("Successfully sent");
//        }
//        catch (AuthenticationException e) {
//            return("Authentication error while sending message to the contact number! \n" + e.getMessage());
//        }
//        catch (Exception e) {
//            return ("Unable to send sms\n" + e.getMessage());
//        }
//    }
//    
    
    /**
     * @description Sends an SMS string to user which is a token to verify the user.
     * @creationDate 29 October 2021
     * @param toNumber the to number
     * @param message  the message
     * @return the string
     */
    public static ResponseEntity<String> sendSms(String contactNumber, String smsToken) {
        String message = "Your SmS Verification token for user registration is:\n" + smsToken;
        try {
            String toNumber = contactNumber;
            String response;
            
            try {
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), message).create();
                response =  "Successfully sent";
            }
            catch (AuthenticationException e) {
            	 response =  ("Authentication error while sending message to the contact number! \n" + e.getMessage());
            }
            
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return new ResponseEntity("Unable to send sms\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
