package com.freechess.game.core;

import java.util.ArrayList;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position add(int dx,int dy){
        return new Position(x+dx,y+dy);
    }

    public boolean memberOf(ArrayList<Position> positions){
        for (int i = 0; i < positions.size (); i++) {
            if(positions.get(i).getX()==getX() && positions.get(i).getY()==getY()){
                return true;
            }
        }
        return false;
    }
}
