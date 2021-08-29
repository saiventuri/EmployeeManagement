package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.User;
import com.example.employeemanagement.model.UserRequest;
import com.example.employeemanagement.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserController {

    @PostMapping("/save")
    ResponseEntity<Long> saveUser(@RequestBody User user);

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest);

}
