package com.game.kalah.entities;

public class Pit {

    private Integer id;
    private PitType type;

    public Pit(Integer id, PitType type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public PitType getType() {
        return type;
    }
}
