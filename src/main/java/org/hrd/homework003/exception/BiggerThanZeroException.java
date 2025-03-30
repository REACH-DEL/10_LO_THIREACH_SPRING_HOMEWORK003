package org.hrd.homework003.exception;

import lombok.Data;

import java.util.Map;


public class BiggerThanZeroException extends RuntimeException {
    private final Map<String, String> errors;
    public BiggerThanZeroException(Map<String, String> errors) {
        this.errors = errors;
    }
    public Map<String, String> getExceptionDetails() {
        return errors;
    }
}
