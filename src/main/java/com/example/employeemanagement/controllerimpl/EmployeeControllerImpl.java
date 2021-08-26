package com.example.employeemanagement.controllerimpl;

import com.example.employeemanagement.controller.EmployeeController;
import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.service.EmployeeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation class for Employee Controller interface.
 *
 * @author sai praveen venturi
 * @see com.example.employeemanagement.controller.EmployeeController
 */
@RestController
@Api(tags = {"Employee Service"})
public class EmployeeControllerImpl implements EmployeeController {
    /**
     * The employee service.
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * Handler method for post request from the rest client to
     * add employee to the data repository.
     *
     * @param employeeDto The employee dto object sent through post request.
     * @return the employee dto after adding the received employee to the data source.
     */
    @Override
    public EmployeeDto addEmployee(final EmployeeDto employeeDto) {
        return new EmployeeDto(employeeService.addEmployee(employeeDto.toEmployee()));
    }

    /**
     * Handler method for get request from the rest client
     * to retrieve all employees from the data repository.
     *
     * @return List of all employee dto's.
     */
    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees()
                .stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Handler method for get request from the rest client
     * to retrieve an employee based on the id.
     *
     * @param employeeId the id of the employee that is to be retrieved.
     * @return the retrieved the employee dto.
     * @throws EmployeeNotFoundException if employee with given id does not exist.
     */
    @Override
    public EmployeeDto getEmployeeById(final Long employeeId) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.getEmployeeById(employeeId));
    }

    /**
     * Handler method for put request from the rest client
     * to update an employee.
     *
     * @param employeeDto the employee dto that is to be updated.
     * @return the updated employee dto if successfully updated in the data repository.
     * @throws EmployeeNotFoundException if the employee does not exist.
     */
    @Override
    public EmployeeDto updateEmployee(final EmployeeDto employeeDto) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.updateEmployee(employeeDto.toEmployee()));
    }

    /**
     * Handler method for patch request from the rest client
     * to update an employee.
     *
     * @param employeeDto the employee dto that is to be updated.
     * @return the updated employee dto if successfully updated in the data repository.
     * @throws EmployeeNotFoundException if the employee does not exist.
     */
    @Override
    public EmployeeDto patchEmployee(final EmployeeDto employeeDto) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.updateEmployee(employeeDto.toEmployee()));
    }

    /**
     * Handler method for delete request from rest client
     * to delete all the employees.
     */
    @Override
    public void deleteAllEmployees() {
        employeeService.deleteAllEmployees();
    }

    /**
     * Handler method for delete request from the rest client
     * to delete an employee based on the employee id.
     *
     * @param employeeId the id of the employee that is to be deleted.
     * @throws EmployeeNotFoundException if employee does not exist.
     */
    @Override
    public void deleteEmployeeByID(final Long employeeId) throws EmployeeNotFoundException {
        employeeService.deleteEmployeeByID(employeeId);
    }
}
