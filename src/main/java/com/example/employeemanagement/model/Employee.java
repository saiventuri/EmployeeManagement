package com.example.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employees_table")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "employee_designation")
    private String designation;

    @Column(name = "employee_department")
    private String department;

    @Column(name = "employee_date_of_joining")
    private Date dateOfJoining;

    @Column(name = "employee_salary")
    private int salary;

    @Column(name = "employee_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "employee_email", unique = true)
    private String email;

    @Column(name = "employee_mobile_number", unique = true)
    private String mobileNumber;
}
