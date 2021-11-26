package com.example.EGovt_CovidHealthApp.Util;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 
 * @author Haroon Rasheed
 *
 */
public class    MailUtil {
	private final JavaMailSender javaMailSender;

    /**
     * @creationDate 29 October 2021
     * @description Instantiates a new Mail util.
     * @param javaMailSender the java mail sender
     */
    public MailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * @creationDate 29 October 2021
     * @param recipientEmail the recipient email
     * @param emailMessage   the email message
     * @description This function returns the String upon successful or failed transaction of sms message.
     * @return String which is a phone number.
     */
    public ResponseEntity<String> sendEmail(String recipientEmail, String emailMessage) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipientEmail, recipientEmail);
        msg.setSubject("Verification Token for E-Health User Registration");
        msg.setText("\nYour verification token for E health Registration is : \n"+emailMessage);

        javaMailSender.send(msg);
        return ResponseEntity.ok().body("successfully sent");
    }
}
