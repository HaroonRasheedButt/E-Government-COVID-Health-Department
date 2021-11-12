package com.example.EGovt_CovidHealthApp.Model.Interface;

import java.util.List;
import java.util.Date;
import org.springframework.http.HttpStatus;

public class DetailedCustomResponse {

	int statusCode;
	HttpStatus httpStatus;
	String message;
	List<String> details;
	String path;
	Date Date;

	public DetailedCustomResponse(int statusCode, HttpStatus httpStatus, String message, List<String> details,
			String path, Date Date) {
		super();
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
		this.message = message;
		this.details = details;
		this.path = path;
		this.Date = Date;
	}

	public DetailedCustomResponse(int statusCode, HttpStatus httpStatus, String message, String path, Date Date) {
		super();
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
		this.message = message;
		this.path = path;
		this.Date = Date;
	}

	public DetailedCustomResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

}
