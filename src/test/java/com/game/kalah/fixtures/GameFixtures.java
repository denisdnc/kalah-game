package com.game.kalah.fixtures;

import com.game.kalah.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameFixtures {
    public static Game endgame() {
        List<Pit> pits = new ArrayList<>();
        pits.add(new Pit(1, PitType.REGULAR, 0, Player.SOUTH_PLAYER));
        pits.add(new Pit(2, PitType.REGULAR, 0, Player.SOUTH_PLAYER));
        pits.add(new Pit(3, PitType.REGULAR, 0, Player.SOUTH_PLAYER));
        pits.add(new Pit(4, PitType.REGULAR, 0, Player.SOUTH_PLAYER));
        pits.add(new Pit(5, PitType.REGULAR, 0, Player.SOUTH_PLAYER));
        pits.add(new Pit(6, PitType.REGULAR, 1, Player.SOUTH_PLAYER));
        pits.add(new Pit(7, PitType.HOUSE, 5, Player.SOUTH_PLAYER));

        pits.add(new Pit(8, PitType.REGULAR, 0, Player.NORTH_PLAYER));
        pits.add(new Pit(9, PitType.REGULAR, 0, Player.NORTH_PLAYER));
        pits.add(new Pit(10, PitType.REGULAR, 0, Player.NORTH_PLAYER));
        pits.add(new Pit(11, PitType.REGULAR, 0, Player.NORTH_PLAYER));
        pits.add(new Pit(12, PitType.REGULAR, 0, Player.NORTH_PLAYER));
        pits.add(new Pit(13, PitType.REGULAR, 1, Player.NORTH_PLAYER));
        pits.add(new Pit(14, PitType.HOUSE, 5, Player.NORTH_PLAYER));

        return new Game(UUID.randomUUID(), new Board(pits, Player.SOUTH_PLAYER));
    }

}
