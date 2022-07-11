package com.freechess.generators.piece.impl;

import com.freechess.game.board.Position;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GenConfig {

    public HashMap<Position, Double> POSITION_WSK = new HashMap<>();


    public List<Double> CIRCLES_WSKS = Arrays.asList(0.05, 0.50, 0.2, 0.1, 0.15);

    public double MIRROR2_WSK = 0.4f;
    public double MIRROR4_WSK = 0.6f;
    public double MIRROR8_WSK = 0.3f;


    public double ENEMY_MOVE_WSK = 0.1f;
    public double FREE_FIELD_MOVE_WSK = 0.1f;

    public double SWAP_WSK = 0.1f;
    public double BOTH_MOVE_WSK = 0.7f;

    public List<Double> MOVE_PATTERN_NUMBER_WSKS = Arrays.asList(0.2,0.8);
    public List<Double> MOVE_PATTERN_TYPE_WSKS = Arrays.asList(0.8,0.0666,0.0666,0.0667,0.0,0.0);
    public List<Double> MOVE_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.2,0.2,0.2,0.2,0.2);

    public List<Double> RUSH_PATTERN_NUMBER_WSKS = Arrays.asList(0.02,0.98);

    public List<Double> RUSH_PATTERN_TYPE_WSKS = Arrays.asList(0.8,0.0666,0.0666,0.0667,0.0,0.0);

    public List<Double> RUSH_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.0,0.5,0.5,0.0,0.0);

    public GenConfig(int lvl) {
        setLvl(lvl);
/*      addPositionWskAtY(2,Arrays.asList(0.1, 0.07, 0.06));
        addPositionWskAtY(1,Arrays.asList(0.2, 0.2, 0.07));
        addPositionWskAtY(0,Arrays.asList(0.0, 0.2, 0.1));*/

        /*
        3 |- - - -
        2 |- - x -
        1 |- - - -
        0 |- x x -
          ----------
           0 1 2 3
         */
    }

    public void setLvl(int lvl) {
        switch (lvl) {
            case 1:
                addPositionWskAtY(2,Arrays.asList(0.1, 0.07, 0.06));
                addPositionWskAtY(1,Arrays.asList(0.2, 0.2, 0.07));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.2, 0.1));
                CIRCLES_WSKS = Arrays.asList(0.05, 0.95);

                MIRROR2_WSK = 0.6f;
                MIRROR4_WSK = 0.3f;
                MIRROR8_WSK = 0.2f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
                break;
            case 2:
                addPositionWskAtY(2,Arrays.asList(0.15, 0.09, 0.07));
                addPositionWskAtY(1,Arrays.asList(0.15, 0.15, 0.09));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.15, 0.15));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.7,0.3);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
                break;
            case 3:
                addPositionWskAtY(3,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(2,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(1,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.066, 0.066, 0.066));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.0, 1.0);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
                break;
            case 4:
                addPositionWskAtY(3,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(2,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(1,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.066, 0.066, 0.066));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.0, 0.0, 0.7, 0.3);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;

                MOVE_PATTERN_NUMBER_WSKS = Arrays.asList(0.2,0.4,0.4);
                MOVE_PATTERN_TYPE_WSKS = Arrays.asList(0.3,0.2,0.2,0.1,0.1,0.1);

                RUSH_PATTERN_NUMBER_WSKS = Arrays.asList(0.7,0.2,0.1);
                RUSH_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.0,0.4,0.3,0.3,0.0);

            case 5:
                addPositionWskAtY(4, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(3, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(2, Arrays.asList(0.04, 0.04, 0.05, 0.04, 0.04));
                addPositionWskAtY(1, Arrays.asList(0.05, 0.05, 0.04, 0.04, 0.04));
                addPositionWskAtY(0, Arrays.asList(0.0, 0.05, 0.04, 0.04, 0.04));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.2, 0.6, 0.2);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;

                MOVE_PATTERN_NUMBER_WSKS = Arrays.asList(0.2,0.3,0.3,0.2);
                MOVE_PATTERN_TYPE_WSKS = Arrays.asList(0.0,0.0,0.3,0.3,0.2,0.2);

                RUSH_PATTERN_NUMBER_WSKS = Arrays.asList(0.5,0.3,0.2);
                RUSH_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.0,0.3,0.3,0.2,0.2);
                break;
            default:
                addPositionWskAtY(4, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(3, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(2, Arrays.asList(0.04, 0.04, 0.05, 0.04, 0.04));
                addPositionWskAtY(1, Arrays.asList(0.05, 0.05, 0.04, 0.04, 0.04));
                addPositionWskAtY(0, Arrays.asList(0.0, 0.05, 0.04, 0.04, 0.04));
                CIRCLES_WSKS = Arrays.asList(0.05, 0.50, 0.2, 0.1, 0.15);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
        }
    }


    private void addPositionWskAtY(int y, List<Double> wsks) {
        for (int i = 0; i < wsks.size(); i++) {
            POSITION_WSK.put(new Position(i, y), wsks.get(i));
        }
    }
}
