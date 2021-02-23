package com.game.kalah.usecases;

import com.game.kalah.entities.Game;

import java.util.UUID;

public interface MakeAMove {
    Game execute(UUID gameId, Integer movingPit);
}
