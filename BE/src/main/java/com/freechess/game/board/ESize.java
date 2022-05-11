package com.freechess.game.board;

public enum ESize {

    small(15),medium(20),big(30);

    int size;

    ESize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
