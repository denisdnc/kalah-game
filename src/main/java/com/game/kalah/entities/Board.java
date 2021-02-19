package com.game.kalah.entities;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final Integer PITS_QUANTITY = 14;

    private List<Pit> pits;

    public Board() {
        this.pits = new ArrayList<>();
        for (int i = 1; i <= PITS_QUANTITY; i++) {
            pits.add(new Pit());
        }
    }

    public List<Pit> getPits() {
        return pits;
    }
}
