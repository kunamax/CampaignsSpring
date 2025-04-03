package com.example.CampaignsSpring.controllers;

import com.example.CampaignsSpring.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user_products")
    @Operation(summary = "Get all products for a user", description = "Retrieves a list of all products for a specific user")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @Tag(name = "Products", description = "Endpoints for managing products")
    public ResponseEntity<?> getUserProducts(int userId) {
        try {
            return ResponseEntity.ok(productService.getUserProducts(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving products: " + e.getMessage());
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

    @PutMapping("/update")
    @Operation(summary = "Update a product", description = "Updates an existing product in the system")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @Tag(name = "Products", description = "Endpoints for managing products")
    public ResponseEntity<?> updateProduct(int productId, String name, String description) {
        try {
            productService.updateProduct(productId, name, description);
            return ResponseEntity.ok("Product updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating product: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete a product", description = "Deletes a specific product by its ID")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @Tag(name = "Products", description = "Endpoints for managing products")
    public ResponseEntity<?> deleteProduct(int productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting product: " + e.getMessage());
        }
    }
}
