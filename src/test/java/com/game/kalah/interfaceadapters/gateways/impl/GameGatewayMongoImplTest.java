package com.game.kalah.interfaceadapters.gateways.impl;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.gateways.GameGateway;
import com.game.kalah.interfaceadapters.gateways.models.GameDocumentModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureDataMongo
@ActiveProfiles(value = "test")
public class GameGatewayMongoImplTest {

    /** component to test */
    @Autowired
    GameGateway gameGateway;

    /** utils */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Scenario: persist game entity with success")
    public void persistWithSuccess() {
        // GIVEN a new Game
        Game game = new Game();

        // WHEN save game
        gameGateway.save(game);

        // THEN game should be at games collection
        Query query = new Query();
        query.addCriteria(Criteria.where("gameId").is(game.getId().toString()));
        GameDocumentModel gameDocumentModel = mongoTemplate.findOne(query, GameDocumentModel.class, "games");

        // AND should match properties
        assertEquals(game.getId().toString(), gameDocumentModel.getId());
        assertEquals(game.getBoard().getTurn().toString(), gameDocumentModel.getBoard().getTurn());
    }

}
