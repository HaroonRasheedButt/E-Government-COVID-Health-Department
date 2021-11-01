package com.example.EGovt_CovidHealthApp.Util;

import java.util.Random;

public class TokenGenerationUtil {
	public final static int allowedDelayMinutes = 3;
	
	/**
	 * Generates an OTP token which is a 6 digit number!
	 * @return
	 */
	public static String generateToken() {
		// generating random 6-digit number for sms and email verification
        Integer n = 100000 + new Random().nextInt(900000);
        String token = n.toString();
        return token;
	}
	
	
}
