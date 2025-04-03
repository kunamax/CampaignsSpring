package com.example.CampaignsSpring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CampaignDTO {
    private int id;
    private String campaignName;
    private boolean status;
    private BigDecimal bidAmount;
    private BigDecimal remainingBudget;
    private LocalDate createdAt;
    private int radius;
    private String productName;
    private String userName;
    private List<String> keyWords;
    private String town;

    public CampaignDTO(int id, String campaignName, boolean status, BigDecimal bidAmount, BigDecimal remainingBudget, int radius, String productName, String userName, List<String> keyWords, LocalDate createdAt, String town) {
        this.id = id;
        this.campaignName = campaignName;
        this.status = status;
        this.bidAmount = bidAmount;
        this.remainingBudget = remainingBudget;
        this.radius = radius;
        this.productName = productName;
        this.userName = userName;
        this.keyWords = keyWords;
        this.createdAt = createdAt;
        this.town = town;
    }

    public int getId() {
        return id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public boolean isStatus() {
        return status;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public BigDecimal getRemainingBudget() {
        return remainingBudget;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public int getRadius() {
        return radius;
    }

    public String getProductName() {
        return productName;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }
}
