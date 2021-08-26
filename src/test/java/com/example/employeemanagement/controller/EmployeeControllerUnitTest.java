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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    void addEmployee_whenEmployeeViolatingEntityPropertiesGiven_shouldThrowDataAccessException() throws Exception {

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

        DataAccessException dataAccessException = new DataIntegrityViolationException("Data violation occurred");

        Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class)))
                .thenThrow(dataAccessException);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(this.URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, dataAccessException.getMessage(), mvcResult.getRequest().getSession().getId());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertEquals(DataIntegrityViolationException.class, mvcResult.getResolvedException().getClass(), "Unexpected exception");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }

    @Test
    @Order(3)
    void addEmployee_whenEmployeeWithInvalidPropertiesGiven_shouldThrowMethodArgumentNotValidException() throws Exception {

        String inputJson = "    {\n" +
                "        \"name\": \"Sai Praveen\",\n" +
                "        \"designation\": \"Software Engineer\",\n" +
                "        \"department\": \"Backend\",\n" +
                "        \"dateOfJoining\": \"28/07/2021\",\n" +
                "        \"salary\": 50000,\n" +
                "        \"gender\": \"MALE\",\n" +
                "        \"email\": \"saipraveen.venturigmail.com\",\n" +
                "        \"mobileNumber\": \"9493843811\"\n" +
                "    }";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(this.URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertEquals(MethodArgumentNotValidException.class, mvcResult.getResolvedException().getClass(), "Unexpected exception");
    }

    @Test
    @Order(4)
    void addEmployee_whenEmployeeIsGivenInXMLFormat_shouldThrowHttpMediaTypeNotSupportedException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(this.URI)
                .contentType(MediaType.APPLICATION_XML)).andReturn();

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Content type 'application/xml' not supported", mvcResult.getRequest().getSession().getId());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertEquals(HttpMediaTypeNotSupportedException.class, mvcResult.getResolvedException().getClass(), "Unexpected exception");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }

    @Test
    @Order(5)
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
    @Order(6)
    void getEmployeeById_whenEmployeeNotPresent_shouldThrowEmployeeNotFoundException() throws Exception {
        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong()))
                .thenAnswer(parameter -> {
                    Long employeeId = (Long) parameter.getArguments()[0];
                    throw new EmployeeNotFoundException("Employee with employee id " + employeeId + " not found");
                });

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI + "/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, "Employee with employee id 1 not found", mvcResult.getRequest().getSession().getId());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertEquals(EmployeeNotFoundException.class, mvcResult.getResolvedException().getClass(), "Unexpected exception");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }

    @Test
    @Order(7)
    void getEmployeeById_whenIdNotPresentInRequest_shouldThrowMissingPathVariableException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI + "/id/ ")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, "Required URI template variable 'id' for method parameter type Long is present but converted to null", mvcResult.getRequest().getSession().getId());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertEquals(MissingPathVariableException.class, mvcResult.getResolvedException().getClass(), "Unexpected exception");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }

    @Test
    @Order(8)
    void getEmployeeById_whenIdNotPresentInRequest_shouldThrowHttpRequestMethodNotSupportedException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI + "/id")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED, "Request method 'GET' not supported", mvcResult.getRequest().getSession().getId());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertEquals(HttpRequestMethodNotSupportedException.class, mvcResult.getResolvedException().getClass(), "Unexpected exception");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }

    @Test
    @Order(8)
    void getEmployeeById_whenIdGivenIsNotLongType_shouldThrowTypeMismatchException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI + "/id/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, "Request method 'GET' not supported", mvcResult.getRequest().getSession().getId());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertEquals(MethodArgumentTypeMismatchException.class, mvcResult.getResolvedException().getClass(), "Unexpected exception");
    }

    @Test
    @Order(9)
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
    @Order(10)
    void getAllEmployees_whenEmployeesNotPresent_shouldReturnEmptyListOfEmployees() throws Exception {

        Mockito.when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(this.URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDto[].class).length, "Http response is not as expected");
    }

    @Test
    @Order(11)
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
    @Order(12)
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
    @Order(13)
    void deleteAllEmployees_inAllCases_shouldNotThrowExceptions() throws Exception {
        Mockito.doNothing().when(employeeService).deleteAllEmployees();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(this.URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertTrue(mvcResult.getResponse().getContentAsString().isEmpty(), "Http response is not as expected");
    }

    @Test
    @Order(14)
    void deleteEmployeeByID_whenEmployeePresent_shouldBeDeletedWithoutReturningAnything() throws Exception {

        Mockito.doNothing().when(employeeService).deleteEmployeeByID(1L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(this.URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus(), "Http status is not as expected");
        assertTrue(mvcResult.getResponse().getContentAsString().isEmpty(), "Http response is not as expected");
    }

    @Test
    @Order(15)
    void deleteEmployeeByID_whenEmployeeNotPresent_shouldThrowEmployeeNotFoundException() throws Exception {

        EmployeeNotFoundException employeeNotFoundException = new EmployeeNotFoundException("Unable to delete employee with id 1");

        Mockito.doThrow(employeeNotFoundException).when(employeeService).deleteEmployeeByID(1L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(this.URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        ErrorMessage expectedErrorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, employeeNotFoundException.getMessage(), mvcResult.getRequest().getSession().getId());
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorMessage);

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus(), "Http status is not ok");
        assertEquals(expectedJsonResponse, mvcResult.getResponse().getContentAsString(), "Http response is not as expected");
    }
}