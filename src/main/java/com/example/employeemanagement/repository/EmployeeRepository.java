package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for Employees.
 *
 * @author sai praveen venturi
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
