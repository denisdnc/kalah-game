package com.game.kalah.gateways.models;

public class PitDocumentModel {
    private Integer id;
    private String type;
    private Integer stonesQuantity;
    private String owner;

    public PitDocumentModel(Integer id, String type, Integer stonesQuantity, String owner) {
        this.id = id;
        this.type = type;
        this.stonesQuantity = stonesQuantity;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getStonesQuantity() {
        return stonesQuantity;
    }

    public String getOwner() {
        return owner;
    }
}
