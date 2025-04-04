package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.exceptions.DuplicateException;
import com.example.CampaignsSpring.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/all")
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @Tag(name = "User", description = "Endpoints for managing users")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving users: " + e.getMessage());
        }
    }

    @GetMapping("/user_balance")
    @Operation(summary = "Get user balance", description = "Retrieves the balance of a specific user")
    @Tag(name = "User", description = "Endpoints for managing users")
    public ResponseEntity<?> getUserBalance(
            @RequestParam int userId) {
        try {
            return ResponseEntity.ok(userService.getUserBalance(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user balance: " + e.getMessage());
        }
    }
}
