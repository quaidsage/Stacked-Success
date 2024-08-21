package com.stackedsuccess.tetriminos;

public class OShape extends Tetrimino {
  public static final int SPAWN_VALUE = 4;

  public OShape() {
    layout =
        new int[][] {
          {0, 0, 0, 0},
          {0, SPAWN_VALUE, SPAWN_VALUE, 0},
          {0, SPAWN_VALUE, SPAWN_VALUE, 0},
          {0, 0, 0, 0}
        };
    width = 4;
    height = 4;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y - 1;
  }
}
