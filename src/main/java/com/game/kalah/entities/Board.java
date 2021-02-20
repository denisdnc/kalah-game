package com.game.kalah.entities;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int PITS_QUANTITY = 14;
    private static final List<Integer> HOUSES_INDEXES = List.of(7, 14);

    private final List<Pit> pits;

    public Board() {
        // ArrayList is used to maintain the elements insertion order and Search (get method) operations are fast in Arraylist (O(1))
        this.pits = new ArrayList<>();
        for (int i = 1; i <= PITS_QUANTITY; i++) {
            pits.add(new Pit(i, HOUSES_INDEXES.contains(i) ? PitType.HOUSE : PitType.REGULAR));
        }
    }

    private void sowsToRight(int pitSource, int stonesQuantity) {
        for (int i = 1; i < pitSource + stonesQuantity; i++) {
            pits.get(i).add(1);
        }
    }

    public void move(int pitId) {
        pits.stream()
                .filter(pit -> pit.getId() == pitId)
                .findFirst()
                .ifPresent(pit -> {
                    sowsToRight(pit.getId(), pit.getStonesQuantity());
                    pit.setStonesQuantity(0);
                });
    }

    public List<Pit> getPits() {
        return pits;
    }
}
