package com.freechess.generators.piecegenerator;

import com.freechess.game.actions.Action;
import com.freechess.game.actions.Actions;
import com.freechess.game.pieces.ActionMap;
import com.freechess.game.pieces.DefaultPieceType;
import com.freechess.game.board.Position;

import java.util.*;

public class DefaultPieceTypeGenerator {

    private GenConfig gc = new GenConfig();
    private Random rand;
    private long seed;
    private int lvl = 0;


    public DefaultPieceType generate(int lvl,long seed){
        String str = String.valueOf(seed);
        this.lvl = lvl;
        this.seed = Long.valueOf(str);
        rand = new Random(seed);
        return generate();
    }

    public DefaultPieceType generate(long pieceId){
        String str = String.valueOf(pieceId);
        lvl = Integer.valueOf(str.charAt(0));
        this.seed = Long.valueOf(str.substring(1));
        rand = new Random(seed);
        return generate();
    }

    private DefaultPieceType generate() {
        DefaultPieceType piece = new DefaultPieceType();
        ActionMap map = generateActions();
        piece.setActions(map);
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
            return Actions.MOVE_TO_ENEMY_POSITION();
        } else if (wsk - gc.ENEMY_MOVE_WSK <= gc.FREE_FIELD_MOVE_WSK) {
            return Actions.MOVE_TO_FREE_POSITION();
        }
        return Actions.MOVE_OR_ATTACK();

    }


    private void addToMap(ActionMap actions, Set<Position> positions, Action action) {
        for (Position pos : positions) {
          //  System.out.println("generated action");
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

/*    public static void main(String[] args) {
        DefaultPieceTypeGenerator gen = new DefaultPieceTypeGenerator((long) (Math.random() * 128392173));
        gen.start();
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
