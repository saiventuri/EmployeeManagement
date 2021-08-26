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
    /**
     * Handler method for post request from the rest client to
     * add employee to the data repository.
     *
     * @param employeeDto The employee dto object sent through post request.
     * @return the employee dto after adding the received employee to the data source.
     */
    @PostMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    EmployeeDto addEmployee(@Valid @RequestBody EmployeeDto employeeDto);

    /**
     * Handler method for get request from the rest client
     * to retrieve all employees from the data repository.
     *
     * @return List of all employee dto's.
     */
    @GetMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    List<EmployeeDto> getAllEmployees();

    /**
     * Handler method for get request from the rest client
     * to retrieve an employee based on the id.
     *
     * @param employeeId the id of the employee that is to be retrieved.
     * @return the retrieved the employee dto.
     * @throws EmployeeNotFoundException if employee with given id does not exist.
     */
    @GetMapping(value = "/employees/id/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    EmployeeDto getAllEmployeeById(@PathVariable("id") Long employeeId) throws EmployeeNotFoundException;

    /**
     * Handler method for put request from the rest client
     * to update an employee.
     *
     * @param employeeDto the employee dto that is to be updated.
     * @return the updated employee dto if successfully updated in the data repository.
     * @throws EmployeeNotFoundException if the employee does not exist.
     */
    @PutMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    EmployeeDto updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException;

    /**
     * Handler method for patch request from the rest client
     * to update an employee.
     *
     * @param employeeDto the employee dto that is to be updated.
     * @return the updated employee dto if successfully updated in the data repository.
     * @throws EmployeeNotFoundException if the employee does not exist.
     */
    @PatchMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    EmployeeDto patchEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException;

    /**
     * Handler method for delete request from rest client
     * to delete all the employees.
     */
    @DeleteMapping(value = "/employees", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteAllEmployees();

    /**
     * Handler method for delete request from the rest client
     * to delete an employee based on the employee id.
     *
     * @param employeeId the id of the employee that is to be deleted.
     * @throws EmployeeNotFoundException if employee does not exist.
     */
    @DeleteMapping(value = "/employees/{id}", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteEmployeeByID(@PathVariable("id") Long employeeId) throws EmployeeNotFoundException;
}
