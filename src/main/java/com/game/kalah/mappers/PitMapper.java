package com.game.kalah.mappers;

import com.game.kalah.entities.Pit;
import com.game.kalah.interfaceadapters.gateways.models.PitDocumentModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PitMapper {
    PitDocumentModel fromEntityToDocumentModel(Pit pit) {
        return new PitDocumentModel(pit.getId(),
                pit.getType().toString(),
                pit.getStonesQuantity(),
                pit.getOwner().toString());
    }

    List<PitDocumentModel> fromEntityListToDocumentModelList(List<Pit> pit) {
        return pit.stream().map(this::fromEntityToDocumentModel).collect(Collectors.toList());
    }
}
