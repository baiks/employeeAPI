package com.employee.dtos;

import com.employee.exception.NonEmptyValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class EmployeesDto {
    @Getter
    @Setter
    @Builder
    public static class CreateDto {

        @NotBlank(message = "First name cannot be blank")
        @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
        private String firstName;

        @NotBlank(message = "Last name cannot be blank")
        @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
        private String lastName;

        @NotBlank(message = "Email cannot be blank")
        @Size(max = 100, message = "Email must be at most 100 characters")
        @Email(message = "Invalid email format")
        private String email;

        @PositiveOrZero(message = "Salary must be a positive number or zero")
        @Digits(integer = 10, fraction = 2, message = "Salary can have up to 10 digits and 2 decimal places")
        private BigDecimal salary;

        @NotNull(message = "Department ID cannot be null")
        @Min(value = 1, message = "Department ID must be a positive number")
        private Long departmentId;
    }

    @Getter
    @Setter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EditDto {

        @NotBlank(message = "First name cannot be blank", groups = {NonEmptyValidation.class})
        @Size(max = 100, message = "First name must be at most 100 characters", groups = {NonEmptyValidation.class})
        private String firstName;

        @NotBlank(message = "Last name cannot be blank", groups = {NonEmptyValidation.class})
        @Size(max = 100, message = "Last name must be at most 100 characters", groups = {NonEmptyValidation.class})
        private String lastName;

        @NotBlank(message = "Email cannot be blank", groups = {NonEmptyValidation.class})
        @Size(max = 100, message = "Email must be at most 100 characters", groups = {NonEmptyValidation.class})
        @Email(message = "Invalid email format", groups = {NonEmptyValidation.class})
        private String email;

        @PositiveOrZero(message = "Salary must be a positive number or zero", groups = {NonEmptyValidation.class})
        @Digits(integer = 10, fraction = 2, message = "Salary can have up to 10 digits and 2 decimal places", groups = {NonEmptyValidation.class})
        private BigDecimal salary;

        @NotNull(message = "Department ID cannot be null", groups = {NonEmptyValidation.class})
        @Min(value = 1, message = "Department ID must be a positive number", groups = {NonEmptyValidation.class})
        private Long departmentId;
    }

    @Builder
    @Getter
    @Setter
    public static class responseDto {
        private String status;
        private String message;
    }
}
