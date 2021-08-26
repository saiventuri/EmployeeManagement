package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Data Transfer Object class for Employee Entity.
 *
 * @author sai praveen venturi
 * @see com.example.employeemanagement.model.Employee
 */
@Data
@NoArgsConstructor
public class EmployeeDto implements Serializable {

    /**
     * Unique identifier for Employee.
     */
    private Long id;

    /**
     * Name of the Employee.
     */
    @NotEmpty(message = "Employee Name cannot be empty")
    private String name;

    /**
     * Designation of the Employee.
     */
    @NotEmpty(message = "Employee designation cannot be empty")
    private String designation;

    /**
     * Department of the Employee.
     */
    @NotEmpty(message = "Employee should be assigned in a department")
    private String department;

    /**
     * Date of joining of the Employee.
     */
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    @NotNull(message = "Employee date of joining is mandatory")
    private LocalDate dateOfJoining;

    /**
     * Salary of the Employee.
     */
    private int salary;

    /**
     * Gender of the Employee.
     */
    private Gender gender;

    /**
     * Email of the Employee.
     */
    @NotNull(message = "Employee email id is mandatory")
    @Email
    private String email;

    /**
     * Mobile number of the Employee.
     */
    @NotNull(message = "Employee mobile number is mandatory")
    private String mobileNumber;

    /**
     * Constructor used to initialize the DTO object using Employee entity.
     *
     * @param employee the employee used to initialize this object instance.
     */
    public EmployeeDto(final Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.department = employee.getDepartment();
        this.designation = employee.getDesignation();
        this.dateOfJoining = employee.getDateOfJoining();
        this.gender = employee.getGender();
        this.salary = employee.getSalary();
        this.email = employee.getEmail();
        this.mobileNumber = employee.getMobileNumber();
    }

    /**
     * Converts this data transfer object to Employee entity object.
     *
     * @return the Employee entity object.
     */
    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.id);
        employee.setName(this.name);
        employee.setDepartment(this.department);
        employee.setDateOfJoining(this.dateOfJoining);
        employee.setDesignation(this.designation);
        employee.setGender(this.gender);
        employee.setSalary(this.salary);
        employee.setMobileNumber(this.mobileNumber);
        employee.setEmail(this.email);

        return employee;
    }
}
