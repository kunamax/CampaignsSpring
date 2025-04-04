package com.example.CampaignsSpring.dto;

public class TransactionDTO {
    private int id;
    private double amount;
    private String date;
    private String transactionStatus;
    private int campaignId;
    private int userId;

    public TransactionDTO(int id, double amount, String date, String transactionStatus, int campaignId, int userId) {
        this.id = id;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.date = date;
        this.campaignId = campaignId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public int getUserId() {
        return userId;
    }
}
