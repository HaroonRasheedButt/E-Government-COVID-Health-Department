package com.example.EGovt_CovidHealthApp.Model.Pojo;

import lombok.Data;

@Data
public class NoRecordFoundException extends RuntimeException {
    String msg;
    public NoRecordFoundException(String message) {
        super();
        this.msg = message;
    }
}