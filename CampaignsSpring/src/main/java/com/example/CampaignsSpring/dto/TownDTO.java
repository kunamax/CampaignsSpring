package com.example.CampaignsSpring.dto;

public class TownDTO {
    private int id;
    private String townName;

    public TownDTO(int id, String townName) {
        this.id = id;
        this.townName = townName;
    }

    public int getId() {
        return id;
    }

    public String getTownName() {
        return townName;
    }
}
