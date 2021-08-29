package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.User;
import com.example.employeemanagement.model.UserResponse;
import com.example.employeemanagement.security.jwt.JwtUtil;
import com.example.employeemanagement.service.UserService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerUnitTest {

    private static final String SAVE_URI = "/user/save";
    private static final String LOGIN_URI = "/user/login";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Test
    @Order(1)
    void saveUser_whenValidUserGiven_userShouldBeSavedAndIdShouldBeReturned() throws Exception {

        String inputJson = "{\n" +
                "    \"name\": \"sai\",\n" +
                "    \"password\": \"praveen\",\n" +
                "    \"roles\": [{\"name\":\"ADMIN\"},{\"name\":\"USER\"}]\n" +
                "}";

        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(1L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(SAVE_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus(), "Http status is not created");
        assertEquals(1L, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Long.class), "Http response is not as expected");
    }

    @Test
    @Order(2)
    void loginUser_whenValidUserGiven_jwtTokenShouldBeGeneratedAndReturned() throws Exception {

        String mockUserName = "sai";

        String inputJson = "{\n" +
                "    \"userName\": \"" +
                mockUserName +
                "\",\n" +
                "    \"password\": \"*******\"\n" +
                "}";

        UserResponse expectedUserResponse = new UserResponse();
        expectedUserResponse.setMessage("Success!");
        expectedUserResponse.setToken(
                new JwtUtil("secret").generateToken(mockUserName)
        );

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        Mockito.when(jwtUtil.generateToken(Mockito.any(String.class))).thenReturn(expectedUserResponse.getToken());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Http status is not created");
        assertEquals(expectedUserResponse, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class), "Http response is not as expected");
    }
}