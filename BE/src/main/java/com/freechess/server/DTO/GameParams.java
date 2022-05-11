package com.freechess.server.DTO;

import com.freechess.game.board.ESize;

public class GameParams {

    private ESize size;
    private Long seed;

    public GameParams(ESize size, Long seed) {
        this.size = size;
        this.seed = seed;
    }

    public GameParams(ESize size) {
        this.size = size;
    }

    public GameParams(Long seed) {
        this.seed = seed;
    }

    public ESize getESize() {
        return size;
    }

    public Long getSeed() {
        return seed;
    }
}
