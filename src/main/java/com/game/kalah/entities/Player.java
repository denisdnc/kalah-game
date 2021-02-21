package com.game.kalah.entities;

public enum Player {
    SOUTH_PLAYER(7), NORTH_PLAYER(14);

    private int houseIndex;

    Player(int houseIndex) {
        this.houseIndex = houseIndex;
    }

    public int getHouseIndex() {
        return houseIndex;
    }
}
