package com.example.CampaignsSpring.exceptions;

public class NotEnoughFunds extends RuntimeException {
    public NotEnoughFunds(String message) {
        super(message);
    }
}
