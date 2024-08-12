package com.stackedsuccess.tetriminos;

import java.util.Random;

public class TetriminoFactory {

    private static final Random random = new Random();

    public static Tetrimino createRandomTetrimino() {
        int type = random.nextInt(7);
        return switch (type) {
            case 0 -> new IShape();
            case 1 -> new TShape();
            default -> null;
        };
    }
}