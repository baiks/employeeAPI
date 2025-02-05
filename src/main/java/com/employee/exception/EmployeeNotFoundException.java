package com.employee.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class EmployeeNotFoundException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String message;
    protected HttpStatus status;

	public EmployeeNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public EmployeeNotFoundException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
