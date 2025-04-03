package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int id;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "TransactionDate", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "Status", nullable = false)
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "CampaignID", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    public enum TransactionStatus {
        CAMPAIGN_CREATED
    }

    public int getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public User getUser() {
        return user;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
