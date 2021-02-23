package com.game.kalah.interfaceadapters.gateways.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("games")
public class GameDocumentModel {

    @Id
    private String id;
    private BoardDocumentModel board;

    public GameDocumentModel(String id, BoardDocumentModel board) {
        this.id = id;
        this.board = board;
    }

    public String getId() {
        return id;
    }

    public BoardDocumentModel getBoard() {
        return board;
    }
}
