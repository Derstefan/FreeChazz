package com.freechess.game.pieces.impl;

import com.freechess.game.actions.Action;
import com.freechess.game.actions.Actions;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.exception.FailedParsingPieceTypeException;
import com.freechess.game.pieces.IPieceType;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PieceType implements IPieceType {

    private String symbol = "X";// for pieceType
    private String serial;// for piecetype
    private String seed = null;

    private ActionMap actions = new ActionMap();

    private PieceType(){

    }

    private PieceType(String serial) throws FailedParsingPieceTypeException{
        Scanner s1 = new Scanner(serial);
        s1.useDelimiter(";");

        for(int i=0;s1.hasNext();i++){
            if(i>0){
                Scanner s2 = new Scanner(s1.next());
                s2.useDelimiter(",");

               char action = s2.next().charAt(0);
               int x = Integer.valueOf(s2.next());
               int y = Integer.valueOf(s2.next());
               actions.put(new Position(x,y), Actions.getActionBySymbol(action));

            } else {
                //its first, there is no action data
                symbol = s1.next();
            }

        }


    }

    @Override
    public void perform(Board board, Position fromPos, Position toPos) throws Exception {
        Piece piece = board.pieceAt(fromPos);
        boolean topDown = piece.getOwner()!= EPlayer.P2;
        Position dPos = toPos.minus(fromPos);
        if(topDown) dPos.setY(-dPos.getY());
        Action action = actions.get(dPos);
        action.perform(board,fromPos,toPos);
    }

    /**
     * Computes the possible moves of the Piece in Position pos of the board and returns it.
     *
     */
    @Override
    public ArrayList<Position> computePossibleMoves(Board board, Position pos) {

        Piece piece1 = board.pieceAt(pos);
        boolean topDown = piece1.getOwner()!= EPlayer.P2;
        ArrayList<Position> possibleMoves= new ArrayList<>();
     //   System.out.println("piece: " + piece1.getSymbol() + " (" + pos.getX() + "," + pos.getY() + ") with " + actions.size() + " actions"  );
        for (Position p : actions.keySet()) {
            int dx = p.getX();
            int dy = p.getY();
            if(topDown)dy=-dy;//wenn spieler2 -> topdown

            Action action = actions.get(p);
            Position toPos = new Position(pos.getX() + dx, pos.getY() + dy);
            Piece piece2 = board.pieceAt(toPos);
       //     System.out.println(" - " + actions.get(p).getSymbol() + " to " + " (" + p.getX() + "," + p.getY() + ")");
            if (board.isOnboard(toPos) && action.checkCondition(board,pos,toPos)) {
                possibleMoves.add(toPos);
    //            System.out.println(" -> adds possible moves");
            }
        }
        return possibleMoves;
    }


    public ActionMap getActionMap() {
        return actions;
    }

    public void setActionMap(ActionMap actions) {
        this.actions = actions;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSerial(){
        String id = symbol+";";
        for(Position p: actions.keySet()){
            id += actions.get(p).getSymbol() + "," + p.getX() + "," +p.getY() + ";";
        }
        return id;
    }

    @Override
    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceType pieceType = (PieceType) o;
        for(Position p: this.actions.keySet()){
            if(!p.isIn(pieceType.actions.keySet())){
                return false;
            }
        }
        for(Position p: pieceType.actions.keySet()){
            if(!p.isIn(this.actions.keySet())){
                return false;
            }
        }
        return Objects.equals(symbol, pieceType.symbol);
    }

    public static PieceType getInstance(){
        return new PieceType();
    }

    public static PieceType getInstance(String id) throws FailedParsingPieceTypeException {
        try {
        return new PieceType(id);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            throw new FailedParsingPieceTypeException();
        }
    }
}
