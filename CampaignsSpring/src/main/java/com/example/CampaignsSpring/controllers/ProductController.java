package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new product", description = "Creates a new product in the system")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @Tag(name = "Products", description = "Endpoints for managing products")
    public ResponseEntity<?> createProduct(String name, String description, int userId) {
        try {
            productService.createProduct(name, description, userId);
            return ResponseEntity.ok("Product created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating product: " + e.getMessage());
        }
    }

    @PostMapping("/all")
    @Operation(summary = "Get all products", description = "Retrieves a list of all products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @Tag(name = "Products", description = "Endpoints for managing products")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving products: " + e.getMessage());
        }
    }
}
