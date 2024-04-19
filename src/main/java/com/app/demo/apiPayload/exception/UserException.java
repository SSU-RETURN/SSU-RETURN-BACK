package com.app.demo.apiPayload.exception;


import com.app.demo.apiPayload.code.BaseErrorCode;

public class UserException extends GeneralException {

    public UserException(BaseErrorCode code) {
        super(code);
    }
}
