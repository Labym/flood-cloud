package com.labym.flood.iam.exception;

import com.labym.flood.common.exception.FloodError;
import com.labym.flood.common.exception.FloodException;

public class UserAlreadyExistException extends FloodException {


    public UserAlreadyExistException(FloodError error) {
        super(error);
    }

    public UserAlreadyExistException(FloodError error, Throwable cause) {
        super(error, cause);
    }

    public UserAlreadyExistException(FloodError error, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(error, cause, enableSuppression, writableStackTrace);
    }
}
