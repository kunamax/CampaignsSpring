package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.services.KeyWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/keyword")
@Tag(name = "Keywords", description = "Endpoints for managing keywords")
public class KeyWordController {
    private final KeyWordService keyWordService;

    public KeyWordController(KeyWordService keyWordService) {
        this.keyWordService = keyWordService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all keywords", description = "Retrieves a list of all keywords")
    @Tag(name = "Keywords", description = "Endpoints for managing keywords")
    public ResponseEntity<?> getAllKeywords() {
        try {
            return ResponseEntity.ok(keyWordService.getAllKeyWords());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving keywords: " + e.getMessage());
        }
    }
}
