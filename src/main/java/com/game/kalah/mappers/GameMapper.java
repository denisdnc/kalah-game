package com.game.kalah.mappers;

import com.game.kalah.entities.Board;
import com.game.kalah.entities.Game;
import com.game.kalah.entities.Player;
import com.game.kalah.interfaceadapters.gateways.models.BoardDocumentModel;
import com.game.kalah.interfaceadapters.gateways.models.GameDocumentModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameMapper {

    private PitMapper pitMapper;

    public GameMapper(PitMapper pitMapper) {
        this.pitMapper = pitMapper;
    }

    public GameDocumentModel fromEntityToDocumentModel(Game game) {
        return new GameDocumentModel(game.getId().toString(),
                new BoardDocumentModel(pitMapper.fromEntityListToDocumentModelList(game.getBoard().getPits()),
                        game.getBoard().getTurn().toString()));
    }

    public Game fromDocumentModelToEntity(GameDocumentModel gameDocumentModel) {
        return new Game(UUID.fromString(gameDocumentModel.getId()),
                new Board(pitMapper.fromDocumentModelListToEntityList(gameDocumentModel.getBoard().getPits()),
                        Player.valueOf(gameDocumentModel.getBoard().getTurn())));
    }
}
