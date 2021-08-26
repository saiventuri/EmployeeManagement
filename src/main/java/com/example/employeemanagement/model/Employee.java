package com.example.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The employee entity class.
 *
 * @author sai praveen venturi
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employees_table")
public class Employee {

    /**
     * The unique identifier for employee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Long id;

    /**
     * Name of the Employee.
     */
    @Column(name = "employee_name", nullable = false)
    private String name;

    /**
     * Designation of the Employee.
     */
    @Column(name = "employee_designation")
    private String designation;

    /**
     * Department of the Employee.
     */
    @Column(name = "employee_department")
    private String department;

    /**
     * Date of joining of the Employee.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    @Column(name = "employee_date_of_joining")
    private LocalDate dateOfJoining;

    /**
     * Salary of the Employee.
     */
    @Column(name = "employee_salary")
    private int salary;

    /**
     * Gender of the Employee.
     */
    @Column(name = "employee_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Email of the Employee.
     */
    @Column(name = "employee_email", unique = true, nullable = false)
    private String email;

    /**
     * Mobile number of the Employee.
     */
    @Column(name = "employee_mobile_number", unique = true, nullable = false)
    private String mobileNumber;
}
