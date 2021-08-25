package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Interface for Employee controller which exposes
 * the CRUD end points.
 *
 * @author sai praveen venturi
 */
@RequestMapping("/api")
public interface EmployeeController {

    @PostMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    EmployeeDto addEmployee(@Valid @RequestBody EmployeeDto employeeDto);

    @GetMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    List<EmployeeDto> getAllEmployees();

    @GetMapping(value = "/employees/id/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    EmployeeDto getAllEmployeeById(@PathVariable("id") Long employeeId) throws EmployeeNotFoundException;

    @PutMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    EmployeeDto updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException;

    @PatchMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    EmployeeDto patchEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException;

    @DeleteMapping(value = "/employees", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteAllEmployees();

    @DeleteMapping(value = "/employees/{id}", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteEmployeeByID(@PathVariable("id") Long employeeId) throws EmployeeNotFoundException;
}
