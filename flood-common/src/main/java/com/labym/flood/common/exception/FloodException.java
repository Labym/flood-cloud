package com.labym.flood.common.exception;

import lombok.Getter;


public class FloodException extends RuntimeException {

    @Getter
    private FloodError error;

    public FloodException(FloodError error) {
        super(error.getErrorDescription());
        this.error = error;
    }

    public FloodException( FloodError error, Throwable cause) {
        super(error.getErrorDescription(), cause);
        this.error = error;
    }


    public FloodException(FloodError error, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(error.getErrorDescription(), cause, enableSuppression, writableStackTrace);
        this.error = error;
    }
}
