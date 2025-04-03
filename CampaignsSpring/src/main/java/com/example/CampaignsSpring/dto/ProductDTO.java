package com.example.CampaignsSpring.dto;

public class ProductDTO {
    private int id;
    private String productName;
    private String productDescription;
    private String userName;

    public ProductDTO(int id, String productName, String productDescription, String userName) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getUserName() {
        return userName;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
