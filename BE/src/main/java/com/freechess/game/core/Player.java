package com.freechess.game.core;

import com.freechess.game.pieces.Piece;

import java.util.ArrayList;
import java.util.UUID;

public class Player {

    private String name;
    private ArrayList<Piece> graveyard = new ArrayList<>();
    private UUID playerId;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToGraveyard(Piece p){
        graveyard.add(p);
        p.setPos(null);
    }
}
