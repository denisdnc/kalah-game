package com.game.kalah.entities;

import org.apache.commons.collections4.iterators.LoopingListIterator;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int PITS_TOTAL_QUANTITY = 14;
    private static final List<Integer> HOUSES_INDEXES = List.of(Player.SOUTH_PLAYER.getHouseIndex(), Player.NORTH_PLAYER.getHouseIndex());

    private final List<Pit> pits;
    private Player turn;

    public Board() {
        // ArrayList is used to maintain the elements insertion order and Search (get method) operations are fast in Arraylist (O(1))
        this.pits = new ArrayList<>();
        for (int i = 1; i <= PITS_TOTAL_QUANTITY; i++) {
            pits.add(new Pit(i,
                    HOUSES_INDEXES.contains(i) ? PitType.HOUSE : PitType.REGULAR,
                    i <= Player.SOUTH_PLAYER.getHouseIndex() ? Player.SOUTH_PLAYER : Player.NORTH_PLAYER));
        }
        this.turn = Player.SOUTH_PLAYER;
    }

    /**
     * Picks up all the stones in any of their own pits, and sows the stones on to the right, one in
     * each of the following pits, including his own Kalah.
     * No stones are put in the opponent's' Kalah.
     *
     * @param pitId Pits Id to start the movement
     */
    public void move(int pitId) {
        pits.stream()
                .filter(pit -> pit.getId() == pitId)
                .findFirst()
                .ifPresent(pit -> sowsToRight(pit));
    }

    private void sowsToRight(Pit movingPit) {
        int stonesQuantity = movingPit.getStonesQuantity();
        movingPit.setStonesQuantity(0);
        LoopingListIterator<Pit> loopingListIterator = new LoopingListIterator<>(pits);

        for (int i = 0; i < movingPit.getId(); i++) {
            loopingListIterator.next();
        }

        for (int i = 0; i < stonesQuantity; i++) {
            Pit currentPit = loopingListIterator.next();
            if (PitType.HOUSE.equals(currentPit.getType()) && !isPlayerHouse(currentPit, movingPit.getId())) {
                currentPit = loopingListIterator.next();
            }
            currentPit.add(1);

            if (isLastStone(i, stonesQuantity)) {
                if (currentPit.getStonesQuantity() == 0) {

                }

                if (isPlayerHouse(currentPit, movingPit.getId())) {
                    this.turn = getCurrentPlayer(movingPit.getId());
                } else {
                    this.turn = getOpponent(movingPit.getId());
                }
            }
        }

    }

    private boolean isPlayerHouse(Pit currentPit, int movingPit) {
        return currentPit.getId() == getPlayerHouseIndex(movingPit);
    }

    private int getPlayerHouseIndex(int movingPit) {
        return movingPit < Player.SOUTH_PLAYER.getHouseIndex() ? Player.SOUTH_PLAYER.getHouseIndex() : Player.NORTH_PLAYER.getHouseIndex();
    }

    private boolean isLastStone(int i, int stonesQuantity) {
        return i == stonesQuantity - 1;
    }

    private Player getCurrentPlayer(int movingPit) {
        return movingPit < Player.SOUTH_PLAYER.getHouseIndex() ? Player.SOUTH_PLAYER : Player.NORTH_PLAYER;
    }

    private Player getOpponent(int movingPit) {
        return getCurrentPlayer(movingPit).equals(Player.SOUTH_PLAYER) ? Player.NORTH_PLAYER : Player.SOUTH_PLAYER;
    }

    public List<Pit> getPits() {
        return pits;
    }

    public Player getTurn() {
        return turn;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }
}
