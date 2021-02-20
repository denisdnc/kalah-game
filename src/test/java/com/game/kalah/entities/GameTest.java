package com.game.kalah.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    @DisplayName("Scenario: make a regular move")
    void move() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN 
    }

}
