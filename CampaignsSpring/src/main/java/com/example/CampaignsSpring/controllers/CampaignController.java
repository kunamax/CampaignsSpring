package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.dto.CampaignDTO;
import com.example.CampaignsSpring.models.Campaign;
import com.example.CampaignsSpring.models.KeyWord;
import com.example.CampaignsSpring.services.CampaignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/campaigns")
@Tag(name = "Campaigns", description = "Endpoints for managing campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new campaign", description = "Creates a new marketing campaign for a product")
    @ApiResponse(responseCode = "201", description = "Campaign created successfully")
    @Tag(name = "Campaigns", description = "Endpoints for managing campaigns")
    public ResponseEntity<?> createCampaign(String campaignName, boolean status, BigDecimal bidAmount, BigDecimal remainingBudget, int radius, String town, int productId, int userId/*, List<String> keywords*/) {
        List<String> keywords = List.of("keyword1", "keyword2", "keyword3"); // Example keywords, replace with actual input
        try {
            if (keywords == null || keywords.isEmpty()) {
                return ResponseEntity.status(500).body("Keywords cannot be null or empty");
            }
            CampaignDTO campaign = campaignService.createCampaign(campaignName, status, bidAmount, remainingBudget, radius, productId, userId, town);
            campaignService.addKeywordsToCampaign(campaign.getId(), keywords);
            return ResponseEntity.ok("Campaign created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating campaign: " + e.getMessage());
        }
    }

    @GetMapping("/user_campaigns")
    @Operation(summary = "Get all campaigns for a user", description = "Retrieves a list of all campaigns for a specific user")
    @ApiResponse(responseCode = "200", description = "Campaigns retrieved successfully")
    @Tag(name = "Campaigns", description = "Endpoints for managing campaigns")
    public ResponseEntity<?> getUserCampaigns(int userId) {
        try {
            return ResponseEntity.ok(campaignService.getUserCampaigns(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving campaigns: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete a campaign", description = "Deletes a specific campaign by its ID")
    @ApiResponse(responseCode = "200", description = "Campaign deleted successfully")
    @Tag(name = "Campaigns", description = "Endpoints for managing campaigns")
    public ResponseEntity<?> deleteCampaign(int campaignId) {
        try {
            campaignService.deleteCampaign(campaignId);
            return ResponseEntity.ok("Campaign deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting campaign: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update a campaign", description = "Updates the details of an existing campaign")
    @ApiResponse(responseCode = "200", description = "Campaign updated successfully")
    @Tag(name = "Campaigns", description = "Endpoints for managing campaigns")
    public ResponseEntity<?> updateCampaign(int campaignId, String campaignName, boolean status, BigDecimal bidAmount, BigDecimal remainingBudget, int radius) {
        try {
            Campaign updatedCampaign = campaignService.updateCampaign(campaignId, campaignName, status, bidAmount, remainingBudget, radius);
            return ResponseEntity.ok("Campaign updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating campaign: " + e.getMessage());
        }
    }
}
