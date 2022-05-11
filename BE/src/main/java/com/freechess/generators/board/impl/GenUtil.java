package com.freechess.generators.board.impl;

import com.freechess.game.board.Position;
import com.freechess.game.pieces.IPieceType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class GenUtil {

    /**
     * Without repeatation
     */
    public static ArrayList<Position> getRandomPosOfArea(int minX, int maxX, int minY, int maxY, int number, Random rand) {
        ArrayList<Position> allPositions = new ArrayList<>();
        ArrayList<Position> positions = new ArrayList<>();

        for (int i = minX; i < maxX + 1; i++) {
            for (int j = minY; j < maxY + 1; j++) {
                allPositions.add(new Position(i, j));
            }
        }
        if (allPositions.size() < number) {
            number = allPositions.size();
        }

        for (int i = 0; i < number; i++) {
            int k = (int) Math.round(rand.nextDouble() * (allPositions.size() - 1) - 0.5);
            positions.add(allPositions.get(k));
            allPositions.remove(k);
        }
        return positions;
    }

    public static IPieceType getRandomEntryOf(ArrayList<IPieceType> pieceTypes, Random rand) {

        int index = rand.nextInt(pieceTypes.size());
        Iterator<IPieceType> iter = pieceTypes.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }

        return iter.next();
    }
}
