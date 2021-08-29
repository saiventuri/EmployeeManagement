package com.example.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main class from where
 * application starts.
 *
 * @author sai praveen venturi
 */
@SpringBootApplication
public class EmployeeManagementApplication {

    /**
     * The main method used to start the spring application.
     *
     * @param args properties that can be given while starting the
     *             application.
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }
}
