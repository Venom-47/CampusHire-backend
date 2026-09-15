package com.racker.CampusHire.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IneligibleStudentException extends RuntimeException {
    public IneligibleStudentException(String message) {
        super(message);
    }
}
