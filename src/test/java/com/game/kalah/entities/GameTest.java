package com.game.kalah.entities;

import com.game.kalah.exceptions.BusinessException;
import com.game.kalah.fixtures.GameFixtures;
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

        // WHEN make a moves on Pits
        makeMoves(game, 1, 2, 8, 6);

        // THEN the last move should step over opponent's HOUSE and status should be
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(2, getPitStonesQuantity(1, pits)),
                () -> assertEquals(0, getPitStonesQuantity(2, pits)),
                () -> assertEquals(8, getPitStonesQuantity(3, pits)),
                () -> assertEquals(8, getPitStonesQuantity(4, pits)),
                () -> assertEquals(8, getPitStonesQuantity(5, pits)),
                () -> assertEquals(0, getPitStonesQuantity(6, pits)),
                // HOUSE pit
                () -> assertEquals(3, getPitStonesQuantity(7, pits)),
                () -> assertEquals(1, getPitStonesQuantity(8, pits)),
                () -> assertEquals(9, getPitStonesQuantity(9, pits)),
                () -> assertEquals(8, getPitStonesQuantity(10, pits)),
                () -> assertEquals(8, getPitStonesQuantity(11, pits)),
                () -> assertEquals(8, getPitStonesQuantity(12, pits)),
                () -> assertEquals(8, getPitStonesQuantity(13, pits)),
                // HOUSE pit
                () -> assertEquals(1, getPitStonesQuantity(14, pits))
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

        // WHEN make a moves on Pits
        makeMoves(game, 1, 2, 13, 2, 12, 6);

        // THEN the last move should step over opponent's HOUSE and status should be
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(3, getPitStonesQuantity(1, pits)),
                () -> assertEquals(1, getPitStonesQuantity(2, pits)),
                () -> assertEquals(11, getPitStonesQuantity(3, pits)),
                () -> assertEquals(10, getPitStonesQuantity(4, pits)),
                () -> assertEquals(9, getPitStonesQuantity(5, pits)),
                () -> assertEquals(0, getPitStonesQuantity(6, pits)),
                () -> assertEquals(3, getPitStonesQuantity(7, pits)),
                () -> assertEquals(8, getPitStonesQuantity(8, pits)),
                () -> assertEquals(8, getPitStonesQuantity(9, pits)),
                () -> assertEquals(7, getPitStonesQuantity(10, pits)),
                () -> assertEquals(7, getPitStonesQuantity(11, pits)),
                () -> assertEquals(1, getPitStonesQuantity(12, pits)),
                () -> assertEquals(2, getPitStonesQuantity(13, pits)),
                () -> assertEquals(2, getPitStonesQuantity(14, pits))
        );

        // AND stones total quantity should be 72
        assertEquals(72, game.getBoard()
                .getPits()
                .stream()
                .mapToInt(Pit::getStonesQuantity)
                .sum());
    }

    @Test
    @DisplayName("Scenario: captures other player's pit and his own stone - South Player")
    void capturesOtherPlayerPitSouthPlayer() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a moves on Pits
        makeMoves(game, 1, 2, 8, 1);

        // THEN the last move should captures other player's pit and his own stone
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(0, getPitStonesQuantity(1, pits)),
                () -> assertEquals(0, getPitStonesQuantity(2, pits)),
                () -> assertEquals(8, getPitStonesQuantity(3, pits)),
                () -> assertEquals(8, getPitStonesQuantity(4, pits)),
                () -> assertEquals(8, getPitStonesQuantity(5, pits)),
                () -> assertEquals(8, getPitStonesQuantity(6, pits)),
                () -> assertEquals(10, getPitStonesQuantity(7, pits)),
                () -> assertEquals(0, getPitStonesQuantity(8, pits)),
                () -> assertEquals(8, getPitStonesQuantity(9, pits)),
                () -> assertEquals(7, getPitStonesQuantity(10, pits)),
                () -> assertEquals(7, getPitStonesQuantity(11, pits)),
                () -> assertEquals(0, getPitStonesQuantity(12, pits)),
                () -> assertEquals(7, getPitStonesQuantity(13, pits)),
                () -> assertEquals(1, getPitStonesQuantity(14, pits))
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
    @DisplayName("Scenario: captures other player's pit and his own stone - North Player")
    void capturesOtherPlayerPitNorthPlayer() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a moves on Pits
        makeMoves(game, 1, 4, 8, 5, 9, 4, 8);

        // THEN the last move should captures other player's pit and his own stone
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(2, getPitStonesQuantity(1, pits)),
                () -> assertEquals(8, getPitStonesQuantity(2, pits)),
                () -> assertEquals(8, getPitStonesQuantity(3, pits)),
                () -> assertEquals(0, getPitStonesQuantity(4, pits)),
                () -> assertEquals(0, getPitStonesQuantity(5, pits)),
                () -> assertEquals(9, getPitStonesQuantity(6, pits)),
                () -> assertEquals(3, getPitStonesQuantity(7, pits)),
                () -> assertEquals(0, getPitStonesQuantity(8, pits)),
                () -> assertEquals(0, getPitStonesQuantity(9, pits)),
                () -> assertEquals(10, getPitStonesQuantity(10, pits)),
                () -> assertEquals(10, getPitStonesQuantity(11, pits)),
                () -> assertEquals(9, getPitStonesQuantity(12, pits)),
                () -> assertEquals(9, getPitStonesQuantity(13, pits)),
                () -> assertEquals(4, getPitStonesQuantity(14, pits))
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
    @DisplayName("Scenario: validate move from empty pit")
    void emptyPit() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a move from Pits
        game.getBoard().move(1);
        BusinessException exception = assertThrows(BusinessException.class, () -> game.getBoard().move(1));

        // THEN should throw error validating invalid move
        assertEquals("Invalid move, pit is empty", exception.getMessage());
    }

    @Test
    @DisplayName("Scenario: validate move from House")
    void moveFromHouse() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a move from a House
        game.getBoard().move(1);
        BusinessException exception = assertThrows(BusinessException.class, () -> game.getBoard().move(Player.SOUTH_PLAYER.getHouseIndex()));

        // THEN should throw error validating invalid move
        assertEquals("Invalid move, cannot move from a HOUSE type pit", exception.getMessage());
    }

    @Test
    @DisplayName("Scenario: invalid Pit")
    void invalidPit() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a move from a invalid pit id
        BusinessException exception = assertThrows(BusinessException.class, () -> game.getBoard().move(100));

        // THEN should throw error validating invalid move
        assertEquals("Pit now found: 100", exception.getMessage());
    }

    @Test
    @DisplayName("Scenario: invalid turn - SOUTH_PLAYER")
    void invalidTurnSouthPlayer() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a move from a invalid pit owner
        BusinessException exception = assertThrows(BusinessException.class, () -> game.getBoard().move(8));

        // THEN should throw error validating invalid move
        assertEquals("Invalid turn, player turn: SOUTH_PLAYER", exception.getMessage());
    }

    @Test
    @DisplayName("Scenario: invalid turn - NORTH_PLAYER")
    void invalidTurnNorthPlayer() {
        // GIVEN a new game
        Game game = new Game();

        // WHEN make a move from a invalid pit owner
        makeMoves(game, 1, 2);
        BusinessException exception = assertThrows(BusinessException.class, () -> game.getBoard().move(3));

        // THEN should throw error validating invalid move
        assertEquals("Invalid turn, player turn: NORTH_PLAYER", exception.getMessage());
    }

    @Test
    @DisplayName("Scenario: endgame - SOUTH_PLAYER wins")
    void endgameSouthPlayerWins() {
        // GIVEN a Game near to end
        Game game = GameFixtures.endgame();

        // WHEN make a move the Pits
        game.getBoard().move(6);

        // THEN the last move should captures all other player's pits
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(0, getPitStonesQuantity(1, pits)),
                () -> assertEquals(0, getPitStonesQuantity(2, pits)),
                () -> assertEquals(0, getPitStonesQuantity(3, pits)),
                () -> assertEquals(0, getPitStonesQuantity(4, pits)),
                () -> assertEquals(0, getPitStonesQuantity(5, pits)),
                () -> assertEquals(0, getPitStonesQuantity(6, pits)),
                () -> assertEquals(7, getPitStonesQuantity(7, pits)),
                () -> assertEquals(0, getPitStonesQuantity(8, pits)),
                () -> assertEquals(0, getPitStonesQuantity(9, pits)),
                () -> assertEquals(0, getPitStonesQuantity(10, pits)),
                () -> assertEquals(0, getPitStonesQuantity(11, pits)),
                () -> assertEquals(0, getPitStonesQuantity(12, pits)),
                () -> assertEquals(0, getPitStonesQuantity(13, pits)),
                () -> assertEquals(5, getPitStonesQuantity(14, pits))
        );

        // AND stones total quantity should be 72
        assertEquals(12, game.getBoard()
                .getPits()
                .stream()
                .mapToInt(Pit::getStonesQuantity)
                .sum());
    }

    @Test
    @DisplayName("Scenario: endgame - NORTH_PLAYER wins")
    void endgameNorthPlayerWins() {
        // GIVEN a Game near to end
        Game game = GameFixtures.endgame();
        game.getBoard().setTurn(Player.NORTH_PLAYER);

        // WHEN make a move the Pits
        game.getBoard().move(13);

        // THEN the last move should captures all other player's pits
        List<Pit> pits = game.getBoard()
                .getPits();

        assertAll(
                () -> assertEquals(0, getPitStonesQuantity(1, pits)),
                () -> assertEquals(0, getPitStonesQuantity(2, pits)),
                () -> assertEquals(0, getPitStonesQuantity(3, pits)),
                () -> assertEquals(0, getPitStonesQuantity(4, pits)),
                () -> assertEquals(0, getPitStonesQuantity(5, pits)),
                () -> assertEquals(0, getPitStonesQuantity(6, pits)),
                () -> assertEquals(5, getPitStonesQuantity(7, pits)),
                () -> assertEquals(0, getPitStonesQuantity(8, pits)),
                () -> assertEquals(0, getPitStonesQuantity(9, pits)),
                () -> assertEquals(0, getPitStonesQuantity(10, pits)),
                () -> assertEquals(0, getPitStonesQuantity(11, pits)),
                () -> assertEquals(0, getPitStonesQuantity(12, pits)),
                () -> assertEquals(0, getPitStonesQuantity(13, pits)),
                () -> assertEquals(7, getPitStonesQuantity(14, pits))
        );

        // AND stones total quantity should be 72
        assertEquals(12, game.getBoard()
                .getPits()
                .stream()
                .mapToInt(Pit::getStonesQuantity)
                .sum());
    }

    void makeMoves(Game game, int... pitIds) {
        for (int pitId : pitIds) {
            game.getBoard().move(pitId);
        }
    }

}
