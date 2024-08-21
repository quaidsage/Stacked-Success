package com.stackedsuccess.tetriminos;

public class IShape extends Tetrimino {
    public static final int VALUE = 1;

    public IShape() {
        layout = new int[][] {
                {0,0,0,0},
                {VALUE,VALUE,VALUE,VALUE},
                {0,0,0,0},
                {0,0,0,0}
        };
        width = 4;
        height = 4;
        xPos = Tetrimino.DEFAULT_SPAWN_X;
        yPos = Tetrimino.DEFAULT_SPAWN_Y - 1;
    }
}
