package com.employee.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class EmployeeExistsException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String message;
    protected HttpStatus status;

	public EmployeeExistsException(String message) {
        super(message);
        this.message = message;
    }

    public EmployeeExistsException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
