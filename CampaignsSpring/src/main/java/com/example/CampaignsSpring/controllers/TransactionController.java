package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all transactions", description = "Retrieves a list of all transactions")
    @Tag(name = "Transactions", description = "Endpoints for managing transactions")
    public ResponseEntity<?> getAllTransactions() {
        try {
            return ResponseEntity.ok(transactionService.getAllTransactions());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving transactions: " + e.getMessage());
        }
    }
}
