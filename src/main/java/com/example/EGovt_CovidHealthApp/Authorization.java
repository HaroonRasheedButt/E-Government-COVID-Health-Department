package com.example.EGovt_CovidHealthApp;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class Authorization {
	
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";
    public static void authorized(Optional<String> authToken) throws Exception {
    	if (!authToken.isPresent()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    	if (!authToken.get().equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

}
