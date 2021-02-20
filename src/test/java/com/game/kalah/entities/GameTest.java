package com.game.kalah.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

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

        // WHEN make a move from Pit 1
        game.getBoard().move(1);

        // THEN pits status should be
        List<Pit> pits = game.getBoard()
                .getPits()
                .stream()
                .sorted(Comparator.comparingInt(Pit::getId))
                .collect(Collectors.toList());

        assertAll(
                () -> assertEquals(0, pits.get(0).getStonesQuantity()),
                () -> assertEquals(7, pits.get(2).getStonesQuantity()),
                () -> assertEquals(7, pits.get(3).getStonesQuantity()),
                () -> assertEquals(7, pits.get(1).getStonesQuantity()),
                () -> assertEquals(7, pits.get(4).getStonesQuantity()),
                () -> assertEquals(7, pits.get(5).getStonesQuantity()),
                // HOUSE pit
                () -> assertEquals(1, pits.get(6).getStonesQuantity()),
                () -> assertEquals(6, pits.get(7).getStonesQuantity()),
                () -> assertEquals(6, pits.get(8).getStonesQuantity()),
                () -> assertEquals(6, pits.get(9).getStonesQuantity()),
                () -> assertEquals(6, pits.get(10).getStonesQuantity()),
                () -> assertEquals(6, pits.get(11).getStonesQuantity()),
                () -> assertEquals(6, pits.get(12).getStonesQuantity()),
                // HOUSE pit
                () -> assertEquals(0, pits.get(13).getStonesQuantity())
        );
    }

}
