package com.game.kalah.entities;

import com.game.kalah.exceptions.BusinessException;
import org.apache.commons.collections4.iterators.LoopingListIterator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private static final int PITS_TOTAL_QUANTITY = 14;
    private static final List<Integer> HOUSES_INDEXES = List.of(Player.SOUTH_PLAYER.getHouseIndex(), Player.NORTH_PLAYER.getHouseIndex());

    private final List<Pit> pits;
    private Player turn;

    /**
     * Instantiates a Board with default properties.
     */
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
     * Instantiates a Board with given properties.
     *
     * @param pits List of Pit
     * @param turn Player turn
     */
    public Board(List<Pit> pits, Player turn) {
        this.pits = pits;
        this.turn = turn;
    }

    /**
     * Picks up all the stones in any of their own pits, and sows the stones on to the right, one in
     * each of the following pits, including his own House.
     * No stones are put in the opponent's' House.
     *
     * @param pitId Pits Id to start the movement
     */
    public void move(int pitId) {
        Pit movingPit = pits.stream()
                .filter(pit -> pit.getId() == pitId)
                .findFirst()
                .orElseThrow(() -> new BusinessException(String.format("Pit now found: %s", pitId)));
        validateMove(movingPit);
        sowsToRight(movingPit);
    }

    private void sowsToRight(Pit movingPit) {
        int pickedStones = movingPit.getStonesQuantity();
        movingPit.empty();

        // Create a iterator to circle the Pit list
        LoopingListIterator<Pit> loopingListIterator = new LoopingListIterator<>(pits);

        // Set iterator to the moving Pit
        for (int i = 0; i < movingPit.getId(); i++) {
            loopingListIterator.next();
        }

        for (int i = 0; i < pickedStones; i++) {
            Pit currentPit = loopingListIterator.next();
            if (isOpponentHouse(movingPit, currentPit)) {
                // Skip opponent house
                currentPit = loopingListIterator.next();
            }
            currentPit.add(1);

            if (isLastStone(i, pickedStones)) {
                moveLastStone(movingPit, currentPit);
            }
        }

    }

    /**
     * In the last stone must set the next turn and capture opponent stones if allowed.
     *
     * @param movingPit the Pit where the move started
     * @param currentPit the Pit being sowed
     */
    private void moveLastStone(Pit movingPit, Pit currentPit) {
        if (isAllowedToCatch(currentPit)) {
            capturesOppositeStones(currentPit);
        }
        setNextTurn(movingPit, currentPit);
    }

    private boolean isAllowedToCatch(Pit currentPit) {
        return currentPit.getOwner().equals(turn) &&
                PitType.REGULAR.equals(currentPit.getType()) &&
                currentPit.getStonesQuantity() == 1 &&
                getOppositePit(currentPit).getStonesQuantity() > 0;
    }

    /**
     * The player captures this stone and all stones in the opposite pit (the other players' pit) and puts them in his own House.
     *
     * @param currentPit the Pit being sowed
     */
    private void capturesOppositeStones(Pit currentPit) {
        Pit oppositePit = getOppositePit(currentPit);
        int stonesCaptureQuantity = oppositePit.getStonesQuantity() + currentPit.getStonesQuantity();
        Pit playerHouse = getPlayerHouse(turn);
        playerHouse.add(stonesCaptureQuantity);
        currentPit.empty();
        oppositePit.empty();
    }

    private void setNextTurn(Pit movingPit, Pit currentPit) {
        if (!(PitType.HOUSE.equals(currentPit.getType()) && currentPit.getOwner().equals(turn))) {
            this.turn = getOpponent(movingPit.getId());
        }
    }

    private void validateMove(Pit movingPit) {
        if (movingPit.getStonesQuantity() == 0) {
            throw new BusinessException("Invalid move, pit is empty");
        }

        if (PitType.HOUSE.equals(movingPit.getType())) {
            throw new BusinessException("Invalid move, cannot move from a HOSE type pit");
        }

        if (!movingPit.getOwner().equals(turn)) {
            throw new BusinessException("Invalid turn, player turn: SOUTH_PLAYER");
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

    private Player getOpponent(int movingPit) {
        return turn.equals(Player.SOUTH_PLAYER) ? Player.NORTH_PLAYER : Player.SOUTH_PLAYER;
    }

    private Pit getOppositePit(Pit currentPit) {
        return pits.stream()
                .filter(pit -> pit.getId() == (PITS_TOTAL_QUANTITY - currentPit.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Always returns Pits sorted by ID to guarantee right distribution of stones.
     * @return List of sorted Pits
     */
    public List<Pit> getPits() {
        return pits.stream().sorted(Comparator.comparingInt(Pit::getId)).collect(Collectors.toList());
    }

    public Player getTurn() {
        return turn;
    }
}
