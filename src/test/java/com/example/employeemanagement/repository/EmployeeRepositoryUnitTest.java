package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Gender;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryUnitTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Employee employee = Employee.builder()
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 29))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        testEntityManager.persist(employee);
    }


    @Test
    @Order(1)
    void save_whenValidEmployeeGiven_employeeShouldBeSavedAndReturned() {

        Employee employee = Employee.builder()
                .name("Rani")
                .dateOfJoining(LocalDate.of(2021, 7, 20))
                .department("Frontend")
                .email("rani@gmail.com")
                .salary(30000)
                .mobileNumber("9498343811")
                .designation("Associate Engineer")
                .gender(Gender.FEMALE)
                .build();

        assertEquals(employee, employeeRepository.save(employee), "Creation of employee unsuccessful");
        assertTrue(employeeRepository.findById(employee.getId()).isPresent(), "Employee not exist in the data base");
    }

    @Test
    @Order(2)
    void save_whenInvalidEmployeeGiven_shouldThrowDataAccessException() {

        Employee employee = Employee.builder()
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 29))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> employeeRepository.saveAndFlush(employee), "Creation of employee unsuccessful");
    }

    @Test
    @Order(3)
    void findAll_whenEmployeesArePresent_shouldReturnListOfEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        assertNotNull(employeeList, "Employee list from database is null");
        assertFalse(employeeList.isEmpty(), "Employee list is empty");
    }

    @Test
    @Order(4)
    void findAll_whenEmployeesAreNotPresent_shouldReturnEmptyListOfEmployees() {

        assertDoesNotThrow(() -> employeeRepository.deleteAll(), "Clearing the database unsuccessful");
        List<Employee> employeeList = employeeRepository.findAll();
        assertNotNull(employeeList, "Employee list from database is null");
        assertTrue(employeeList.isEmpty(), "Employee list is not empty");
    }

    @Test
    @Order(5)
    void findById_whenEmployeeIsPresent_shouldReturnEmployee() {

        Employee employee = Employee.builder()
                .name("Vijay")
                .dateOfJoining(LocalDate.of(2021, 7, 26))
                .department("HR")
                .email("vijay@gmail.com")
                .salary(40000)
                .mobileNumber("9213409876")
                .designation("Assistant Engineer")
                .gender(Gender.MALE)
                .build();

        assertEquals(employee, employeeRepository.save(employee), "Creation of employee unsuccessful");
        assertTrue(employeeRepository.findById(employee.getId()).isPresent());
    }

    @Test
    @Order(6)
    void findById_whenEmployeeNotPresent_shouldReturnNull() {
        assertFalse(employeeRepository.findById(33L).isPresent());
    }

    @Test
    @Order(7)
    void existsById_whenEmployeeIsPresent_shouldReturnTrue() {

        Employee employee = Employee.builder()
                .name("Lakes")
                .dateOfJoining(LocalDate.of(2021, 7, 26))
                .department("Web")
                .email("lokesh@gmail.com")
                .salary(40000)
                .mobileNumber("9498309876")
                .designation("Associate Engineer")
                .gender(Gender.MALE)
                .build();

        assertEquals(employee, employeeRepository.save(employee), "Creation of employee unsuccessful");
        assertTrue(employeeRepository.existsById(employee.getId()), "Employee not exist in the database");
    }

    @Test
    @Order(8)
    void existsById_whenEmployeeIsNotPresent_shouldReturnFalse() {
        assertFalse(employeeRepository.existsById(200L), "Employee not exist in the database");
    }

    @Test
    @Order(9)
    void count_whenEmployeesPresent_shouldReturnCountOfEmployees() {
        assertTrue(employeeRepository.count() > 0, "Employee count is not greater than zero in the database");
    }

    @Test
    @Order(10)
    void count_whenEmployeesNotPresent_shouldReturnZero() {
        assertDoesNotThrow(() -> employeeRepository.deleteAll(), "Clearing the database unsuccessful");
        assertEquals(0, employeeRepository.count(), "Employee count is not zero in the database");
    }

    @Test
    @Order(11)
    void deleteAll_inEveryScenario_shouldDeleteAllEmployees() {
        assertDoesNotThrow(() -> employeeRepository.deleteAll(), "Clearing the database unsuccessful");
    }

    @Test
    @Order(12)
    void deleteById_whenEmployeePresent_shouldDeleteEmployee() {

        Employee employee = Employee.builder()
                .name("Vijay")
                .dateOfJoining(LocalDate.of(2021, 7, 26))
                .department("HR")
                .email("vijay@gmail.com")
                .salary(40000)
                .mobileNumber("9213409876")
                .designation("Assistant Engineer")
                .gender(Gender.MALE)
                .build();

        assertEquals(employee, employeeRepository.save(employee), "Creation of employee should be successful");
        assertDoesNotThrow(() -> employeeRepository.deleteById(employee.getId()), "Employee deletion unsuccessful");
    }

    @Test
    @Order(13)
    void deleteById_whenEmployeeNotPresent_shouldDeleteEmployee() {

        assertDoesNotThrow(() -> employeeRepository.deleteAll(), "Clearing the database unsuccessful");
        assertThrows(EmptyResultDataAccessException.class, () -> employeeRepository.deleteById(1L), "Deletion of employee successful");
    }
}