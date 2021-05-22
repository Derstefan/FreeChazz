package com.freechess.game.board;

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

    public Position add(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public boolean memberOf(ArrayList<Position> positions) {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getX() == getX() && positions.get(i).getY() == getY()) {
                return true;
            }
        }
        return false;
    }


    public boolean equals(Position pos){
        return pos.getX()==this.getX() && pos.getY()==this.getY();
    }

    public Position minus(Position pos){
        return new Position(this.getX()-pos.getX(),this.getY()-pos.getY());
    }

    public String toString() {
        return "(x=" + x + ",y=" + y+")";
    }
}
