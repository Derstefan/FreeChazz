package com.freechess.game.DTO;

import com.freechess.game.core.EPlayer;
import com.freechess.game.core.Game;
import com.freechess.game.core.Player;

import java.util.UUID;

public class GameData {

    private UUID gameId;
    private Player player1;
    private Player player2;
    private EPlayer turn;
    private int round;
    private EPlayer winner;

    public GameData(Game game) {
        this.gameId = game.getGameId();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.turn = game.getPlayersTurn();
        this.round = game.getRound();
    }


    public UUID getGameId() {
        return gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public EPlayer getTurn() {
        return turn;
    }

    public int getRound() {
        return round;
    }

    public EPlayer getWinner() {
        return winner;
    }

    public void setWinner(EPlayer winner) {
        this.winner = winner;
    }
}
