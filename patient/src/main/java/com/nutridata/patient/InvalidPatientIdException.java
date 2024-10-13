package com.nutridata.patient;

public class InvalidPatientIdException extends RuntimeException {
    public InvalidPatientIdException(String message) {
        super(message);
    }
}
