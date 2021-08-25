package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDto;
import com.example.employeemanagement.errorresponse.ErrorMessage;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Gender;
import com.example.employeemanagement.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EmployeeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerUnitTest {

    private final String URI = "/api/employees";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void addEmployee_whenValidEmployeeGiven_shouldAddAndReturnEmployeeDto() throws Exception {

        Employee employee = Employee.builder()
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 28))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        String inputJson = "    {\n" +
                "        \"name\": \"Sai Praveen\",\n" +
                "        \"designation\": \"Software Engineer\",\n" +
                "        \"department\": \"Backend\",\n" +
                "        \"dateOfJoining\": \"28/07/2021\",\n" +
                "        \"salary\": 50000,\n" +
                "        \"gender\": \"MALE\",\n" +
                "        \"email\": \"saipraveen.venturi@gmail.com\",\n" +
                "        \"mobileNumber\": \"9493843811\"\n" +
                "    }";

        Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class)))
                .thenAnswer(parameter -> parameter.getArguments()[0]);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(this.URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus(), "Http status is not created");
        assertEquals(new EmployeeDto(employee), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDto.class), "Http response is not as expected");
    }

    @Test
    @Order(2)
    void getEmployeeById_whenEmployeePresent_shouldReturnEmployeeDto() throws Exception {

        Employee employee = Employee.builder()
                .id(1L)
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 28))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI + "/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(new EmployeeDto(employee), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDto.class), "Http response is not as expected");
    }

    @Test
    @Order(3)
    void getEmployeeById_whenEmployeeNotPresent_shouldThrowEmployeeNotFoundException() throws Exception {
        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong()))
                .thenAnswer(parameter -> {
                    Long employeeId = (Long) parameter.getArguments()[0];
                    throw new EmployeeNotFoundException("Employee with employee id " + employeeId + " not found");
                });

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, "Employee with employee id 1 not found");
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI + "/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }

    @Test
    @Order(4)
    void getAllEmployees_whenEmployeesPresent_shouldReturnListOfEmployees() throws Exception {
        Employee employee = Employee.builder()
                .id(1L)
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 28))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        EmployeeDto[] expectedArray = {new EmployeeDto(employee)};

        Mockito.when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employee));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertArrayEquals(expectedArray, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDto[].class), "Http response is not as expected");
    }

    @Test
    @Order(5)
    void getAllEmployees_whenEmployeesNotPresent_shouldReturnEmptyListOfEmployees() throws Exception {

        Mockito.when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDto[].class).length, "Http response is not as expected");
    }

    @Test
    @Order(6)
    void updateEmployee_whenEmployeePresent_shouldReturnUpdatedEmployeeDto() throws Exception {

        Employee employee = Employee.builder()
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 28))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        String inputJson = "    {\n" +
                "        \"name\": \"Sai Praveen\",\n" +
                "        \"designation\": \"Software Engineer\",\n" +
                "        \"department\": \"Frontend\",\n" +
                "        \"dateOfJoining\": \"28/07/2021\",\n" +
                "        \"salary\": 60000,\n" +
                "        \"gender\": \"MALE\",\n" +
                "        \"email\": \"saipraveen@gmail.com\",\n" +
                "        \"mobileNumber\": \"9493843811\"\n" +
                "    }";

        Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class)))
                .thenAnswer(parameter -> {
                    Employee givenEmployee = (Employee) parameter.getArguments()[0];
                    employee.setEmail(givenEmployee.getEmail());
                    employee.setMobileNumber(givenEmployee.getMobileNumber());
                    employee.setSalary(givenEmployee.getSalary());
                    employee.setGender(givenEmployee.getGender());
                    employee.setName(givenEmployee.getName());
                    employee.setDateOfJoining(givenEmployee.getDateOfJoining());
                    employee.setDepartment(givenEmployee.getDepartment());
                    employee.setDesignation(givenEmployee.getDesignation());
                    return employee;
                });

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(this.URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(new EmployeeDto(employee), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDto.class), "Http response is not as expected");
    }

    @Test
    @Order(7)
    void patchEmployee_whenEmployeePresent_shouldReturnUpdatedEmployeeDto() throws Exception {

        Employee employee = Employee.builder()
                .name("Sai Praveen")
                .dateOfJoining(LocalDate.of(2021, 7, 28))
                .department("Backend")
                .email("saipraveen.venturi@gmail.com")
                .salary(50000)
                .mobileNumber("9493843811")
                .designation("Software Engineer")
                .gender(Gender.MALE)
                .build();

        String inputJson = "    {\n" +
                "        \"name\": \"Sai Praveen\",\n" +
                "        \"designation\": \"Software Engineer\",\n" +
                "        \"department\": \"Frontend\",\n" +
                "        \"dateOfJoining\": \"28/07/2021\",\n" +
                "        \"salary\": 60000,\n" +
                "        \"gender\": \"MALE\",\n" +
                "        \"email\": \"saipraveen@gmail.com\",\n" +
                "        \"mobileNumber\": \"9493843811\"\n" +
                "    }";

        Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class)))
                .thenAnswer(parameter -> {
                    Employee givenEmployee = (Employee) parameter.getArguments()[0];
                    employee.setEmail(givenEmployee.getEmail());
                    employee.setMobileNumber(givenEmployee.getMobileNumber());
                    employee.setSalary(givenEmployee.getSalary());
                    employee.setGender(givenEmployee.getGender());
                    employee.setName(givenEmployee.getName());
                    employee.setDateOfJoining(givenEmployee.getDateOfJoining());
                    employee.setDepartment(givenEmployee.getDepartment());
                    employee.setDesignation(givenEmployee.getDesignation());
                    return employee;
                });

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(this.URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(new EmployeeDto(employee), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDto.class), "Http response is not as expected");
    }

    @Test
    @Order(8)
    void deleteAllEmployees_inAllCases_shouldNotThrowExceptions() throws Exception {
        Mockito.doNothing().when(employeeService).deleteAllEmployees();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(this.URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertTrue(mvcResult.getResponse().getContentAsString().isEmpty(), "Http response is not as expected");
    }

    @Test
    @Order(9)
    void deleteEmployeeByID_whenEmployeePresent_shouldBeDeletedWithoutReturningAnything() throws Exception {

        Mockito.doNothing().when(employeeService).deleteEmployeeByID(1L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(this.URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertTrue(mvcResult.getResponse().getContentAsString().isEmpty(), "Http response is not as expected");
    }

    @Test
    @Order(10)
    void deleteEmployeeByID_whenEmployeeNotPresent_shouldThrowEmployeeNotFoundException() throws Exception {

        EmployeeNotFoundException employeeNotFoundException = new EmployeeNotFoundException("Unable to delete employee with id 1");

        Mockito.doThrow(employeeNotFoundException).when(employeeService).deleteEmployeeByID(1L);

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, employeeNotFoundException.getMessage());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(this.URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }
}