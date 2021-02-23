package com.game.kalah.entities;

import java.util.UUID;

public class Game {

    private UUID id;
    private Board board;

    /**
     * Instantiates a new Game with default properties:
     */
    public Game() {
        this.id = UUID.randomUUID();
        this.board = new Board();
    }

    /**
     * Instantiates a new Game given the properties.
     *
     * @param id    game's id
     * @param board game Board
     */
    public Game(UUID id, Board board) {
        this.id = id;
        this.board = board;
    }

    public UUID getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }
}
