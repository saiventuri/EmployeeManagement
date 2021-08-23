package com.example.employeemanagement.restcontroller;

import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();

        return employeeList.stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/id/{id}")
    public EmployeeDto getAllEmployeeById(@PathVariable("id") Long employeeId) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.getEmployeeById(employeeId));
    }

    @PostMapping("/employees")
    public EmployeeDto addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return new EmployeeDto(employeeService.addEmployee(employeeDto.toEmployee()));
    }

    @PutMapping("/employees")
    public EmployeeDto updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.updateEmployee(employeeDto.toEmployee()));
    }

    @DeleteMapping("/employees")
    public void deleteAllEmployees() {
        employeeService.deleteAllEmployees();
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeByID(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployeeByID(employeeId);
    }

    @PatchMapping("/employees")
    public EmployeeDto patchEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNotFoundException {
        return new EmployeeDto(employeeService.updateEmployee(employeeDto.toEmployee()));
    }
}
