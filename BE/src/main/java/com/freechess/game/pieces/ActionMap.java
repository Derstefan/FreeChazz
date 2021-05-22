package com.freechess.game.pieces;

import com.freechess.game.actions.Action;
import com.freechess.game.board.Position;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



/*
    ToDo: mehrere Actions pro feld möglich? erstmal lieber nein!
 */
public class ActionMap {

    private Map<Position, Action> actions = new HashMap<>();

    public boolean put(Position pos,Action action){
        for(Position p: keySet()){
            if(p.equals(pos)){
                return false;
            }
        }
        actions.put(pos,action);
        return true;
    }

    public Action get(Position pos){
        for(Position p: keySet()){
            if(p.equals(pos)){
                return actions.get(p);
            }
        }
        return null;
    }

    public Set<Position> keySet(){
        return actions.keySet();
    }

    public void clear(){
        actions.clear();
    }

    public int size(){
        return actions.size();
    }

/*    public Map<Position, Action> getActions() {
        return actions;
    }*/

    //zur serialisierung gleich ein array
    public String[][] getActions(){
        int w = 10;
        int h = 10;

        String[][] moves = new String[2*w+1][2*h+1];
        for (int i = 0; i < moves.length; i++) {
        for (int j = 0; j < moves.length; j++) {
            moves[i][j] = "-";
            if (i == w && j == h) {
                moves[i][j] = "P";
            }
        }
    }
        for (Position pos : actions.keySet()) {
        moves[pos.getX() + w][pos.getY() + h] = ""+actions.get(pos).getSymbol();
    }
        return moves;
    }


    public void setActions(Map<Position, Action> actions) {
        this.actions = actions;
    }
}
