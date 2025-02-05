package com.employee.services;

import com.employee.dtos.DepartmentDto;
import com.employee.dtos.EmployeesDto;
import com.employee.dtos.LimitSortDto;
import org.springframework.http.ResponseEntity;

public interface EmployeesService {

    /**
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

    /**
     *
     * @param departmentDto
     * @return
     */
    ResponseEntity<?> addDepartment(DepartmentDto departmentDto);

    /**
     *
     * @param id
     * @return
     */
    ResponseEntity<?> delete(Long id);
}
