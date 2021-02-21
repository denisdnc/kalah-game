package com.game.kalah.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    @DisplayName("Scenario: new game")
    void newGame() {
        // WHEN a new Game is created
        Game game = new Game();

        // THEN the Game should have an ID
        assertNotNull(game.getId());

        // AND should have a board
        assertNotNull(game.getBoard());

        // AND the Board should have 14 pits
        assertEquals(14, game.getBoard().getPits().size());

        // AND all REGULAR Pits should have 6 stones
        assertTrue(game.getBoard()
                .getPits()
                .stream()
                .filter(pit -> PitType.REGULAR.equals(pit.getType()))
                .allMatch(pit -> pit.getStonesQuantity() == 6));

        // AND should have 2 houses
        assertEquals(2, game.getBoard()
                .getPits()
                .stream()
                .filter(pit -> PitType.HOUSE.equals(pit.getType())).count());

        // AND pits with ids 7 and 14 should be HOUSE type
        assertTrue(game.getBoard()
                .getPits()
                .stream()
                .filter(pit -> PitType.HOUSE.equals(pit.getType()))
                .anyMatch(pit -> pit.getId() == 7));
        assertTrue(game.getBoard()
                .getPits()
                .stream()
                .filter(pit -> PitType.HOUSE.equals(pit.getType()))
                .anyMatch(pit -> pit.getId() == 14));

        // AND all the HOUSE Pits should have 0 stones
        assertTrue(game.getBoard()
                .getPits()
                .stream()
                .filter(pit -> PitType.HOUSE.equals(pit.getType()))
                .allMatch(pit -> pit.getStonesQuantity() == 0));

        // AND stones total quantity should be 72
        assertEquals(72, game.getBoard()
                .getPits()
                .stream()
                .mapToInt(Pit::getStonesQuantity)
                .sum());

        // AND default turn is SOUTH_PLAYER
        assertEquals(Player.SOUTH_PLAYER, game.getBoard().getTurn());

        // AND the pits should have the following owners
        assertTrue(game.getBoard().getPits().stream()
                .filter(pit -> pit.getId() <= 7)
                .allMatch(pit -> pit.getOwner().equals(Player.SOUTH_PLAYER)));
        assertTrue(game.getBoard().getPits().stream()
                .filter(pit -> pit.getId() > 7)
                .allMatch(pit -> pit.getOwner().equals(Player.NORTH_PLAYER)));
    }

    int getPitStonesQuantity(int pitId, List<Pit> pits) {
        return pits.stream().filter(pit -> pit.getId() == pitId).mapToInt(Pit::getStonesQuantity).sum();
    }

    @Test
    @DisplayName("Scenario: make a regular move")
    void regularMove() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a move from Pit 1
        game.getBoard().move(1);

        // THEN pits status should be
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(0, getPitStonesQuantity(1, pits)),
                () -> assertEquals(7, getPitStonesQuantity(2, pits)),
                () -> assertEquals(7, getPitStonesQuantity(3, pits)),
                () -> assertEquals(7, getPitStonesQuantity(4, pits)),
                () -> assertEquals(7, getPitStonesQuantity(5, pits)),
                () -> assertEquals(7, getPitStonesQuantity(6, pits)),
                // HOUSE pit
                () -> assertEquals(1, getPitStonesQuantity(7, pits)),
                () -> assertEquals(6, getPitStonesQuantity(8, pits)),
                () -> assertEquals(6, getPitStonesQuantity(9, pits)),
                () -> assertEquals(6, getPitStonesQuantity(10, pits)),
                () -> assertEquals(6, getPitStonesQuantity(11, pits)),
                () -> assertEquals(6, getPitStonesQuantity(12, pits)),
                () -> assertEquals(6, getPitStonesQuantity(13, pits)),
                // HOUSE pit
                () -> assertEquals(0, getPitStonesQuantity(14, pits))
        );

        // AND stones total quantity should be 72
        assertEquals(72, game.getBoard()
                .getPits()
                .stream()
                .mapToInt(Pit::getStonesQuantity)
                .sum());

        // AND the turn is from the SOUTH_PLAYER
        assertEquals(Player.SOUTH_PLAYER, game.getBoard().getTurn());
    }

    @Test
    @DisplayName("Scenario: step over opponent's HOUSE")
    void stepOverOpponentHouse() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a moves on Pits 1, 13, 2, 12 and 6
        game.getBoard().move(1);
        game.getBoard().move(13);
        game.getBoard().move(2);
        game.getBoard().move(12);
        game.getBoard().move(6);

        // THEN the last move should step over opponent's HOUSE and status should be
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(3, getPitStonesQuantity(1, pits)),
                () -> assertEquals(1, getPitStonesQuantity(2, pits)),
                () -> assertEquals(10, getPitStonesQuantity(3, pits)),
                () -> assertEquals(10, getPitStonesQuantity(4, pits)),
                () -> assertEquals(9, getPitStonesQuantity(5, pits)),
                () -> assertEquals(0, getPitStonesQuantity(6, pits)),
                // HOUSE pit
                () -> assertEquals(3, getPitStonesQuantity(7, pits)),
                () -> assertEquals(8, getPitStonesQuantity(8, pits)),
                () -> assertEquals(8, getPitStonesQuantity(9, pits)),
                () -> assertEquals(8, getPitStonesQuantity(10, pits)),
                () -> assertEquals(7, getPitStonesQuantity(11, pits)),
                () -> assertEquals(1, getPitStonesQuantity(12, pits)),
                () -> assertEquals(2, getPitStonesQuantity(13, pits)),
                // HOUSE pit
                () -> assertEquals(2, getPitStonesQuantity(14, pits))
        );

        // AND stones total quantity should be 72
        assertEquals(72, game.getBoard()
                .getPits()
                .stream()
                .mapToInt(Pit::getStonesQuantity)
                .sum());

        // AND the turn is from the SOUTH_PLAYER
        assertEquals(Player.NORTH_PLAYER, game.getBoard().getTurn());
    }

    @Test
    @DisplayName("Scenario: step over opponent's HOUSE - twice")
    void stepOverOpponentHouseTwice() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a moves on Pits 1, 13, 2, 12, 6 and 4
        game.getBoard().move(1);
        game.getBoard().move(13);
        game.getBoard().move(2);
        game.getBoard().move(12);
        game.getBoard().move(6);
        game.getBoard().move(4);

        // THEN the last move should step over opponent's HOUSE and status should be
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(4, getPitStonesQuantity(1, pits)),
                () -> assertEquals(1, getPitStonesQuantity(2, pits)),
                () -> assertEquals(10, getPitStonesQuantity(3, pits)),
                () -> assertEquals(0, getPitStonesQuantity(4, pits)),
                () -> assertEquals(10, getPitStonesQuantity(5, pits)),
                () -> assertEquals(1, getPitStonesQuantity(6, pits)),
                // HOUSE pit
                () -> assertEquals(4, getPitStonesQuantity(7, pits)),
                () -> assertEquals(9, getPitStonesQuantity(8, pits)),
                () -> assertEquals(9, getPitStonesQuantity(9, pits)),
                () -> assertEquals(9, getPitStonesQuantity(10, pits)),
                () -> assertEquals(8, getPitStonesQuantity(11, pits)),
                () -> assertEquals(2, getPitStonesQuantity(12, pits)),
                () -> assertEquals(3, getPitStonesQuantity(13, pits)),
                // HOUSE pit
                () -> assertEquals(2, getPitStonesQuantity(14, pits))
        );

        // AND stones total quantity should be 72
        assertEquals(72, game.getBoard()
                .getPits()
                .stream()
                .mapToInt(Pit::getStonesQuantity)
                .sum());
    }

//    @Test
//    @DisplayName("Scenario: captures other player's pit")
//    void captureOtherPlayerPit() {
//        // GIVEN a new game
//        Game game = new Game();
//
//        // WHEN make a moves on Pits 1, 2, 8 and 1
//        game.getBoard().move(1);
//        game.getBoard().move(2);
//        game.getBoard().move(8);
//        game.getBoard().move(1);
//
//        // THEN the last move should captures other player's pit and his own stone
//        List<Pit> pits = game.getBoard()
//                .getPits();
//
//        assertAll(
//                () -> assertEquals(0, getPitStonesQuantity(1, pits)),
//                () -> assertEquals(0, getPitStonesQuantity(2, pits)),
//                () -> assertEquals(8, getPitStonesQuantity(3, pits)),
//                () -> assertEquals(8, getPitStonesQuantity(4, pits)),
//                () -> assertEquals(8, getPitStonesQuantity(5, pits)),
//                () -> assertEquals(8, getPitStonesQuantity(6, pits)),
//                // HOUSE pit
//                () -> assertEquals(10, getPitStonesQuantity(7, pits)),
//                () -> assertEquals(0, getPitStonesQuantity(8, pits)),
//                () -> assertEquals(8, getPitStonesQuantity(9, pits)),
//                () -> assertEquals(7, getPitStonesQuantity(10, pits)),
//                () -> assertEquals(7, getPitStonesQuantity(11, pits)),
//                () -> assertEquals(0, getPitStonesQuantity(12, pits)),
//                () -> assertEquals(7, getPitStonesQuantity(13, pits)),
//                // HOUSE pit
//                () -> assertEquals(1, getPitStonesQuantity(14, pits))
//        );
//
//        // AND stones total quantity should be 72
//        assertEquals(72, game.getBoard()
//                .getPits()
//                .stream()
//                .mapToInt(Pit::getStonesQuantity)
//                .sum());
//
//        // AND the turn is from the SOUTH_PLAYER
//        assertEquals(Player.NORTH_PLAYER, game.getBoard().getTurn());
//    }

    // TODO validate move from empty pit
    // TODO validate move from house

}
