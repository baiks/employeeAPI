package com.employee.management.impl;

import com.employee.management.dtos.EmployeesDto;
import com.employee.management.dtos.LimitSortDto;
import com.employee.management.dtos.ListDataDto;
import com.employee.management.entity.Department;
import com.employee.management.repos.EmployeesRepository;
import com.employee.management.services.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.web.api.entities.Employees;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeesServiceImpl implements EmployeesService {
    private final ModelMapper modelMapper;
    private final EmployeesRepository employeesRepository;


    @Override
    public ResponseEntity<?> add(EmployeesDto.CreateDto createDto) {
        Employees employees = null;
        EmployeesDto.responseDto responseDto = null;
        try {
            if (employeesRepository.findByEmail(createDto.getEmail()).isPresent()) {
                responseDto = EmployeesDto.responseDto.builder().status("01").message("Employee already exist").build();
                return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
            }
            employees = modelMapper.map(createDto, Employees.class);
            Department department = Department.builder().id(createDto.getDepartmentId()).build();
            employees.setDepartment(department);
            employees = employeesRepository.save(employees);
        } catch (Exception ex) {
            responseDto = EmployeesDto.responseDto.builder().status("01").message(ex.getMessage()).build();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(employees, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(EmployeesDto.EditDto editDto, Long id) {
        Employees employees = null;
        EmployeesDto.responseDto responseDto = null;
        try {
            Optional<Employees> employeesOptional = employeesRepository.findById(id);
            Employees existingEmployees = employeesOptional.get();
            modelMapper.map(editDto, existingEmployees);
            existingEmployees.setId(id);
            employees = employeesRepository.save(existingEmployees);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            responseDto = EmployeesDto.responseDto.builder().status("01").message(e.getMessage()).build();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findAll(LimitSortDto limitSortDto) {
        Sort sort = limitSortDto.getSort().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(limitSortDto.getSortBy()).ascending() : Sort.by(limitSortDto.getSortBy()).descending();
        Pageable pageable = PageRequest.of(limitSortDto.getOffset(), limitSortDto.getLimit(), sort);
        long count = count = employeesRepository.count();
        List<Employees> employees = employeesRepository.findAll(pageable).getContent();
        ListDataDto listDataDto = ListDataDto.builder().count(count).results(employees).build();
        return ResponseEntity.ok(listDataDto);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        if (id > 0) {
            Optional<Employees> employees = employeesRepository.findById(id);
            if (employees.isPresent()) {
                return new ResponseEntity<>(employees.get(), HttpStatus.OK);
            } else {
                EmployeesDto.responseDto responseDto = EmployeesDto.responseDto.builder().status("01").message("Employee Id " + id + " not found").build();
                return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
            }
        } else {
            EmployeesDto.responseDto responseDto = EmployeesDto.responseDto.builder().status("01").message("Class Id " + id + " is invalid").build();
            return new ResponseEntity<>(responseDto, HttpStatus.NO_CONTENT);

        }
    }
}
