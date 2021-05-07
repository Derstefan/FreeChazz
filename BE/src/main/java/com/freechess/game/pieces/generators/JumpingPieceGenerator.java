package com.freechess.game.pieces.generators;

import com.freechess.game.core.Position;
import com.freechess.game.pieces.classic.EMoveType;
import com.freechess.game.pieces.classic.JumpingPieceType;

import java.util.*;

public class JumpingPieceGenerator {


    private  List<Float> DISTANCE_WSKS = Arrays.asList(0.36f, 0.29f, 0.2f, 0.08f, 0.03f, 0.01f, 0.01f, 0.01f, 0.005f, 0.005f);
    private  List<Float> CIRCLES_WSKS = Arrays.asList(0.05f, 0.50f, 0.2f, 0.1f, 0.15f);

    private  float MIRROR2_WSK = 0.4f;
    private  float MIRROR4_WSK = 0.6f;
    private  float MIRROR8_WSK = 0.3f;


    private  float ENEMY_MOVE_WSK = 0.1f;
    private  float FREE_FIELD_MOVE_WSK = 0.1f;
    private  float BOTH_MOVE_WSK = 0.8f;

    private Random rand;
    private long seed;
    private int lvl;

    public JumpingPieceGenerator(long seed) {
        this.seed = seed;
        rand = new Random(seed);
    }


    public JumpingPieceType generate(int lvl){
        setLvl(lvl);
        return generate();
    }

    public JumpingPieceType generate() {
        JumpingPieceType piece = new JumpingPieceType();
        HashMap<Position, EMoveType> map = generateMoves();
        piece.setMoves(map);
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

    private HashMap<Position, EMoveType> generateMoves() {
        HashMap<Position, EMoveType> moveList = new HashMap<>();

        int circleNumber = dice(CIRCLES_WSKS);
        //      System.out.println("circleNumber: " + circleNumber);
        for (int i = 0; i < circleNumber; i++) {
            int x = dice(DISTANCE_WSKS);
            int y = dice(DISTANCE_WSKS);
            //System.out.println("x="+x+",y="+y);
            EMoveType type = generateMoveType();
            if (!(x == 0 && y == 0)) {
                double mirrorWsk = rand.nextDouble();
                if (mirrorWsk <= MIRROR2_WSK) {
                    addToMap(moveList, getMirrors2(new Position(x, y)), type);
                } else if (mirrorWsk - MIRROR2_WSK <= MIRROR4_WSK) {
                    addToMap(moveList, getMirrors4(new Position(x, y)), type);
                } else {

                    addToMap(moveList, getMirrors8(new Position(x, y)), type);
                }
            } else {
                i--; // TODO: Remove this
            }
        }
        return moveList;
    }


    // array as list... input ?
    private int dice(List<Float> wsks) {
        double wsk = rand.nextFloat();
       // System.out.println(wsk);
        for (int i = 0; i < wsks.size(); i++) {

            if (wsk <= wsks.get(i)) {
                return i;
            }
            wsk -= wsks.get(i);
        }
        return -1;
    }

    private float sum(List<Float> list) {
        float sum = 0;
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


    private EMoveType generateMoveType() {
        float wsk = rand.nextFloat();
        if (wsk <= ENEMY_MOVE_WSK) {
            return EMoveType.E;
        } else if (wsk - ENEMY_MOVE_WSK <= FREE_FIELD_MOVE_WSK) {
            return EMoveType.F;
        }
        return EMoveType.X;

    }


    private void addToMap(HashMap<Position, EMoveType> moves, Set<Position> positions, EMoveType type) {
        for (Position pos : positions) {
            moves.put(pos, type);
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

    private void setLvl(int lvl) {
        this.lvl =lvl;
        switch (lvl){
            case 1:
                DISTANCE_WSKS = Arrays.asList(0.3f,0.5f,0.2f);
                CIRCLES_WSKS = Arrays.asList(0.05f, 0.95f);

                MIRROR2_WSK = 0.6f;
                MIRROR4_WSK = 0.3f;
                MIRROR8_WSK = 0.2f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
                break;
            case 2:
                DISTANCE_WSKS = Arrays.asList(0.1f,0.1f, 0.8f, 0.05f);
                CIRCLES_WSKS = Arrays.asList(0.0f, 0.95f);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
                break;
            case 3:
                DISTANCE_WSKS = Arrays.asList(0.1f,0.1f, 0.3f, 0.3f,0.2f);
                CIRCLES_WSKS = Arrays.asList(0.0f, 0.0f,1.0f);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
                break;
            case 4:
                DISTANCE_WSKS = Arrays.asList(0.0f,0.05f, 0.2f, 0.2f,0.1f,0.2f,0.3f);
                CIRCLES_WSKS = Arrays.asList(0.0f, 0.0f,0.0f,0.3f,0.4f,0.3f);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
            case 5:
                DISTANCE_WSKS = Arrays.asList(0.0f,0.05f, 0.2f, 0.2f,0.1f,0.2f,0.1f,0.1f,0.1f);
                CIRCLES_WSKS = Arrays.asList(0.0f, 0.0f,0.0f,0.0f,0.2f,0.3f,0.3f,0.2f);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
                break;
            default:
                DISTANCE_WSKS = Arrays.asList(0.36f, 0.29f, 0.2f, 0.08f, 0.03f, 0.01f, 0.01f, 0.01f, 0.005f, 0.005f);

                CIRCLES_WSKS = Arrays.asList(0.05f, 0.50f, 0.2f, 0.1f, 0.15f);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                ENEMY_MOVE_WSK = 0.1f;
                FREE_FIELD_MOVE_WSK = 0.1f;
                BOTH_MOVE_WSK = 0.8f;
        }

      //  System.out.println(DISTANCE_WSKS.size());

    }


    //--------------------------------------------------------

    public static void main(String[] args) {
        JumpingPieceGenerator gen = new JumpingPieceGenerator((long) (Math.random() * 128392173));
    }


    private void start() {
        //check wsks
        // System.out.println(sum(DISTANCE_WSKS));
        // System.out.println(sum(CIRCLES_WSKS));

        char[][] moves = new char[2 * DISTANCE_WSKS.size() + 1][2 * DISTANCE_WSKS.size() + 1];
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves.length; j++) {
                moves[i][j] = '-';
                if (i == DISTANCE_WSKS.size() && j == DISTANCE_WSKS.size()) {
                    moves[i][j] = 'P';
                }
            }
        }
        HashMap<Position, EMoveType> map = generateMoves();
        for (Position pos : map.keySet()) {
            moves[pos.getX() + DISTANCE_WSKS.size()][pos.getY() + DISTANCE_WSKS.size()] = map.get(pos).name().charAt(0);
        }
        //showMoves(moves);
    }
}
