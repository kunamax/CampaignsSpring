package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.initializers.LoadCountries;
import com.example.CampaignsSpring.services.CountriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/countries")
public class CountriesController {
    private final LoadCountries loadCountries;
    private final CountriesService countriesService;

    public CountriesController(LoadCountries loadCountries, CountriesService countriesService) {
        this.loadCountries = loadCountries;
        this.countriesService = countriesService;
    }

    @GetMapping("/load")
    @Operation(
            summary = "Load countries from CSV",
            description = "Loads country data from a CSV file located in the classpath."
    )
    @ApiResponse(responseCode = "200", description = "Countries loaded successfully")
    @Tag(name = "Countries", description = "Endpoints for managing countries")
    public ResponseEntity<?> loadCountries() {
        try {
            ClassPathResource resource = new ClassPathResource("countries.csv");
            String filePath = resource.getFile().getAbsolutePath();
            loadCountries.loadCountries(filePath);
            return ResponseEntity.ok("Countries loaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error loading countries: " + e.getMessage());
        }
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
