package com.example.employeemanagement.service;

import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new EmployeeNotFoundException("Employee with employee id " + employeeId + " not found");
        }
    }

    @Override
    public void deleteEmployeeByID(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        Long employeeId = employee.getId();
        if (employeeId != null) {
            Optional<Employee> employeeInstance = employeeRepository.findById(employeeId);
            if (employeeInstance.isPresent()) {
                Employee updatedEmployee = employeeInstance.get();
                updatedEmployee.setEmail(employee.getEmail());
                updatedEmployee.setMobileNumber(employee.getMobileNumber());
                updatedEmployee.setSalary(employee.getSalary());
                updatedEmployee.setGender(employee.getGender());
                updatedEmployee.setName(employee.getName());
                updatedEmployee.setDateOfJoining(employee.getDateOfJoining());
                updatedEmployee.setDepartment(employee.getDepartment());
                updatedEmployee.setDesignation(employee.getDesignation());
                employeeRepository.save(updatedEmployee);
                return updatedEmployee;
            }
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
