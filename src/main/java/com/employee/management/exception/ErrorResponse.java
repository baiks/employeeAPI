package com.employee.management.exception;

import lombok.Value;


/**
 * This class holds a list of {@code ErrorModel} that describe the error raised on rejected validation
 */

@Value
public class ErrorResponse {
    String code;
    String message;

}
