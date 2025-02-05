package com.employee.management.services;

import com.employee.management.dtos.EmployeesDto;
import com.employee.management.dtos.LimitSortDto;
import com.web.api.dtos.ClassesDto;
import com.web.api.dtos.LimitSortDto;
import org.springframework.http.ResponseEntity;

public interface EmployeesService {
    /**
     *
     * @param createDto
     * @return
     */
    ResponseEntity<?> add(EmployeesDto.CreateDto createDto);


    /**
     * @param editDto
     * @param id
     * @return
     */
    ResponseEntity<?> update(EmployeesDto.EditDto editDto, Long id);


    /**
     * @return
     */
    ResponseEntity<?> findAll(LimitSortDto limitSortDto);

    /**
     * @param id
     * @return
     */
    ResponseEntity<?> findById(Long id);
}
