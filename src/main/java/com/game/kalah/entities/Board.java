package com.game.kalah.entities;

import org.apache.commons.collections4.iterators.LoopingListIterator;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int PITS_TOTAL_QUANTITY = 14;
    private static final int PLAYER_1_HOUSE_INDEX = 7;
    private static final int PLAYER_2_HOUSE_INDEX = 14;
    private static final List<Integer> HOUSES_INDEXES = List.of(PLAYER_1_HOUSE_INDEX, PLAYER_2_HOUSE_INDEX);

    private final List<Pit> pits;

    public Board() {
        // ArrayList is used to maintain the elements insertion order and Search (get method) operations are fast in Arraylist (O(1))
        this.pits = new ArrayList<>();
        for (int i = 1; i <= PITS_TOTAL_QUANTITY; i++) {
            pits.add(new Pit(i, HOUSES_INDEXES.contains(i) ? PitType.HOUSE : PitType.REGULAR));
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

    private void sowsToRight(int movingPit, int stonesQuantity) {
        // TODO validate move from empty pit
        LoopingListIterator<Pit> loopingListIterator = new LoopingListIterator<>(pits);

        for (int i = 0; i < movingPit; i++) {
            loopingListIterator.next();
        }

        for (int i = 0; i < stonesQuantity; i++) {
            Pit currentPit = loopingListIterator.next();
            if (!PitType.REGULAR.equals(currentPit.getType()) && !isPlayerHouse(currentPit, movingPit)) {
                currentPit = loopingListIterator.next();
            }
            currentPit.add(1);
        }
    }

    private boolean isPlayerHouse(Pit currentPit, int movingPit) {
        return currentPit.getId() == playerHouseIndex(movingPit);
    }

    private int playerHouseIndex(int movingPit) {
        return movingPit < PLAYER_1_HOUSE_INDEX ? PLAYER_1_HOUSE_INDEX : PLAYER_2_HOUSE_INDEX;
    }

    public List<Pit> getPits() {
        return pits;
    }
}
