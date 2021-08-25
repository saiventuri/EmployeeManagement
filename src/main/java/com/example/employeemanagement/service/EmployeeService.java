package com.example.employeemanagement.service;

import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;

import java.util.List;

/**
 * The service interface responsible for servicing requests
 * related to Employees.
 *
 * @author sai praveen venturi
 */
public interface EmployeeService {

    /**
     * Returns list of all the employees.
     *
     * @return the list containing all the employees.
     */
    List<Employee> getAllEmployees();

    /**
     * Fetches employee with the given employee id.
     *
     * @param employeeId the id of the employee to be fetched.
     * @return the employee with the given employee id.
     * @throws EmployeeNotFoundException if the employee with the given employee id is not found.
     */
    Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    /**
     * Deletes employee with the given employee id.
     *
     * @param employeeId the id of the employee to be deleted.
     * @throws EmployeeNotFoundException if the employee with the given employee id is not found.
     */
    void deleteEmployeeByID(Long employeeId) throws EmployeeNotFoundException;

    /**
     * Deletes all the employees.
     */
    void deleteAllEmployees();

    /**
     * Updates the employee.
     *
     * @param employee the employee that is to be updated.
     * @return employee that is updated.
     * @throws EmployeeNotFoundException if the given employee is not found.
     */
    Employee updateEmployee(Employee employee) throws EmployeeNotFoundException;

    /**
     * Adds the given employee.
     *
     * @param employee employee that is to be added.
     * @return employee that is added.
     */
    Employee addEmployee(Employee employee);
}
