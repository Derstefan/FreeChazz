package com.freechess.game.core;

import com.freechess.game.pieces.Piece;
import com.freechess.game.pieces.classic.King;

import java.util.ArrayList;
import java.util.UUID;

public class Game {

    private UUID gameId;

    private Board board;
    private Player player1;
    private Player player2;

    // GameEnd Pieces
    private Piece king1;
    private Piece king2;

    private ArrayList<Piece> graveYard1 = new ArrayList<>();
    private ArrayList<Piece> graveYard2 = new ArrayList<>();

    private int round = 0;
    private Player playersTurn;


    public Game(String player1,String player2){
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        playersTurn=this.player1;
        init();



    }

    private void init(){
        Board board = new Board();
        king1 = new King(board,player1,new Position(0,4));
        king2 = new King(board,player2,new Position(7,4));
    }


    public void play(Position fromPos, Position toPos){
        Piece piece = board.pieceAt(fromPos);

        // is it this players turn?
        if(piece.getOwner().equals(playersTurn)){

            // is it possible move??
            if(piece.getPossibleMoves().contains(toPos)){
                piece.perform(toPos);
            }
        }
        endTurn();
    }


    private void endTurn(){
        //Check Win/Lose



        if(playersTurn.equals(player1)){
            playersTurn=player2;
        } else {
            playersTurn=player1;
        }
    }


}
