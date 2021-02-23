package com.game.kalah.controllers;

import com.game.kalah.controllers.models.CreateGameResponseBody;
import com.game.kalah.controllers.models.GameStatusResponseBody;
import com.game.kalah.entities.Game;
import com.game.kalah.mappers.GameMapper;
import com.game.kalah.usecases.CreateNewGame;
import com.game.kalah.usecases.MakeAMove;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Game controller to make actions on Game.
 */
@RestController
@RequestMapping(value = "/games")
public class GameController {

    private final CreateNewGame createNewGame;
    private final MakeAMove makeAMove;
    private final GameMapper gameMapper;

    public GameController(CreateNewGame createNewGame, MakeAMove makeAMove, GameMapper gameMapper) {
        this.createNewGame = createNewGame;
        this.makeAMove = makeAMove;
        this.gameMapper = gameMapper;
    }

    /**
     * Creates a new Game and save.
     *
     * @return new Game
     */
    @PostMapping
    public ResponseEntity<CreateGameResponseBody> newGame() {
        Game game = createNewGame.execute();
        return new ResponseEntity<>(gameMapper.fromEntityToCreateGameResponseBody(game), HttpStatus.CREATED);
    }

    /**
     * Make a move on a Game's Pit
     *
     * @param gameId the generated Game ID
     * @param pitId  Pit ID 1 to 14
     * @return Updated Game
     */
    @PutMapping(value = "/{gameId}/pits/{pitId}")
    public ResponseEntity<GameStatusResponseBody> makeAMove(@PathVariable("gameId") String gameId,
                                                            @PathVariable("pitId") Integer pitId) {
        Game game = makeAMove.execute(UUID.fromString(gameId), pitId);
        return new ResponseEntity<>(gameMapper.fromEntityToGameStatusResponseBody(game), HttpStatus.OK);
    }

}
