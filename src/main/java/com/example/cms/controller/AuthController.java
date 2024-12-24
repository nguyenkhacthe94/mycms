package com.example.cms.controller;

import com.example.cms.model.User;
import com.example.cms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserService userService;


    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }




    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                // Session handling will be done here by Spring Security.
                return new ResponseEntity<>(Map.of("message","Logged in successfully."), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("message","Invalid credentials"), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            return new ResponseEntity<>(Map.of("message","Internal Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}