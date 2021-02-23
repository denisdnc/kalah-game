package com.game.kalah.interfaceadapters.controllers;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.controllers.models.CreateGameResponseBody;
import com.game.kalah.interfaceadapters.controllers.models.GameStatusResponseBody;
import com.game.kalah.usecases.CreateNewGame;
import com.game.kalah.usecases.MakeAMove;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    private final CreateNewGame createNewGame;
    private final MakeAMove makeAMove;

    public GameController(CreateNewGame createNewGame, MakeAMove makeAMove) {
        this.createNewGame = createNewGame;
        this.makeAMove = makeAMove;
    }

    @PostMapping
    public ResponseEntity<CreateGameResponseBody> newGame() {
        Game game = createNewGame.execute();
        CreateGameResponseBody responseBody = new CreateGameResponseBody(game.getId().toString(), buildUri(game));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{gameId}/pits/{pitId}")
    public ResponseEntity<GameStatusResponseBody> makeAMove(@PathVariable("gameId") String gameId,
                                                            @PathVariable("pitId") Integer pitId) {
        Game game = makeAMove.execute(UUID.fromString(gameId), pitId);
        return new ResponseEntity<>(mapGameStatusResponseBodyFromEntity(game), HttpStatus.OK);
    }

    private GameStatusResponseBody mapGameStatusResponseBodyFromEntity(Game game) {
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
