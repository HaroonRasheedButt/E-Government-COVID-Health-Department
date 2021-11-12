package com.example.EGovt_CovidHealthApp.Util;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.EGovt_CovidHealthApp.Model.Interface.DetailedCustomResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;

/**
 * The GLoball Exception handling Class to handle all the request.
 * 
 * @author Haroon Rasheed
 * @createdDate November 10, 2021
 */
@ControllerAdvice
public class ExceptionHandling {

	/**
	 * Returns the detialed custom response upon Exception
	 * 
	 * @param HttpServletRequest: req
	 * @param Exception           ex
	 * @return DetailedCustomeResponse
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ javax.validation.ConstraintViolationException.class, InvalidFormatException.class,
			HttpMessageNotReadableException.class, MissingRequestHeaderException.class,
			MissingPathVariableException.class, HttpRequestMethodNotSupportedException.class,
			UnexpectedTypeException.class, MethodArgumentNotValidException.class, DataIntegrityViolationException.class,
			RuntimeException.class, ConstraintViolationException.class, DataException.class, HttpClientErrorException.class})
	public @ResponseBody Object handleBadRequestException(HttpServletRequest req, Exception ex) {

		if(ex instanceof HttpClientErrorException){
			String msg = "Authorization Failed Error\n\n";
			return new DetailedCustomResponse(401, HttpStatus.UNAUTHORIZED, msg+ex.getMessage(), req.getRequestURI(),
					DateTimeUtil.getDate());
		}

		return new DetailedCustomResponse(400, HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI(),
				DateTimeUtil.getDate());
	}
}
