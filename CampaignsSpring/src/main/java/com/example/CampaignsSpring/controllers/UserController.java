package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.exceptions.DuplicateException;
import com.example.CampaignsSpring.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    @Tag(name = "User", description = "Endpoints for managing users")
    public ResponseEntity<?> createUser(String name, String email, String password, String phoneNumber, BigDecimal balance, String countryISO2Code) {
        try {
            userService.createUser(name, email, password, phoneNumber, balance, countryISO2Code);
            return ResponseEntity.ok("User added successfully.");
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
