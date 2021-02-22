package com.game.kalah.mappers;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.gateways.models.BoardDocumentModel;
import com.game.kalah.interfaceadapters.gateways.models.GameDocumentModel;
import org.springframework.stereotype.Component;

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
}
