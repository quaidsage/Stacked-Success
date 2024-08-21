package com.stackedsuccess.tetriminos;

public class LShape extends Tetrimino {
  public static final int VALUE = 3;

  public LShape() {
    layout =
        new int[][] {
          {0, 0, VALUE},
          {VALUE, VALUE, VALUE},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
