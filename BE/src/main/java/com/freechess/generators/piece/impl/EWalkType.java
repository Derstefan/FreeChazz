package com.freechess.generators.piece.impl;

import com.freechess.game.board.Position;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public enum EWalkType {
    up(1,  Arrays.asList(new Position(0,-1))),
    updown(2,Arrays.asList(new Position(0,-1),new Position(0,-1))),
    v(2, Arrays.asList(new Position(-1,-1),new Position(1,-1))),
    leftRight(2, Arrays.asList(new Position(0,-1),new Position(0,-1))),
    cross(3, Arrays.asList(new Position(-1,-1),new Position(1,-1),new Position(1,1),new Position(-1,1))),
    plus(3, Arrays.asList(new Position(0,-1),new Position(1,0),new Position(0,1),new Position(-1,0))),;

    int type;
    List<Position> dPos;

    EWalkType(int type, List<Position> dPos) {
        this.type = type;
        this.dPos = dPos;
    }

    public List<Position> getdPos() {
        return dPos;
    }

    public int getType() {
        return type;
    }


    public static ArrayList<EWalkType> getValues(){
        ArrayList<EWalkType> list = new ArrayList<>();
        list.add(up);
        list.add(updown);
        list.add(v);
        list.add(leftRight);
        list.add(cross);
        list.add(plus);
        return list;
    }
}
