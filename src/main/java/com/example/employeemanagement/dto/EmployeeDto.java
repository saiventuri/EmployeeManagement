package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto implements Serializable {

    private Long id;

    @NotEmpty(message = "Employee Name cannot be empty")
    private String name;

    @NotEmpty(message = "Employee designation cannot be empty")
    private String designation;

    @NotEmpty(message = "Employee should be assigned in a department")
    private String department;

    @NotNull(message = "Employee date of joining is mandatory")
    private Date dateOfJoining;

    private int salary;

    private Gender gender;

    @NotNull(message = "Employee email id is mandatory")
    @Email
    private String email;

    @NotNull(message = "Employee mobile number is mandatory")
    private String mobileNumber;

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

    public EmployeeDto(Employee employee) {
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
}
