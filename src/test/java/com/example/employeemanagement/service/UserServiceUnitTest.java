package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.RoleRepository;
import com.example.employeemanagement.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Order(1)
    void saveUser_whenValidUserWithNoRolesGiven_saveAndReturnUserId() {
        User mockUser = User.builder()
                .id(1L)
                .name("Sai")
                .password("******")
                .roles(null)
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(parameter -> (User) parameter.getArguments()[0]);
        assertEquals(1L, userService.saveUser(mockUser));
    }

    @Test
    @Order(2)
    void saveUser_whenValidUserWithRolesGiven_saveAndReturnUserId() {

        Role mockRole1 = new Role(1L, "ADMIN");
        Role mockRole2 = new Role(2L, "USER");

        User mockUser = User.builder()
                .id(1L)
                .name("Sai")
                .password("******")
                .roles(new HashSet<>(Arrays.asList(mockRole1, mockRole2)))
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(parameter -> (User) parameter.getArguments()[0]);

        Mockito.when(roleRepository.findByName(Mockito.any(String.class))).thenAnswer(parameter -> {
            String roleName = (String) parameter.getArguments()[0];
            if (roleName.equals(mockRole1.getName())) {
                return Optional.of(mockRole1);
            } else {
                return Optional.empty();
            }
        });
        assertEquals(1L, userService.saveUser(mockUser));
    }
}