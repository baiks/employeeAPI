package com.employee.management.controllers;

import com.employee.management.dtos.EmployeesDto;
import com.employee.management.dtos.LimitSortDto;
import com.employee.management.services.EmployeesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "employees")
@RequiredArgsConstructor
public class EmployeesController {
    private final EmployeesService employeesService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid @RequestBody EmployeesDto.CreateDto createDto) {
        return employeesService.add(createDto);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
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
}
