package com.stackedsuccess.tetriminos;

public class SShape extends Tetrimino {
  public static final int VALUE = 5;

  public SShape() {
    layout =
        new int[][] {
          {0, VALUE, VALUE},
          {VALUE, VALUE, 0},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
