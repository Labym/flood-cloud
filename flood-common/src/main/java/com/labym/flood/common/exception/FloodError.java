package com.labym.flood.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class FloodError {
    private String error;
    private String errorDescription;
}
