package com.example.employeemanagement.service;

import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    void deleteEmployeeByID(Long employeeId);

    void deleteAllEmployees();

    Employee updateEmployee(Employee employee) throws EmployeeNotFoundException;

    Employee addEmployee(Employee employee);
}
