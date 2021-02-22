package com.game.kalah.interfaceadapters.gateways.impl;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.gateways.GameGateway;
import com.game.kalah.interfaceadapters.gateways.models.GameDocumentModel;
import com.game.kalah.mappers.GameMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameGatewayMongoImpl implements GameGateway {

    private MongoTemplate mongoTemplate;
    private GameMapper gameMapper;

    public GameGatewayMongoImpl(MongoTemplate mongoTemplate, GameMapper gameMapper) {
        this.mongoTemplate = mongoTemplate;
        this.gameMapper = gameMapper;
    }

    @Override
    public void save(Game game) {
        GameDocumentModel gameDocumentModel = gameMapper.fromEntityToDocumentModel(game);
        mongoTemplate.save(gameDocumentModel);
    }
}
