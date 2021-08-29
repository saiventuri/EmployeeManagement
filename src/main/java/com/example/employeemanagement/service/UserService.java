package com.example.employeemanagement.service;

import com.example.employeemanagement.model.User;

import java.util.Optional;

public interface UserService {

    Long saveUser(User user);

    Optional<User> findUserByName(String userName);
}
