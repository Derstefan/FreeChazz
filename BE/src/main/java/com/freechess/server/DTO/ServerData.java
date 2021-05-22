package com.freechess.server.DTO;

import com.freechess.server.Server;

public class ServerData {
    private int gameNumber;

    public ServerData(Server server) {
        gameNumber = server.getGameNumbers();
    }

    public int getGameNumber() {
        return gameNumber;
    }
}
