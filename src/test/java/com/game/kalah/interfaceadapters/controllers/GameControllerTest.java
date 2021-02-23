package com.game.kalah.interfaceadapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.controllers.bodies.CreateGameResponseBody;
import com.game.kalah.usecases.CreateNewGame;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@DirtiesContext
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /** dependencies */
    @MockBean
    CreateNewGame createNewGame;

    @Test
    @DisplayName("Scenario: do POST and get HTTP status code 201")
    public void doPostAndGet200() throws Exception {
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


}
