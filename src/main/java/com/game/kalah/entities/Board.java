package com.game.kalah.entities;

import com.game.kalah.exceptions.BusinessException;
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
     * each of the following pits, including his own House.
     * No stones are put in the opponent's' House.
     *
     * @param pitId Pits Id to start the movement
     */
    public void move(int pitId) {
        pits.stream()
                .filter(pit -> pit.getId() == pitId)
                .findFirst()
                .ifPresent(this::sowsToRight);
    }

    private void sowsToRight(Pit movingPit) {
        int pickedStones = movingPit.getStonesQuantity();

        if (pickedStones == 0) {
            throw new BusinessException("Invalid move, pit is empty");
        }

        if (PitType.HOUSE.equals(movingPit.getType())) {
            throw new BusinessException("Invalid move, cannot move from a HOSE type pit");
        }

        movingPit.empty();

        LoopingListIterator<Pit> loopingListIterator = new LoopingListIterator<>(pits);

        for (int i = 0; i < movingPit.getId(); i++) {
            loopingListIterator.next();
        }

        for (int i = 0; i < pickedStones; i++) {
            Pit currentPit = loopingListIterator.next();
            if (isOpponentHouse(movingPit, currentPit)) {
                currentPit = loopingListIterator.next();
            }
            currentPit.add(1);

            if (isLastStone(i, pickedStones)) {
                // After the movement still 1, meaning that was empty before
                if (currentPit.getOwner().equals(turn) &&
                        PitType.REGULAR.equals(currentPit.getType()) &&
                        currentPit.getStonesQuantity() == 1 &&
                        getOppositePit(currentPit).getStonesQuantity() > 0) {
                    Pit oppositePit = getOppositePit(currentPit);
                    int stonesCaptureQuantity = oppositePit.getStonesQuantity() + currentPit.getStonesQuantity();
                    Pit playerHouse = getPlayerHouse(turn);
                    playerHouse.setStonesQuantity(playerHouse.getStonesQuantity() + stonesCaptureQuantity);
                    currentPit.empty();
                    oppositePit.empty();
                }

                if (isPlayerHouse(currentPit, movingPit.getId())) {
                    this.turn = getCurrentPlayer(movingPit.getId());
                } else {
                    this.turn = getOpponent(movingPit.getId());
                }
            }
        }

    }

    private boolean isOpponentHouse(Pit movingPit, Pit currentPit) {
        return PitType.HOUSE.equals(currentPit.getType()) && !isPlayerHouse(currentPit, movingPit.getId());
    }

    private boolean isPlayerHouse(Pit currentPit, int movingPit) {
        return currentPit.getId() == getPlayerHouseIndex(movingPit);
    }

    private Pit getPlayerHouse(Player player) {
        return pits.stream()
                .filter(pit -> PitType.HOUSE.equals(pit.getType()) && pit.getOwner().equals(player))
                .findFirst()
                .orElse(null);
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

    private Pit getOppositePit(Pit currentPit) {
        return pits.stream()
                .filter(pit -> pit.getId() == (PITS_TOTAL_QUANTITY - currentPit.getId()))
                .findFirst()
                .orElse(null);
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
