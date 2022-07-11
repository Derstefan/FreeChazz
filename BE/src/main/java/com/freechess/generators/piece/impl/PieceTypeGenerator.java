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


        return piece;
    }

    private ActionMap generateActions() {
        ActionMap actions = new ActionMap();

        generateJumpActions(actions);
        generateWalkActions(actions);
        generateRushActions(actions);

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

//------------ jump actions ---------------------------------------------------

    private void generateJumpActions(ActionMap actions){
        int circleNumber = dice(gc.CIRCLES_WSKS);
        for (int i = 0; i < circleNumber; i++) {
            //int x = dice(gc.DISTANCE_WSKS);
            //int y = dice(gc.DISTANCE_WSKS);

            Position p = dicePosition();

            int x = p.getX();
            int y = p.getY();

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
    }

    private Action generateActionType() {
        double wsk = rand.nextDouble();
        if (wsk <= gc.ENEMY_MOVE_WSK) {
            return Actions.MOVE_TO_ENEMY_POSITION;
        } else if (wsk - gc.ENEMY_MOVE_WSK <= gc.FREE_FIELD_MOVE_WSK) {
            return Actions.MOVE_TO_FREE_POSITION;
        } else if(wsk-gc.ENEMY_MOVE_WSK - gc.FREE_FIELD_MOVE_WSK<=gc.SWAP_WSK){
            return Actions.SWAP_POSITIONS_ACTION;
        }
        return Actions.MOVE_OR_ATTACK_ACTION;
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

//------------ line actions ------------------------------------------------------

    private void generateWalkActions(ActionMap actions){
        int number = dice(gc.MOVE_PATTERN_NUMBER_WSKS);
        for(int i=0;i<number;i++){

            EWalkType type = EWalkType.values()[dice(gc.MOVE_PATTERN_TYPE_WSKS)];
            int length = dice(gc.MOVE_PATTERN_LENGTH_WSKS);
            createLineActions(actions,type,length,Actions.WALK_AND_MOVE_OR_ATTACK);
        }
    }

    private void generateRushActions(ActionMap actions){
        int number = dice(gc.RUSH_PATTERN_NUMBER_WSKS);
        for(int i=0;i<number;i++){

            EWalkType type = EWalkType.values()[dice(gc.RUSH_PATTERN_TYPE_WSKS)];
            int length = dice(gc.RUSH_PATTERN_LENGTH_WSKS);
            createLineActions(actions,type,length,Actions.RUSH_ACTION);
        }
    }

    private void createLineActions(ActionMap actions, EWalkType type, int length, Action action){
        if(length<1 || length>8){
            return;
        }
        for(Position pos:type.getdPos()){
           // System.out.println("diagonal to: " + pos.getX()*length+" , "+ pos.getY()*length);
            createDiagonal(actions,pos.add(pos.getX()*length,-pos.getY()*length),action);
        }
    }

    private void createDiagonal(ActionMap actions, final Position pos, Action action){
        final int x = pos.getX();
        final int y = pos.getY();
        if(Math.abs(x)!=Math.abs(y)){
            if(x==0 && y==0){
                return;
            }
        }

        Position walkPos = new Position(0,0);
        for(int i=0;i<Math.max(Math.abs(x),Math.abs(y));i++){
            final int dx = pos.getX()!=0?pos.getX()/Math.abs(pos.getX()):0;
            final int dy = pos.getY()!=0?pos.getY()/Math.abs(pos.getY()):0;
            //System.out.println("added walkaction: " + dx + " , " + dy);
            walkPos = walkPos.add(dx,dy);
            actions.put(walkPos, action);
        }
    }
}
