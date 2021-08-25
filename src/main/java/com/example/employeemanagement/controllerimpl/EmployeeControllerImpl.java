package com.example.employeemanagement.controllerimpl;

import com.example.employeemanagement.controller.EmployeeController;
import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.service.EmployeeService;
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
public class EmployeeControllerImpl implements EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        return new EmployeeDto(employeeService.addEmployee(employeeDto.toEmployee()));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees()
                .stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getAllEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.getEmployeeById(employeeId));
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.updateEmployee(employeeDto.toEmployee()));
    }

    @Override
    public EmployeeDto patchEmployee(EmployeeDto employeeDto) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.updateEmployee(employeeDto.toEmployee()));
    }

    @Override
    public void deleteAllEmployees() {
        employeeService.deleteAllEmployees();
    }

    @Override
    public void deleteEmployeeByID(Long employeeId) throws EmployeeNotFoundException {
        employeeService.deleteEmployeeByID(employeeId);
    }
}
