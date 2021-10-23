package com.freechess.game.pieces;

import com.freechess.game.actions.Action;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.player.EPlayer;

import java.util.ArrayList;

public class DefaultPieceType extends PieceType{

    private ActionMap actions = new ActionMap();

    @Override
    public void perform(Board board, Position fromPos, Position toPos) throws Exception {
        Piece piece = board.pieceAt(fromPos);
        boolean topDown = piece.getOwner()!= EPlayer.P2;
        Position dPos = toPos.minus(fromPos);
        if(topDown) dPos.setY(-dPos.getY());
        Action action = actions.get(dPos);


        //debug
/*        System.out.println("action: " + toPos.minus(fromPos).toString());
        for(Position p: actions.keySet()){
            System.out.println(p.toString());
        }
        if (action == null) {
            System.out.println("action is null");
        }*/

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


    public ActionMap getMoves() {
        return actions;
    }

    public void setActions(ActionMap actions) {
        this.actions = actions;
    }
}
