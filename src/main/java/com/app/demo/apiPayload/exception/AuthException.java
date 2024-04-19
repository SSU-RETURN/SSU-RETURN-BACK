package com.app.demo.apiPayload.exception;
import com.app.demo.apiPayload.code.BaseErrorCode;

public class AuthException extends GeneralException {

    public AuthException(BaseErrorCode code) {
        super(code);
    }
}