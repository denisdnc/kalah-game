package com.game.kalah.usecases;

import com.game.kalah.entities.Game;

/**
 * Creates a new Game with default properties and save the state.
 */
public interface CreateNewGame {
    Game execute();
}
