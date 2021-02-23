package com.game.kalah.interfaceadapters.gateways.impl;

import com.game.kalah.entities.Game;
import com.game.kalah.interfaceadapters.gateways.GameGateway;
import com.game.kalah.interfaceadapters.gateways.models.GameDocumentModel;
import com.game.kalah.mappers.GameMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Game> findById(UUID id) {
        GameDocumentModel result = mongoTemplate.findById(id.toString(), GameDocumentModel.class);

        if (result == null) {
            return Optional.empty();
        }

        Game game = gameMapper.fromDocumentModelToEntity(result);
        return Optional.of(game);
    }
}
