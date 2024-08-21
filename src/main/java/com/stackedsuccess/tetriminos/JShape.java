package com.stackedsuccess.tetriminos;

public class JShape extends Tetrimino {
    public static final int VALUE = 2;

    public JShape() {
        layout = new int[][] {
                {VALUE,0,0},
                {VALUE,VALUE,VALUE},
                {0,0,0}
        };
        width = 3;
        height = 3;
        xPos = Tetrimino.DEFAULT_SPAWN_X;
        yPos = Tetrimino.DEFAULT_SPAWN_Y;
    }
}
