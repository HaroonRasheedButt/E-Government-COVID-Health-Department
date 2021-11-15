package com.example.EGovt_CovidHealthApp.Util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class AuthorizationUtil {
	
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";
    /**
     * Authorizes the token
     * 
     * @param authToken
     * @throws Exception
     */
    public static void authorized(String authToken) throws Exception {
    	if (!authToken.equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

}
