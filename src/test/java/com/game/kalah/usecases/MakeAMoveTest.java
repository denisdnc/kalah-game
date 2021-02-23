package com.game.kalah.usecases;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.gateways.GameGateway;
import com.game.kalah.usecases.impl.CreateNewGameImpl;
import com.game.kalah.usecases.impl.MakeAMoveImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MakeAMoveTest {

    /** dependencies */
    GameGateway gameGateway = mock(GameGateway.class);

    /** component to test */
    MakeAMove makeAMove = new MakeAMoveImpl(gameGateway);

    @Test
    @DisplayName("Make a move with success")
    public void makeAMove() {
        // GIVEN a game ID
        UUID gameId = UUID.randomUUID();

        // AND a pit id
        Integer pitId = 1;

        // AND a Game returned from gateway
        when(gameGateway.findById(gameId)).thenReturn(new Game());

        // WHEN make a move
        Game game = makeAMove.execute(gameId, pitId);

        // THEN should call Game Gateway to find a game
        verify(gameGateway, times(1)).findById(gameId);

        // AND call Game Gateway to save the game
        ArgumentCaptor<Game> saveGameArgument = ArgumentCaptor.forClass(Game.class);
        verify(gameGateway).save(saveGameArgument.capture());
        assertNotNull(saveGameArgument.getValue().getId());
        verify(gameGateway, times(1)).save(any(Game.class));

        // AND should return the updated game
        assertNotNull(game);
    }

}
