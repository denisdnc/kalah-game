package com.game.kalah.usecases.impl;

import com.game.kalah.entities.Game;
import com.game.kalah.gateways.GameGateway;
import com.game.kalah.usecases.CreateNewGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class CreateNewGameImpl implements CreateNewGame {

    Logger logger = LoggerFactory.getLogger(CreateNewGameImpl.class);

    private GameGateway gameGateway;

    public CreateNewGameImpl(GameGateway gameGateway) {
        this.gameGateway = gameGateway;
    }

    @Override
    public Game execute() {
        logger.info("Creating new game");

        Game game = new Game();
        gameGateway.save(game);

        logger.info("New Game created with id: {}", game.getId());

        return game;
    }
}
