package com.game.kalah.mappers;

import com.game.kalah.controllers.GameController;
import com.game.kalah.controllers.models.CreateGameResponseBody;
import com.game.kalah.controllers.models.GameStatusResponseBody;
import com.game.kalah.entities.Board;
import com.game.kalah.entities.Game;
import com.game.kalah.entities.Player;
import com.game.kalah.gateways.models.BoardDocumentModel;
import com.game.kalah.gateways.models.GameDocumentModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Utility class to map Game entity to different models.
 */
@Component
public class GameMapper {

    private PitMapper pitMapper;

    public GameMapper(PitMapper pitMapper) {
        this.pitMapper = pitMapper;
    }

    /**
     * Maps a Game entity to GameDocumentModel.
     *
     * @param game Game entity
     * @return GameDocumentModel
     */
    public GameDocumentModel fromEntityToDocumentModel(Game game) {
        return new GameDocumentModel(game.getId().toString(),
                new BoardDocumentModel(pitMapper.fromEntityListToDocumentModelList(game.getBoard().getPits()),
                        game.getBoard().getTurn().toString()));
    }

    /**
     * Maps a GameDocumentModel database model to Game entity.
     *
     * @param gameDocumentModel GameDocumentModel database model
     * @return Game entity
     */
    public Game fromDocumentModelToEntity(GameDocumentModel gameDocumentModel) {
        return new Game(UUID.fromString(gameDocumentModel.getId()),
                new Board(pitMapper.fromDocumentModelListToEntityList(gameDocumentModel.getBoard().getPits()),
                        Player.valueOf(gameDocumentModel.getBoard().getTurn())));
    }

    /**
     * Maps a Game entity to CreateGameResponseBody response body.
     *
     * @param game Game entity
     * @return CreateGameResponseBody response body
     */
    public CreateGameResponseBody fromEntityToCreateGameResponseBody(Game game) {
        return new CreateGameResponseBody(game.getId().toString(), buildUri(game));
    }

    /**
     * Maps a Game entity to GameStatusResponseBody response body.
     *
     * @param game Game entity
     * @return GameStatusResponseBody response body
     */
    public GameStatusResponseBody fromEntityToGameStatusResponseBody(Game game) {
        SortedMap<String, String> status = new TreeMap<>();
        game.getBoard().getPits()
                .forEach(pit -> status.put(Integer.toString(pit.getId()), Integer.toString(pit.getStonesQuantity())));
        return new GameStatusResponseBody(game.getId().toString(), buildUri(game), status);
    }

    private URI buildUri(Game game) {
        return MvcUriComponentsBuilder.fromController(GameController.class)
                .path("/{id}")
                .buildAndExpand(game.getId())
                .toUri();
    }
}
