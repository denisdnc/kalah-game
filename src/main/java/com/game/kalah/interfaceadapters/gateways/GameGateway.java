package com.game.kalah.interfaceadapters.gateways;

import com.game.kalah.entities.Game;

import java.util.UUID;

public interface GameGateway {
    void save(Game game);

    Game findById(UUID id);
}
