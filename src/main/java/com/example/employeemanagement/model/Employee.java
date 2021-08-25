package com.example.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employees_table")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_name", nullable = false)
    private String name;

    @Column(name = "employee_designation")
    private String designation;

    @Column(name = "employee_department")
    private String department;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy")
    @Column(name = "employee_date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "employee_salary")
    private int salary;

    @Column(name = "employee_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "employee_email", unique = true, nullable = false)
    private String email;

    @Column(name = "employee_mobile_number", unique = true, nullable = false)
    private String mobileNumber;
}
