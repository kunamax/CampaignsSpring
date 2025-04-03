package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

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
    private String status;

    @ManyToOne
    @JoinColumn(name = "CampaignID", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;
}
