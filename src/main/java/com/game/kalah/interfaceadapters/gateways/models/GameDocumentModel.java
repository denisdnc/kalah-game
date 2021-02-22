package com.game.kalah.interfaceadapters.gateways.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("games")
public class GameDocumentModel {

    private String gameId;
    private BoardDocumentModel board;

    public GameDocumentModel(String gameId, BoardDocumentModel board) {
        this.gameId = gameId;
        this.board = board;
    }

    public String getGameId() {
        return gameId;
    }

    public BoardDocumentModel getBoard() {
        return board;
    }
}
