package com.freechess.game.player;

import java.util.UUID;

public class Player {

    private EPlayer playerType;

    private String name;
    private UUID playerId;

    public Player(String name, EPlayer playerType) {
        this.name = name;
        playerId = UUID.randomUUID();
        this.playerType = playerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public EPlayer getPlayerType() {
        return playerType;
    }
}
