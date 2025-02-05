package com.employee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmployeeNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public EmployeeNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

    public EmployeeNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
