package com.freechess.game.pieces.generators;
import com.freechess.game.core.Position;
import com.freechess.game.pieces.classic.EMoveType;
import com.freechess.game.pieces.classic.JumpingPieceType;

import java.util.*;

public class JumpingPieceGenerator {


    private final int MIN_DISTANCE = 1;
    private final int MAX_DISTANCE = 10;
    private final List<Float> DISTANCE_WSKS = Arrays.asList(0.36f,0.29f,0.2f,0.08f,0.03f,0.01f,0.01f,0.01f,0.005f,0.005f);


    private final List<Float> CIRCLES_WSKS = Arrays.asList(0.05f ,0.50f,0.2f,0.1f,0.15f);

    private final float MIRROR2_WSK = 0.4f;
    private final float MIRROR4_WSK = 0.6f;
    private final float MIRROR8_WSK = 0.3f;


    private final float ENEMY_MOVE_WSK = 0.1f;
    private final float FREE_FIELD_MOVE_WSK = 0.1f;
    private final float BOTH_MOVE_WSK = 0.8f;

    private Random rand;
    private long seed;

    public JumpingPieceGenerator(long seed){
        this.seed=seed;
        rand = new Random(seed);
       // start();


    }



    private void start(){
       //check wsks
        System.out.println(sum(DISTANCE_WSKS));
        System.out.println(sum(CIRCLES_WSKS));

        char[][] moves = new char[2*MAX_DISTANCE+1][2*MAX_DISTANCE+1];
        for(int i=0;i<moves.length;i++){
            for(int  j=0;j<moves.length;j++){
                moves[i][j] = '-';
                if(i==MAX_DISTANCE && j==MAX_DISTANCE){
                    moves[i][j]='P';
                }
            }
        }
        HashMap<Position,EMoveType> map = generateMoves();
        for(Position pos: map.keySet()){
            moves[pos.getX()+MAX_DISTANCE][pos.getY()+MAX_DISTANCE] = map.get(pos).name().charAt(0);
        }
        showMoves(moves);
    }

    public JumpingPieceType generate(){
        JumpingPieceType piece = new JumpingPieceType();
        char[][] moves = new char[2*MAX_DISTANCE+1][2*MAX_DISTANCE+1];
        for(int i=0;i<moves.length;i++){
            for(int  j=0;j<moves[0].length;j++){
                moves[i][j] = '-';
                if(i==MAX_DISTANCE && j==MAX_DISTANCE){
                    moves[i][j]='P';
                }
            }
        }
        HashMap<Position,EMoveType> map = generateMoves();
        for(Position pos: map.keySet()){
            moves[pos.getX()+MAX_DISTANCE][pos.getY()+MAX_DISTANCE] = map.get(pos).name().charAt(0);
        }
        showMoves(moves);

        piece.setMoves(map);
        return piece;
    }

    private HashMap<Position, EMoveType>  generateMoves(){
        HashMap<Position, EMoveType> moveList= new HashMap<>();

        int circleNumber = dice(CIRCLES_WSKS);
        System.out.println("circleNumber: " + circleNumber);
        for(int i=0;i<circleNumber;i++){
            int x = dice(DISTANCE_WSKS);
            int y = dice(DISTANCE_WSKS);
            EMoveType type = generateMoveType();
            if(!(x==0 &&y==0)) {
                double mirrorWsk = rand.nextDouble();
                if (mirrorWsk <= MIRROR2_WSK) {
                    addToMap(moveList,getMirrors2(new Position(x, y)),type);

                    //System.out.println("mirror2 (" + x + "," + y + ")");
                } else if (mirrorWsk - MIRROR2_WSK <= MIRROR4_WSK) {
                    addToMap(moveList,getMirrors4(new Position(x, y)),type);

                    //System.out.println("mirror4 (" + x + "," + y + ")");
                } else {

                    addToMap(moveList,getMirrors8(new Position(x, y)),type);
                    //System.out.println("mirror8 (" + x + "," + y + ")");
                }
            }
        }
        return moveList;
    }



    // array as list... input ?
    private int dice(List<Float> wsks){
        double wsk = rand.nextFloat();
        System.out.println(wsk);
        for(int i=0;i<wsks.size();i++){

            if(wsk<=wsks.get(i)){
                return i;
            }
            wsk-=wsks.get(i);
        }
        return -1;
    }

    private float sum(List<Float> list){
        float sum =0;
        for (int i = 0; i < list.size(); i++) {
            sum+= list.get(i);
        }
        return sum;
    }


    private void showMoves(char[][] moves){
        for(int i=0;i<moves.length;i++){
            for(int  j=0;j<moves.length;j++){
                System.out.print(" " + moves[j][moves.length-1-i] + " ");
            }
            System.out.println();
        }
    }


    private EMoveType generateMoveType(){
        float wsk = rand.nextFloat();
        if (wsk <= ENEMY_MOVE_WSK) {
            return EMoveType.E;
        } else if (wsk - ENEMY_MOVE_WSK <= FREE_FIELD_MOVE_WSK) {
            return EMoveType.F;
        }
            return EMoveType.X;

    }



    private void addToMap(HashMap<Position,EMoveType> moves,Set<Position> positions, EMoveType type){
        for (Position pos: positions){
            moves.put(pos,type);
        }
    }

    private Set<Position> getMirrors8(Position pos){
        Set<Position> set= new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        set.add(new Position(x,y));
        set.add(new Position(-x,y));
        set.add(new Position(x,-y));
        set.add(new Position(-x,-y));
        set.add(new Position(y,x));
        set.add(new Position(y,-x));
        set.add(new Position(-y,x));
        set.add(new Position(-y,-x));
        return set;
    }

    private Set<Position> getMirrors4(Position pos){
        Set<Position> set= new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        set.add(new Position(x,y));
        set.add(new Position(-x,y));
        set.add(new Position(x,-y));
        set.add(new Position(-x,-y));
        return set;
    }

    private Set<Position> getMirrors2(Position pos){
        Set<Position> list= new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        list.add(new Position(x,y));
        list.add(new Position(-x,y));
        return list;
    }


    public static void main(String[] args){
        JumpingPieceGenerator gen = new JumpingPieceGenerator((long)(Math.random()*128392173));
    }
}
