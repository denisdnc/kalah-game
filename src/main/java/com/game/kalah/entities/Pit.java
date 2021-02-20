package com.game.kalah.entities;

public class Pit {

    private static final Integer DEFAULT_STONES_QUANTITY = 6;

    private final Integer id;
    private final PitType type;
    private int stonesQuantity;

    public Pit(int id, PitType type) {
        this.id = id;
        this.type = type;
        this.stonesQuantity = PitType.REGULAR.equals(type) ? DEFAULT_STONES_QUANTITY : 0;
    }

    public void add(int stonesQuantity) {
        this.stonesQuantity = this.stonesQuantity + stonesQuantity;
    }

    public int getId() {
        return id;
    }

    public PitType getType() {
        return type;
    }

    public int getStonesQuantity() {
        return stonesQuantity;
    }

    public void setStonesQuantity(int stonesQuantity) {
        this.stonesQuantity = stonesQuantity;
    }
}
