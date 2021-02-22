package com.game.kalah.interfaceadapters.gateways.models;

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
}
