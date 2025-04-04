package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.services.CountriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/countries")
public class CountriesController {
    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all countries",
            description = "Retrieves a list of all countries."
    )
    @ApiResponse(responseCode = "200", description = "Countries retrieved successfully")
    @Tag(name = "Countries", description = "Endpoints for managing countries")
    public ResponseEntity<?> getAllCountries() {
        try {
            return ResponseEntity.ok(countriesService.getAllCountries());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving countries: " + e.getMessage());
        }
    }
}
