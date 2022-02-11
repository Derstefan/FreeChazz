package com.freechess.generators.piece.impl;

import com.freechess.game.actions.Action;
import com.freechess.game.actions.Actions;
import com.freechess.game.pieces.impl.ActionMap;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.impl.PieceTypeBuilder;
import com.freechess.generators.piece.IPieceTypeGenerator;
import com.freechess.generators.piece.PieceTypeGeneratorParam;

import java.util.*;

public class PieceTypeGenerator implements IPieceTypeGenerator {

    private GenConfig gc;
    private Random rand;
    private long seed;
    private int lvl = 0;



    public PieceType generate(PieceTypeGeneratorParam param){
        if(param.getSeed()==null){
            rand = new Random();
        } else {
            seed = param.getSeed();
            rand = new Random(seed);
        }

        if(param.getLvl()==null){
            lvl=rand.nextInt(5);
        } else {
            lvl=param.getLvl().intValue();

        }
        gc = new GenConfig(lvl);
        return generate();
    }




 /*   public PieceType generate(int lvl, long seed){
        String str = String.valueOf(seed);
        this.lvl = lvl;
        gc.setLvl(lvl);
        this.seed = Long.valueOf(str);
        rand = new Random(seed);
        return generate();
    }

    public PieceType generate(long pieceId){
        String str = String.valueOf(pieceId);
        lvl = Integer.valueOf(str.charAt(0));
        gc.setLvl(lvl);
        this.seed = Long.valueOf(str.substring(1));
        rand = new Random(seed);
        return generate();
    }*/

    private PieceType generate() {

        ActionMap map = generateActions();
        PieceType piece = new PieceTypeBuilder().actions(map).build();
        /*char[][] moves = new char[2 * DISTANCE_WSKS.size() + 1][2 * DISTANCE_WSKS.size() + 1];
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves[0].length; j++) {
                moves[i][j] = '-';
                if (i == DISTANCE_WSKS.size() && j == DISTANCE_WSKS.size()) {
                    moves[i][j] = 'P';
                }
            }
        }
        for (Position pos : map.keySet()) {
            moves[pos.getX() + DISTANCE_WSKS.size()][pos.getY() + DISTANCE_WSKS.size()] = map.get(pos).name().charAt(0);
        }
        showMoves(moves);*/



        return piece;
    }

    private ActionMap generateActions() {
        ActionMap actions = new ActionMap();

        int circleNumber = dice(gc.CIRCLES_WSKS);
        //      System.out.println("circleNumber: " + circleNumber);
        for (int i = 0; i < circleNumber; i++) {
            //int x = dice(gc.DISTANCE_WSKS);
            //int y = dice(gc.DISTANCE_WSKS);

            Position p = dicePosition();

            int x = p.getX();
            int y = p.getY();

            //System.out.println("x="+x+",y="+y);
            Action type = generateActionType();
            if (!(x == 0 && y == 0)) {
                double mirrorWsk = rand.nextDouble();
                if (mirrorWsk <= gc.MIRROR2_WSK) {
                    addToMap(actions, getMirrors2(new Position(x, y)), type);
                } else if (mirrorWsk - gc.MIRROR2_WSK <= gc.MIRROR4_WSK) {
                    addToMap(actions, getMirrors4(new Position(x, y)), type);
                } else {
                    addToMap(actions, getMirrors8(new Position(x, y)), type);
                }
            } else {
                i--; // TODO: Remove this
            }
        }
        return actions;
    }


    // array as list... input ?
    private int dice(List<Double> wsks) {
        double wsk = rand.nextDouble();
        // System.out.println(wsk);
        for (int i = 0; i < wsks.size(); i++) {

            if (wsk <= wsks.get(i)) {
                return i;
            }
            wsk -= wsks.get(i);
        }
        return -1;
    }

    private Position dicePosition(){
        ArrayList<Position> posList = new ArrayList<>(gc.POSITION_WSK.keySet());
        Collections.sort(posList);
        double wsk = rand.nextDouble();
        for (int i = 0; i < posList.size(); i++) {
            if (wsk <= gc.POSITION_WSK.get(posList.get(i))) {
                return posList.get(i);
            }
            wsk -= gc.POSITION_WSK.get(posList.get(i));
        }
        return new Position(0,0);
    }

    private double sum(List<Double> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum;
    }

/*    private ArrayList<Position> sortPositionSet(Set<Position> posSet){
        ArrayList<Position> positions = new ArrayList<>(posSet);
        Collections.sort(positions);

    }*/


    private void showMoves(char[][] moves) {
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves.length; j++) {
                System.out.print(" " + moves[j][moves.length - 1 - i] + " ");
            }
            System.out.println();
        }
    }


    private Action generateActionType() {
        double wsk = rand.nextDouble();
        if (wsk <= gc.ENEMY_MOVE_WSK) {
            return Actions.MOVE_TO_ENEMY_POSITION;
        } else if (wsk - gc.ENEMY_MOVE_WSK <= gc.FREE_FIELD_MOVE_WSK) {
            return Actions.MOVE_TO_FREE_POSITION;
        }
        return Actions.MOVE_OR_ATTACK_ACTION;
    }

    private void createWalkActions(int dx,int dy){



    }


    private void addToMap(ActionMap actions, Set<Position> positions, Action action) {
        for (Position pos : positions) {
            actions.put(pos, action);
        }
    }

    private Set<Position> getMirrors8(Position pos) {
        Set<Position> set = new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        set.add(new Position(x, y));
        set.add(new Position(-x, y));
        set.add(new Position(x, -y));
        set.add(new Position(-x, -y));
        set.add(new Position(y, x));
        set.add(new Position(y, -x));
        set.add(new Position(-y, x));
        set.add(new Position(-y, -x));
        return set;
    }

    private Set<Position> getMirrors4(Position pos) {
        Set<Position> set = new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        set.add(new Position(x, y));
        set.add(new Position(-x, y));
        set.add(new Position(x, -y));
        set.add(new Position(-x, -y));
        return set;
    }

    private Set<Position> getMirrors2(Position pos) {
        Set<Position> list = new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        list.add(new Position(x, y));
        list.add(new Position(-x, y));
        return list;
    }




    //--------------------------------------------------------
/*
    public static void main(String[] args) {
        Random rand = new Random(123);
        System.out.println(rand.nextDouble());
    }

    private void start() {
        //check wsks
         System.out.println(sum(gc.DISTANCE_WSKS));
         System.out.println(sum(gc.CIRCLES_WSKS));

        char[][] moves = new char[2 * gc.DISTANCE_WSKS.size() + 1][2 * gc.DISTANCE_WSKS.size() + 1];
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves.length; j++) {
                moves[i][j] = '-';
                if (i == gc.DISTANCE_WSKS.size() && j == gc.DISTANCE_WSKS.size()) {
                    moves[i][j] = 'P';
                }
            }
        }
        ActionMap actions = generateActions();
        for (Position pos : actions.keySet()) {
            moves[pos.getX() + gc.DISTANCE_WSKS.size()][pos.getY() + gc.DISTANCE_WSKS.size()] = 'X';
        }
        showMoves(moves);
    }*/
}
