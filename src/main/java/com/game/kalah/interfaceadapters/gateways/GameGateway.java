package com.game.kalah.interfaceadapters.gateways;

import com.game.kalah.entities.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameGateway {
    void save(Game game);

    Optional<Game> findById(UUID id);
}
