package com.freechess.game.DTO;

import com.freechess.game.server.Server;

public class ServerData {
    private int gameNumber;

    public ServerData(Server server) {
        gameNumber = server.getGameNumbers();
    }

    public int getGameNumber() {
        return gameNumber;
    }
}
