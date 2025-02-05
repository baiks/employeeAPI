package com.employee.controllers;

import com.employee.dtos.DepartmentDto;
import com.employee.dtos.EmployeesDto;
import com.employee.dtos.LimitSortDto;
import com.employee.services.EmployeesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/employees")
@RequiredArgsConstructor
public class EmployeesController {
    private final EmployeesService employeesService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid @RequestBody EmployeesDto.CreateDto createDto) {
        return employeesService.add(createDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody EmployeesDto.EditDto editDto, @PathVariable Long id) {
        return employeesService.update(editDto, id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") int offset,
                                     @RequestParam(required = false, defaultValue = "10") int limit,
                                     @RequestParam(required = false, defaultValue = "id") String sortBy,
                                     @RequestParam(required = false, defaultValue = "desc") String sort,
                                     @PathVariable(required = false) Long schoolId) {
        LimitSortDto limitSortDto = LimitSortDto.builder().offset(offset).limit(limit).sortBy(sortBy).sort(sort).build();
        return employeesService.findAll(limitSortDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return employeesService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/department")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        return employeesService.addDepartment(departmentDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return employeesService.delete(id);
    }
}
