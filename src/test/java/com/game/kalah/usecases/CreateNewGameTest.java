package com.game.kalah.usecases;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.gateways.GameGateway;
import com.game.kalah.usecases.impl.CreateNewGameImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CreateNewGameTest {

    /** dependencies */
    GameGateway gameGateway = mock(GameGateway.class);

    /** component to test */
    CreateNewGame createNewGame = new CreateNewGameImpl(gameGateway);

    @Test
    @DisplayName("Create a new game with success")
    public void createNewGame() {
        // WHEN create a new game
        Game game = createNewGame.execute();

        // THEN should return the created game
        assertNotNull(game);
        assertNotNull(game.getId());

        // AND call Game Gateway to save the game
        ArgumentCaptor<Game> saveGameArgument = ArgumentCaptor.forClass(Game.class);
        verify(gameGateway).save(saveGameArgument.capture());
        assertNotNull(saveGameArgument.getValue().getId());
        verify(gameGateway, times(1)).save(any(Game.class));
    }
}
