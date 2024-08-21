package com.stackedsuccess.tetriminos;

public class SShape extends Tetrimino {
  public static final int SPAWN_VALUE = 5;

  public SShape() {
    layout =
        new int[][] {
          {0, SPAWN_VALUE, SPAWN_VALUE},
          {SPAWN_VALUE, SPAWN_VALUE, 0},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
