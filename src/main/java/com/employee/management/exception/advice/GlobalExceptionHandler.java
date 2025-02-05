package com.employee.management.exception.advice;

import com.employee.management.exception.CustomException;
import com.employee.management.exception.ErrorResponse;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends MessageSourceAdviceCtrl {

    protected GlobalExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleExceptionDataIntegrityViolationException(
            DataIntegrityViolationException e) {
//        log.info("Exception Cause here: ", e.getCause());
        // Check if the cause of the exception is an SQLIntegrityConstraintViolationException
        if (e.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException sqlEx = (ConstraintViolationException) e.getCause();
            // Customize this message for clarity
            String message = "Database integrity constraint violation: " + sqlEx.getMessage();
            if (sqlEx.getMessage().contains("Duplicate entry")) {
                message = "Duplicate entry detected";
            }
            // Log the exception
            logger.error("SQLIntegrityConstraintViolationException: ", sqlEx);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.toString(), message));
        }

        // If it's not an SQLIntegrityConstraintViolationException, just handle the general case
        String message = e.getCause().getLocalizedMessage();
        log.error("Invalid input {}", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), message));
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorResponse> handleExceptionMismatchedInputException(MismatchedInputException e) {
        String message = "Invalid Data types";
        log.error("Mismatched Input Exception..." + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), message));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleApiRequestException(ResponseStatusException e) {
        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        log.error("ResponseStatusException...{}", message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), message));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleExceptionCustomException(CustomException e) {
        log.error("Custom Exception... " + e.getMessage());
        e.printStackTrace();
        log.error(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

}
