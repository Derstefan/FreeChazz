package com.freechess.game.pieces.impl;

import com.freechess.game.actions.Action;
import com.freechess.game.board.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;




public class ActionMap {
    public static final int width = 7;
    public static final int height = 7;


    private Map<Position, Action> actions = new HashMap<>();

    public boolean put(Position pos,Action action){
        ArrayList<Position> posList = new ArrayList<>();
        for(Position p: keySet()){
            if(p.equals(pos)){
                posList.add(p);

            }
        }
        for(Position p: posList){
            actions.remove(p);
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
    //TODO: zur optimierung vll eine liste?
    public String[][] getActions(){

        String[][] moves = new String[2* width +1][2* height +1];
        for (int i = 0; i < moves.length; i++) {
        for (int j = 0; j < moves.length; j++) {
            moves[i][j] = "-";
            if (i == width && j == height) {
                moves[i][j] = "P";
            }
        }
    }
        for (Position pos : actions.keySet()) {
        moves[pos.getX() + width][pos.getY() + height] = ""+actions.get(pos).getSymbol();
    }
        return moves;
    }


    public void setActions(Map<Position, Action> actions) {
        this.actions = actions;
    }
}
