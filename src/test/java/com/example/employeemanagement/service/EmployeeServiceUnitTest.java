package com.example.employeemanagement.service;

import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Gender;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceUnitTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    private List<Employee> mockedEmployeesList = new ArrayList<>();

    private Employee findEmployeeById(Long employeeId) {
        if (mockedEmployeesList != null) {
            for (Employee employee : mockedEmployeesList) {
                if (employee.getId().equals(employeeId)) return employee;
            }
        }
        return null;
    }

    private boolean isEmployeeWithIdExists(Long employeeId) {
        if (mockedEmployeesList != null) {
            for (Employee employee : mockedEmployeesList) {
                if (employee.getId().equals(employeeId)) return true;
            }
        }
        return false;
    }

    @BeforeEach
    void setUp() {

        mockedEmployeesList.add(Employee.builder()
                .id(1L)
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 29))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build());

        mockedEmployeesList.add(Employee.builder()
                .id(2L)
                .name("Rani")
                .dateOfJoining(LocalDate.of(2021, 7, 20))
                .department("Frontend")
                .email("rani@gmail.com")
                .salary(30000)
                .mobileNumber("9498343811")
                .designation("Associate Engineer")
                .gender(Gender.FEMALE)
                .build());

        initMockStubs();
    }

    @AfterEach()
    void tearDown() {
        if (mockedEmployeesList != null) {
            mockedEmployeesList.clear();
        }
    }

    void initMockStubs() {

        Mockito.when(employeeRepository.count()).thenReturn((long) mockedEmployeesList.size());

        Mockito.when(employeeRepository.findById(Mockito.any(Long.class)))
                .thenAnswer(parameter -> {
                    System.out.println(mockedEmployeesList);
                    Long employeeId = (Long) parameter.getArguments()[0];
                    return java.util.Optional.ofNullable(findEmployeeById(employeeId));
                });

        Mockito.when(employeeRepository.findAll())
                .thenReturn(mockedEmployeesList);

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenAnswer(parameter -> {
                    Employee employee = (Employee) parameter.getArguments()[0];
                    if (mockedEmployeesList != null) {
                        mockedEmployeesList.add(employee);
                    }
                    return employee;
                });

        Mockito.when(employeeRepository.existsById(Mockito.any(Long.class)))
                .thenAnswer(parameter -> {
                    Long employeeId = (Long) parameter.getArguments()[0];
                    return isEmployeeWithIdExists(employeeId);
                });

        Mockito.doAnswer(parameter -> {
            Long employeeId = (Long) parameter.getArguments()[0];
            if (mockedEmployeesList != null) {
                Employee employee = findEmployeeById(employeeId);
                mockedEmployeesList.remove(employee);
            }
            return null;
        })
                .when(employeeRepository)
                .deleteById(Mockito.anyLong());

        Mockito.doAnswer(parameter -> {
            mockedEmployeesList.clear();
            return null;
        }).when(employeeRepository).deleteAll();
    }

    @Test
    @Order(1)
    void addEmployee_whenValidEmployeeGiven_EmployeeShouldBeAddedAndReturned() {
        Employee mockEmployee = Employee.builder()
                .id(1L)
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 29))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        assertEquals(mockEmployee, employeeService.addEmployee(mockEmployee));
    }

    @Test
    @Order(2)
    void getEmployeeById_whenValidEmployeeIdGiven_thenEmployeeShouldBeFound() throws EmployeeNotFoundException {
        Long id = 1L;
        System.out.println(mockedEmployeesList);
        Employee employee = employeeService.getEmployeeById(id);
        assertNotNull(employee, "Employee should be null");
        assertEquals(id, employee.getId());
    }

    @Test
    @Order(3)
    void getEmployeeById_whenNonValidEmployeeIdGiven_thenThrowsEmployeeNotFoundException() {
        Long id = 3L;
        Throwable exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(id));
        assertEquals("Employee with employee id " + id + " not found", exception.getMessage());
    }

    @Test
    @Order(5)
    void getAllEmployees_whenEmployeesArePresent_shouldReturnListOfEmployees() {
        List<Employee> expectedEmployeeList = new ArrayList<>();

        expectedEmployeeList.add(Employee.builder()
                .id(1L)
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 29))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build());

        expectedEmployeeList.add(Employee.builder()
                .id(2L)
                .name("Rani")
                .dateOfJoining(LocalDate.of(2021, 7, 20))
                .department("Frontend")
                .email("rani@gmail.com")
                .salary(30000)
                .mobileNumber("9498343811")
                .designation("Associate Engineer")
                .gender(Gender.FEMALE)
                .build());

        List<Employee> employeeList = employeeService.getAllEmployees();

        assertNotNull(employeeList);
        assertEquals(expectedEmployeeList, employeeList);
    }

    @Test
    @Order(5)
    void updateEmployee_whenValidEmployeeGiven_shouldUpdateAndReturnUpdatedEmployee() throws EmployeeNotFoundException {
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .name("Rani")
                .dateOfJoining(LocalDate.of(2021, 6, 20))
                .department("Frontend")
                .email("rani@gmail.com")
                .salary(30000)
                .mobileNumber("8074784700")
                .designation("Associate Engineer")
                .gender(Gender.FEMALE)
                .build();

        Employee updatedEmployeeFromRepository = employeeService.updateEmployee(updatedEmployee);
        assertNotNull(updatedEmployeeFromRepository);
        assertEquals(updatedEmployee.getName(), updatedEmployeeFromRepository.getName());
        assertEquals(updatedEmployee.getGender(), updatedEmployeeFromRepository.getGender());
        assertEquals(updatedEmployee.getSalary(), updatedEmployeeFromRepository.getSalary());
        assertEquals(updatedEmployee.getDepartment(), updatedEmployeeFromRepository.getDepartment());
        assertEquals(updatedEmployee.getDesignation(), updatedEmployeeFromRepository.getDesignation());
        assertEquals(updatedEmployee.getEmail(), updatedEmployeeFromRepository.getEmail());
        assertEquals(updatedEmployee.getMobileNumber(), updatedEmployeeFromRepository.getMobileNumber());
        assertEquals(updatedEmployee.getDateOfJoining(), updatedEmployeeFromRepository.getDateOfJoining());
    }

    @Test
    @Order(6)
    void updateEmployee_whenEmployeeWithNonExistingId_shouldThrowEmployeeNotFoundException() {
        Employee unExistingEmployee = Employee.builder()
                .id(3L)
                .build();
        Throwable exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(unExistingEmployee));
        assertEquals("Unable to update employee with id " + unExistingEmployee.getId(), exception.getMessage());
    }

    @Test
    @Order(7)
    void deleteEmployeeById_whenValidEmployeeIdGiven_thenEmployeeShouldBeDeleted() {
        Long employeeId = 2L;
        assertDoesNotThrow(() -> employeeService.deleteEmployeeByID(employeeId));
        Throwable exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
        assertEquals("Employee with employee id " + employeeId + " not found", exception.getMessage());
    }

    @Test
    @Order(8)
    void deleteEmployeeById_whenNonValidEmployeeIdGiven_thenThrowsEmployeeNotFoundException() {
        Long employeeId = 3L;
        Throwable exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployeeByID(employeeId));
        assertEquals("Unable to delete employee with id " + employeeId, exception.getMessage());
    }

    @Test
    @Order(9)
    void deleteAllEmployees_whenEmployeesArePresent() {
        assertDoesNotThrow(() -> employeeService.deleteAllEmployees());
        assertTrue(employeeService.getAllEmployees().isEmpty());
        assertDoesNotThrow(() -> employeeService.deleteAllEmployees());
    }
}