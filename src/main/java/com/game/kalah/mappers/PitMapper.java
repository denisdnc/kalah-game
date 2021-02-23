package com.game.kalah.mappers;

import com.game.kalah.entities.Pit;
import com.game.kalah.entities.PitType;
import com.game.kalah.entities.Player;
import com.game.kalah.gateways.models.PitDocumentModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PitMapper {
    public PitDocumentModel fromEntityToDocumentModel(Pit pit) {
        return new PitDocumentModel(pit.getId(),
                pit.getType().toString(),
                pit.getStonesQuantity(),
                pit.getOwner().toString());
    }

    public List<PitDocumentModel> fromEntityListToDocumentModelList(List<Pit> pit) {
        return pit.stream().map(this::fromEntityToDocumentModel).collect(Collectors.toList());
    }

    public Pit fromDocumentModelToEntity(PitDocumentModel pitDocumentModel) {
        return new Pit(pitDocumentModel.getId(),
                PitType.valueOf(pitDocumentModel.getType()),
                pitDocumentModel.getStonesQuantity(),
                Player.valueOf(pitDocumentModel.getOwner()));
    }

    public List<Pit> fromDocumentModelListToEntityList(List<PitDocumentModel> pitDocumentModels) {
        return pitDocumentModels.stream().map(this::fromDocumentModelToEntity).collect(Collectors.toList());
    }
}
