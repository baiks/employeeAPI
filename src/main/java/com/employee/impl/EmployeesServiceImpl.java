package com.employee.impl;

import com.employee.configs.Constants;
import com.employee.dtos.*;
import com.employee.entity.Department;
import com.employee.exception.CustomException;
import com.employee.exception.EmployeeExistsException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.repos.DepartmentRepository;
import com.employee.repos.EmployeesRepository;
import com.employee.services.EmployeesService;
import com.employee.services.NotificationsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.employee.entity.Employees;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeesServiceImpl implements EmployeesService {
    private final ModelMapper modelMapper;
    private final EmployeesRepository employeesRepository;
    private final NotificationsService notificationsService;
    private final DepartmentRepository departmentRepository;


    @Override
    public ResponseEntity<?> add(EmployeesDto.CreateDto createDto) {
        Employees employees = null;
        EmployeesDto.responseDto responseDto = null;
        try {
            Department department = departmentRepository.findById(createDto.getDepartmentId())
                    .orElseThrow(() -> new EmployeeExistsException("Department with ID " + createDto.getDepartmentId() + " not found", HttpStatus.NOT_FOUND));
            if (employeesRepository.findByEmail(createDto.getEmail()).isPresent()) {
                throw new EmployeeExistsException("Employee already exists", HttpStatus.CONFLICT);
            }
            employees = modelMapper.map(createDto, Employees.class);
            employees.setDepartment(department);
            employees = employeesRepository.save(employees);
            if (employees.getId() > 0) {
                String name = String.join(" ", createDto.getFirstName(), createDto.getLastName());
                String content = String.format(Constants.NoticationConstants.createEmailTemplate, name, employees.getId());
                List<String> recipients = List.of(employees.getEmail());
                NotificationDto notificationDto = NotificationDto.builder().content(content).recipients(recipients).build();
                notificationsService.sendEmail(notificationDto);
            }
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
            Employees existingEmployees = employeesRepository.findById(id).orElseThrow(() -> new EmployeeExistsException("Employee with ID " + id + " does not exist", HttpStatus.NOT_FOUND));
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
        long count = employeesRepository.count();
        List<Employees> employees = employeesRepository.findAll(pageable).getContent();
        ListDataDto listDataDto = ListDataDto.builder().count(count).results(employees).build();
        return ResponseEntity.ok(listDataDto);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        if (id > 0) {
            Optional<Employees> employees = Optional.ofNullable(employeesRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Invalid Credentials")));
            if (employees.isPresent()) {
                return new ResponseEntity<>(employees.get(), HttpStatus.OK);
            } else {
                EmployeesDto.responseDto responseDto = EmployeesDto.responseDto.builder().status("01").message("Employee Id " + id + " not found").build();
                return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
            }
        } else {
            EmployeesDto.responseDto responseDto = EmployeesDto.responseDto.builder().status("01").message("Employee Id " + id + " is invalid").build();
            return new ResponseEntity<>(responseDto, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> addDepartment(DepartmentDto departmentDto) {
        Department department = null;
        try {
            department = modelMapper.map(departmentDto, Department.class);
            department = departmentRepository.save(department);
        } catch (Exception ex) {
            EmployeesDto.responseDto responseDto = EmployeesDto.responseDto.builder().status("01").message(ex.getMessage()).build();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }
}
