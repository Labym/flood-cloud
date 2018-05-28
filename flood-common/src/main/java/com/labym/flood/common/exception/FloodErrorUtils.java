package com.labym.flood.common.exception;

import org.springframework.http.HttpStatus;

public final class FloodErrorUtils {

    private FloodErrorUtils() {
    }

    public static final FloodError badRequest(String error, String errorDescription, Object... params) {
        return new FloodError(HttpStatus.BAD_REQUEST, error, String.format(errorDescription, params));
    }

    public static final FloodError alreadyExists(String errorDescription, Object... params) {
        return new FloodError(HttpStatus.BAD_REQUEST, FloodErrorConstansts.ALREADY_EXIST, String.format(errorDescription, params));
    }

    public static final FloodError notExists(String errorDescription, Object... params) {
        return new FloodError(HttpStatus.BAD_REQUEST, FloodErrorConstansts.NOT_EXIST, String.format(errorDescription, params));
    }


}
