package com.freechess.server;

import com.freechess.game.Game;
import com.freechess.game.player.Player;
import com.freechess.server.DTO.GameParams;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public final class Server {

    private final Map<UUID, Game> games = new HashMap<>();


    public Game createGame() {
        Game game = new Game();
        this.games.put(game.getGameId(), game);
        return game;
    }

    public Game createGame(GameParams params) {
        Game game = new Game(params);
        this.games.put(game.getGameId(), game);
        return game;
    }

    public void joinGame(Player player, UUID gameId) {
        if (games.containsKey(gameId)) {
            getGameById(gameId).join(player);
        }
        throw new IllegalArgumentException(String.format("Game with id %s is not listed.", gameId));
    }


    public Game getGameById(UUID gameId) {
        return this.games.get(gameId);
    }

    public int getGameNumbers(){
        return games.size();
    }

    public Set<UUID> getGameIDs(){
        return games.keySet();
    }



}