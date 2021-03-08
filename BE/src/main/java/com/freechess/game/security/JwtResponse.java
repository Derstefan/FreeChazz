package com.freechess.game.security;

import java.util.UUID;

public class JwtResponse {

    private UUID gameId;
    private UUID playerId;
    private String accessToken;
    private String type = "FreeChess ";

    public JwtResponse(UUID gameId, UUID playerId, String accessToken) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.accessToken = accessToken;
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
}
