package com.game.kalah.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.kalah.controllers.models.BusinessErrorResponseBody;
import com.game.kalah.entities.Game;
import com.game.kalah.controllers.models.CreateGameResponseBody;
import com.game.kalah.controllers.models.GameStatusResponseBody;
import com.game.kalah.exceptions.BusinessException;
import com.game.kalah.usecases.CreateNewGame;
import com.game.kalah.usecases.MakeAMove;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@DirtiesContext
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * dependencies
     */
    @MockBean
    CreateNewGame createNewGame;

    @MockBean
    MakeAMove makeAMove;

    @Test
    @DisplayName("Scenario: do POST and get HTTP status code 201")
    public void doPostAndGet201() throws Exception {
        // GIVEN a response from use case
        when(createNewGame.execute()).thenReturn(new Game());

        // WHEN make a POST request on Games endpoint
        MvcResult result = mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN should return HTTP status 201
        assertEquals(201, result.getResponse().getStatus());

        // AND should match body
        CreateGameResponseBody createGameResponseBody =
                objectMapper.readValue(result.getResponse().getContentAsString(), CreateGameResponseBody.class);
        assertNotNull(createGameResponseBody.getId());
        assertThat(createGameResponseBody.getUri().toString(), matchesPattern("^http:\\/\\/localhost\\/games\\/(.*)"));
    }

    @Test
    @DisplayName("Scenario: do PUT and get HTTP status code 200")
    public void doPutAndGet200() throws Exception {
        // GIVEN a Game ID and a pit id
        Game game = new Game();
        UUID gameId = game.getId();
        int pitId = 1;

        // GIVEN a response from use case
        when(makeAMove.execute(gameId, 1)).thenReturn(game);

        // WHEN make a PUT request on Games endpoint
        MvcResult result = mockMvc.perform(put("/games/{id}/pits/{pitId}", gameId.toString(), pitId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN should return HTTP status 200
        assertEquals(200, result.getResponse().getStatus());

        // AND should match body
        GameStatusResponseBody gameStatusResponseBody =
                objectMapper.readValue(result.getResponse().getContentAsString(), GameStatusResponseBody.class);
        assertEquals(gameId.toString(), gameStatusResponseBody.getId());
        assertThat(gameStatusResponseBody.getUri().toString(), matchesPattern("^http:\\/\\/localhost\\/games\\/(.*)"));
        assertEquals(gameStatusResponseBody.getStatus().get("1"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("2"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("3"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("4"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("5"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("6"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("7"), "0");
        assertEquals(gameStatusResponseBody.getStatus().get("8"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("9"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("10"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("11"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("12"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("13"), "6");
        assertEquals(gameStatusResponseBody.getStatus().get("14"), "0");
    }


    @Test
    @DisplayName("Scenario: do PUT and get HTTP status code 422 business error")
    public void doPutAndGet422() throws Exception {
        // GIVEN a Game ID and a pit id
        Game game = new Game();
        UUID gameId = game.getId();
        int pitId = 1;

        // GIVEN a response from use case
        when(makeAMove.execute(gameId, 1)).thenThrow(new BusinessException("Business message"));

        // WHEN make a PUT request on Games endpoint
        MvcResult result = mockMvc.perform(put("/games/{id}/pits/{pitId}", gameId.toString(), pitId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN should return HTTP status 200
        assertEquals(422, result.getResponse().getStatus());

        // AND should match body
        BusinessErrorResponseBody gameStatusResponseBody =
                objectMapper.readValue(result.getResponse().getContentAsString(), BusinessErrorResponseBody.class);
        assertEquals("Business message", gameStatusResponseBody.getMessage());
    }

}
