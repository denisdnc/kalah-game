package com.game.kalah.interfaceadapters.controllers;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.controllers.bodies.CreateGameResponseBody;
import com.game.kalah.usecases.CreateNewGame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    private final CreateNewGame createNewGame;

    public GameController(CreateNewGame createNewGame) {
        this.createNewGame = createNewGame;
    }

    @PostMapping
    public ResponseEntity<CreateGameResponseBody> newGame() {
        Game game = createNewGame.execute();
        CreateGameResponseBody responseBody = new CreateGameResponseBody(game.getId().toString(),
                MvcUriComponentsBuilder.fromController(GameController.class)
                        .path("/{id}")
                        .buildAndExpand(game.getId())
                        .toUri());
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

}
