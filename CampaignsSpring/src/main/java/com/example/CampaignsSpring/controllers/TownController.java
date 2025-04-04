package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.services.TownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/town")
@Tag(name = "Towns", description = "Endpoints for managing towns")
public class TownController {
    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all towns", description = "Retrieves a list of all towns")
    @Tag(name = "Towns", description = "Endpoints for managing towns")
    public ResponseEntity<?> getAllTowns() {
        try {
            return ResponseEntity.ok(townService.getAllTowns());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving towns: " + e.getMessage());
        }
    }

}
