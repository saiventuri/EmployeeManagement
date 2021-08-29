package com.example.employeemanagement.controllerimpl;

import com.example.employeemanagement.controller.UserController;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.model.UserRequest;
import com.example.employeemanagement.model.UserResponse;
import com.example.employeemanagement.security.jwt.JwtUtil;
import com.example.employeemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Long> saveUser(User user) {
        Long id = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Override
    public ResponseEntity<UserResponse> loginUser(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassword()));
        String token = jwtUtil.generateToken(userRequest.getUserName());
        return ResponseEntity.ok(new UserResponse(token, "Success!"));
    }
}
