package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for Employees.
 *
 * @author sai praveen venturi
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
