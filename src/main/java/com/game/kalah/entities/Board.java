package com.game.kalah.entities;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int PITS_QUANTITY = 14;
    private static final List<Integer> HOUSES_INDEXES = List.of(7, 14);

    private final List<Pit> pits;

    public Board() {
        this.pits = new ArrayList<>();
        for (int i = 1; i <= PITS_QUANTITY; i++) {
            pits.add(new Pit(i, HOUSES_INDEXES.contains(i) ? PitType.HOUSE : PitType.REGULAR));
        }
    }

    public List<Pit> getPits() {
        return pits;
    }
}
