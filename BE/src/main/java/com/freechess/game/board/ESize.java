package com.freechess.game.board;

public enum ESize {

    small(15,15),medium(20,20),big(30,30);

    int width;
    int height;

    ESize(int width,int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
