package com.freechess.server.DTO;

import com.freechess.game.player.EPlayer;
import com.freechess.game.Game;
import com.freechess.game.player.Player;

import java.util.Optional;
import java.util.UUID;

public class GameData {

    private UUID gameId;
    private Player player1;
    private Player player2;
    private EPlayer turn;
    private int round;
    private Optional<Player> winner;

    public GameData(Game game) {
        this.gameId = game.getGameId();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.turn = game.getPlayersTurn();
        this.round = game.getRound();
        this.setWinner(game.getWinner());
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

    public Optional<Player> getWinner() {
        return winner;
    }

    public void setWinner(Optional<Player> winner) {
        this.winner = winner;
    }
}
