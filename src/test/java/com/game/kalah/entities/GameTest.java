package com.game.kalah.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    @DisplayName("Scenario: new game")
    void newGame() {
        // WHEN a new Game is creted
        Game game = new Game();

        // THEN should have a board
        assertNotNull(game.getBoard());

        // AND the Board should have 14 pits
        assertEquals(game.getBoard().getPits().size(), 14);
    }

}
