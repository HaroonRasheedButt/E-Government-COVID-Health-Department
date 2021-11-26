package com.example.EGovt_CovidHealthApp.Model.Interface;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedCustomResponse {

    int statusCode;
    HttpStatus httpStatus;
    String message;
    String path;
    Date Date;
    Object data;

    public DetailedCustomResponse(int statusCode, HttpStatus httpStatus, String message,
                                  String path, Date Date, Object details) {
        super();
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
        this.Date = Date;
        this.data = details;
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


}
