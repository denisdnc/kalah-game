package com.game.kalah.usecases.impl;

import com.game.kalah.entities.Game;
import com.game.kalah.exceptions.BusinessException;
import com.game.kalah.gateways.GameGateway;
import com.game.kalah.usecases.MakeAMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MakeAMoveImpl implements MakeAMove {

    Logger logger = LoggerFactory.getLogger(MakeAMoveImpl.class);

    private GameGateway gameGateway;

    public MakeAMoveImpl(GameGateway gameGateway) {
        this.gameGateway = gameGateway;
    }

    @Override
    public Game execute(UUID gameId, Integer pitId) {
        logger.info("Making a move on game: {} and pit id: {}", gameId.toString(), pitId);
        Game currentGame = gameGateway.findById(gameId)
                .orElseThrow(() -> new BusinessException(String.format("Game not found: %s", gameId.toString())));
        currentGame.getBoard().move(pitId);
        gameGateway.save(currentGame);
        logger.info("Game updated: {} and pit id: {}", gameId.toString(), pitId);
        return currentGame;
    }
}
