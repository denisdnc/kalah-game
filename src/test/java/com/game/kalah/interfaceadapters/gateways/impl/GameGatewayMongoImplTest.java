package com.game.kalah.interfaceadapters.gateways.impl;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.gateways.GameGateway;
import com.game.kalah.interfaceadapters.gateways.models.GameDocumentModel;
import com.game.kalah.mappers.GameMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureDataMongo
@ActiveProfiles(value = "test")
@DirtiesContext
public class GameGatewayMongoImplTest {

    /** component to test */
    @Autowired
    GameGateway gameGateway;

    /** utils */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    GameMapper gameMapper;

    @Test
    @DisplayName("Scenario: persist game entity with success")
    public void persistWithSuccess() {
        // GIVEN a new Game
        Game game = new Game();

        // WHEN save game
        gameGateway.save(game);

        // THEN game should be at games collection
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(game.getId().toString()));
        GameDocumentModel gameDocumentModel = mongoTemplate.findOne(query, GameDocumentModel.class, "games");

        // AND should match properties
        assertNotNull(gameDocumentModel);
        assertEquals(game.getId().toString(), gameDocumentModel.getId());
        assertEquals(game.getBoard().getTurn().toString(), gameDocumentModel.getBoard().getTurn());
    }

    @Test
    @DisplayName("Scenario: find game by id")
    public void findGameById() {
        // GIVEN a new Game already in database
        Game game = new Game();
        GameDocumentModel gameDocumentModel = gameMapper.fromEntityToDocumentModel(game);
        mongoTemplate.save(gameDocumentModel);

        // WHEN find game by id
        Game result = gameGateway.findById(game.getId()).get();

        // THEN game should be at games collection match properties
        assertNotNull(result);
        assertEquals(result.getId().toString(), game.getId().toString());
        assertEquals(result.getBoard().getTurn().toString(), game.getBoard().getTurn().toString());
    }

    @Test
    @DisplayName("Scenario: empty find")
    public void emptyFind() {
        // WHEN find game by a invalid id
        Optional<Game> result = gameGateway.findById(UUID.randomUUID());

        // THEN game should be at games collection match properties
        assertTrue(result.isEmpty());
    }

}
