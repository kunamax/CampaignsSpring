package com.example.CampaignsSpring.dto;

public class KeyWordDTO {
    private int id;
    private String keyWordName;

    public KeyWordDTO(int id, String keyWordName) {
        this.id = id;
        this.keyWordName = keyWordName;
    }

    public int getId() {
        return id;
    }

    public String getKeyWordName() {
        return keyWordName;
    }
}
