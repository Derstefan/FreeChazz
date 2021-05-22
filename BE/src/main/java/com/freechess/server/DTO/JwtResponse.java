package com.freechess.server.DTO;

import com.freechess.game.player.EPlayer;

import java.util.UUID;

public class JwtResponse {

    private UUID gameId;
    private UUID playerId;
    private String accessToken;
    private EPlayer player;

    public JwtResponse(UUID gameId, UUID playerId, String accessToken,EPlayer player) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.accessToken = accessToken;
        this.player = player;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public EPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EPlayer player) {
        this.player = player;
    }
}
