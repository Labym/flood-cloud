package com.labym.flood.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@ToString
public class FloodError {
    private HttpStatus status;
    private String error;
    private String errorDescription;
}
