package com.game.kalah.entities;

public class Pit {

    private static final Integer DEFAULT_STONES_QUANTITY = 6;

    private final int id;
    private final PitType type;
    private int stonesQuantity;
    private final Player owner;

    /**
     * Instantiates a new Pit with default properties.
     *
     * @param id Pit's id
     * @param type PitType
     * @param owner Pit's owner
     */
    public Pit(int id, PitType type, Player owner) {
        this.id = id;
        this.type = type;
        this.stonesQuantity = PitType.REGULAR.equals(type) ? DEFAULT_STONES_QUANTITY : 0;
        this.owner = owner;
    }

    /**
     * Instantiates a new Pit with default properties.
     *
     * @param id Pit's id
     * @param type PitType
     * @param stonesQuantity stones quantity
     * @param owner Pit's owner
     */
    public Pit(int id, PitType type, int stonesQuantity, Player owner) {
        this.id = id;
        this.type = type;
        this.stonesQuantity = stonesQuantity;
        this.owner = owner;
    }

    /**
     * Adds a quantity to the current stones quantity.
     *
     * @param stonesQuantity stones to be added in the current quantity
     */
    public void add(int stonesQuantity) {
        this.stonesQuantity = this.stonesQuantity + stonesQuantity;
    }

    /**
     * Empty the stones quantity of the Pit.
     */
    public void empty() {
        this.stonesQuantity = 0;
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

    public Player getOwner() {
        return owner;
    }
}
