package com.game.kalah.entities;

public class Pit {

    private static final Integer DEFAULT_STONES_QUANTITY = 6;

    private Integer id;
    private PitType type;
    private Integer stonesQuantity;

    public Pit(Integer id, PitType type) {
        this.id = id;
        this.type = type;
        this.stonesQuantity = PitType.REGULAR.equals(type) ? DEFAULT_STONES_QUANTITY : 0;
    }

    public Integer getId() {
        return id;
    }

    public PitType getType() {
        return type;
    }

    public Integer getStonesQuantity() {
        return stonesQuantity;
    }
}
