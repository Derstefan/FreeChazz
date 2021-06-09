package com.freechess.game;

import com.freechess.game.board.Board;
import com.freechess.game.pieces.Piece;
import com.freechess.game.board.Position;
import com.freechess.game.player.EPlayer;
import com.freechess.game.player.Player;
import com.freechess.generators.boardgenerator.BoardGenerator;

import java.util.Optional;
import java.util.UUID;

public class Game {

    private UUID gameId;

    private Board board;
    private Player player1;
    private Player player2;

    private int round = 0;
    private EPlayer playersTurn;


    public Game(){
        gameId = UUID.randomUUID();
        BoardGenerator gen = new BoardGenerator();
        board = gen.generate(10,10);
        if(Math.random()>=0.5){
            playersTurn=EPlayer.P1;
        } else {
            playersTurn = EPlayer.P2;
        }
    }


    public boolean join(Player player){
        if(player1==null) {
            player1 = player;
            return true;
        }
        if(player2==null){
            player2 = player;
            return true;
        }
        return false;
    }


    public void play(Position fromPos, Position toPos){
        if(board.getWinner().isPresent()){
            return;
        }
        Piece piece = board.pieceAt(fromPos);
        // is it this players turn?
        if(piece.getOwner().equals(playersTurn)){
            // is it possible move??
            if(piece.canMoveTo(toPos)){
                board.perform(fromPos,toPos);
            }
        }
        endTurn();
    }


    private void endTurn(){
        //Check Win/Lose
        if(board.getWinner().isPresent()){
            // board.getWinner() wins
        }

        if(playersTurn.equals(EPlayer.P1)){
            playersTurn=EPlayer.P2;
        } else {
            playersTurn=EPlayer.P1;
        }
        board.computePossibleMoves();
        round++;
    }


    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    public int getRound() {
        return round;
    }

    public EPlayer getPlayersTurn() {
        return playersTurn;
    }

    public Optional<Player> getWinner(){

        Optional<EPlayer> winner = board.getWinner();
        if(winner.isEmpty()){
            return Optional.empty();
        }
        if(winner.get()==EPlayer.P1){
            return Optional.of(player1);
        } else {
            return Optional.of(player2);
        }

    }
}
