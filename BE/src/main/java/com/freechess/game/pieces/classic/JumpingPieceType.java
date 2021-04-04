package com.freechess.game.pieces.classic;

import com.freechess.game.core.Board;
import com.freechess.game.core.EPlayer;
import com.freechess.game.core.Piece;
import com.freechess.game.core.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Classical pieces jump to a free or enemys position.
 */
public class JumpingPieceType extends ClassicPieceType {





    /**
     * Describes the figur movements independent from board state.
     */
    private Map<Position, EMoveType> moves = new HashMap<>();



    public JumpingPieceType() {
        super();
    }



    /**
     * Computes the possible moves of the Piece in Position pos of the board and returns it.
     *
     */
    public ArrayList<Position> computePossibleMoves(Board board, Position pos) {

        Piece piece1 = board.pieceAt(pos);
        boolean topDown = piece1.getOwner()!= EPlayer.Player2;
        ArrayList<Position> possibleMoves= new ArrayList<>();

        for (Position p : moves.keySet()) {
            int dx = p.getX();
            int dy = p.getY();
            if(topDown)dy=-dy;//wenn spieler2 -> topdown

            EMoveType type = moves.get(p);
            Position toPos = new Position(pos.getX() + dx, pos.getY() + dy);
            Piece piece2 = board.pieceAt(toPos);

            if (board.isOnboard(toPos) && !board.areEnemys(piece1, piece2)) {
                if (type == EMoveType.E && board.areEnemys(piece1, piece2)) {
                    // only if there is an enemy

                    possibleMoves.add(toPos);
                } else if (type == EMoveType.F && !board.areEnemys(piece1, piece2)) {
                    // only if there is no enemy
                    possibleMoves.add(toPos);
                } else if (type == EMoveType.X) {
                    // both
                    possibleMoves.add(toPos);
                }
            }
        }
        return possibleMoves;
    }

    public Map<Position, EMoveType> getMoves() {
        return moves;
    }

    public void setMoves(Map<Position, EMoveType> moves) {
        this.moves = moves;
    }
}
