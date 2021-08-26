package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.errorresponse.ErrorMessage;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/employees",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Saves the employee",
            notes = "Returns Employee if saved properly, otherwise null",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee saved Successfully",
                    response = EmployeeDto.class)})
    EmployeeDto addEmployee(@ApiParam(name = "Employee", value = "Employee to save", required = true) @Valid @RequestBody EmployeeDto employeeDto);

    /**
     * Handler method for get request from the rest client
     * to retrieve all employees from the data repository.
     *
     * @return List of all employee dto's.
     */
    @GetMapping(value = "/employees",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Retrieves all the employees",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Returns Employee list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees retrieved Successfully",
                    response = EmployeeDto.class, responseContainer = "List")})
    List<EmployeeDto> getAllEmployees();

    /**
     * Handler method for get request from the rest client
     * to retrieve an employee based on the id.
     *
     * @param employeeId the id of the employee that is to be retrieved.
     * @return the retrieved the employee dto.
     * @throws EmployeeNotFoundException if employee with given id does not exist.
     */
    @GetMapping(value = "/employees/id/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Retrieve the employee by id",
            notes = "Returns employee if exist, else null",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee retrieved Successfully",
                    response = EmployeeDto.class),
            @ApiResponse(code = 404, message = "Employee not found",
                    response = ErrorMessage.class)})
    EmployeeDto getEmployeeById(@ApiParam(name = "Employee Id", value = "Id of the employee to retrieve", required = true) @PathVariable("id") Long employeeId) throws EmployeeNotFoundException;

    /**
     * Handler method for put request from the rest client
     * to update an employee.
     *
     * @param employeeDto the employee dto that is to be updated.
     * @return the updated employee dto if successfully updated in the data repository.
     * @throws EmployeeNotFoundException if the employee does not exist.
     */
    @PutMapping(value = "/employees",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Updates the existing employee",
            notes = "Returns updated employee if exist",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee updated successfully",
                    response = EmployeeDto.class),
            @ApiResponse(code = 404, message = "Employee not found",
                    response = ErrorMessage.class)})
    EmployeeDto updateEmployee(@ApiParam(name = "Employee", value = "Employee to be updated", required = true) @Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException;

    /**
     * Handler method for patch request from the rest client
     * to update an employee.
     *
     * @param employeeDto the employee dto that is to be updated.
     * @return the updated employee dto if successfully updated in the data repository.
     * @throws EmployeeNotFoundException if the employee does not exist.
     */
    @PatchMapping(value = "/employees",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Patches the existing employee",
            notes = "Returns patched employee if exist",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee patched successfully",
                    response = EmployeeDto.class),
            @ApiResponse(code = 404, message = "Employee not found",
                    response = ErrorMessage.class)})
    EmployeeDto patchEmployee(@ApiParam(name = "Employee", value = "Employee to be patched", required = true) @Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException;

    /**
     * Handler method for delete request from rest client
     * to delete all the employees.
     */
    @DeleteMapping(value = "/employees")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deletes the employees")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employees deleted Successfully")
    })
    void deleteAllEmployees();

    /**
     * Handler method for delete request from the rest client
     * to delete an employee based on the employee id.
     *
     * @param employeeId the id of the employee that is to be deleted.
     * @throws EmployeeNotFoundException if employee does not exist.
     */
    @DeleteMapping(value = "/employees/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deletes the employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee deleted Successfully"),
            @ApiResponse(code = 404, message = "Employee not found", response = ErrorMessage.class)
    })
    void deleteEmployeeByID(@ApiParam(name = "Employee Id", value = "Id of the employee to delete", required = true) @PathVariable("id") Long employeeId) throws EmployeeNotFoundException;
}
