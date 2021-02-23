package com.game.kalah.usecases;

import com.game.kalah.entities.Game;

import java.util.UUID;

/**
 * Make a move on the Game and save the state.
 */
public interface MakeAMove {
    Game execute(UUID gameId, Integer movingPit);
}
