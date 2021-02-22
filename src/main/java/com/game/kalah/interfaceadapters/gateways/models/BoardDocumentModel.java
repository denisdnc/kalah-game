package com.game.kalah.interfaceadapters.gateways.models;

import java.util.List;

public class BoardDocumentModel {
    List<PitDocumentModel> pits;
    String turn;

    public BoardDocumentModel(List<PitDocumentModel> pits, String turn) {
        this.pits = pits;
        this.turn = turn;
    }

    public List<PitDocumentModel> getPits() {
        return pits;
    }

    public String getTurn() {
        return turn;
    }
}
