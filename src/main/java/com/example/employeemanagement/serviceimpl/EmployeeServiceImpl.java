package com.example.employeemanagement.serviceimpl;

import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation class for Employee Service interface.
 *
 * @author sai praveen venturi
 * @see com.example.employeemanagement.service.EmployeeService
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * Employee repository used to perform create, read, update and delete Operations
     * on the data source.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Returns list of all the employees.
     *
     * @return the list containing all the employees.
     */
    @Override
    public List<Employee> getAllEmployees() {
        if (employeeRepository.count() > 0) {
            return employeeRepository.findAll();
        }
        return new ArrayList<>();
    }

    /**
     * Fetches employee with the given employee id.
     *
     * @param employeeId the id of the employee to be fetched.
     * @return the employee with the given employee id.
     * @throws EmployeeNotFoundException if the employee with the given employee id is not found.
     */
    @Override
    public Employee getEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        if (employeeId != null) {
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if (employee.isPresent()) {
                return employee.get();
            }
        }
        throw new EmployeeNotFoundException("Employee with employee id " + employeeId + " not found");
    }

    /**
     * Deletes employee with the given employee id.
     *
     * @param employeeId the id of the employee to be deleted.
     * @throws EmployeeNotFoundException if the employee with the given employee id is not found.
     */
    @Override
    public void deleteEmployeeByID(Long employeeId) throws EmployeeNotFoundException {
        if (employeeId != null && employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new EmployeeNotFoundException("Unable to delete employee with id " + employeeId);
        }
    }

    /**
     * Deletes all the employees.
     */
    @Override
    public void deleteAllEmployees() {
        if (employeeRepository.count() > 0) {
            employeeRepository.deleteAll();
        }
    }

    /**
     * Updates the employee.
     *
     * @param employee the employee that is to be updated.
     * @return employee that is updated.
     * @throws EmployeeNotFoundException if the given employee is not found.
     */
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
        throw new EmployeeNotFoundException("Unable to update employee with id " + employeeId);
    }

    /**
     * Adds the given employee.
     *
     * @param employee employee that is to be added.
     * @return employee that is added.
     */
    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
