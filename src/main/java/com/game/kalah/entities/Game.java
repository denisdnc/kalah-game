package com.game.kalah.entities;

import java.util.UUID;

public class Game {

    private String id;
    private Board board;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.board = new Board();
    }

    public String getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }
}
