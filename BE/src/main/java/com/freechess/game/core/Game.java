package com.freechess.game.core;

import com.freechess.game.pieces.generators.PieceGenerator;

import java.util.Map;
import java.util.UUID;

public class Game {

    private UUID gameId;

    private Board board;
    private Player player1;
    private Player player2;

    private int round = 0;
    private Player playersTurn;


    public Game(){
        gameId = UUID.randomUUID();
        board = new Board(30,30);
        if(Math.random()>=0.5){
            playersTurn=player1;
        } else {
            playersTurn = player2;
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
        Piece piece = board.pieceAt(fromPos);

        // is it this players turn?
        if(piece.getOwner().equals(playersTurn)){

            // is it possible move??
            if(piece.getPossibleMoves().contains(toPos)){
                board.perform(fromPos,toPos);
            }
        }
        endTurn();
    }


    private void endTurn(){
        //Check Win/Lose
        if(board.getWinner()!=null){
            // board.getWinner() winns
        }


        if(playersTurn.equals(player1)){
            playersTurn=player2;
        } else {
            playersTurn=player1;
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
}
