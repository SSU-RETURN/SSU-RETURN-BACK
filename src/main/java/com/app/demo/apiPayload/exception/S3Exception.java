package com.app.demo.apiPayload.exception;

import com.app.demo.apiPayload.code.BaseErrorCode;

public class S3Exception extends GeneralException {

    public S3Exception(BaseErrorCode code) {
        super(code);
    }
}