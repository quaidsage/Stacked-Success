package com.stackedsuccess.tetriminos;

public class SShape extends Tetrimino {
  public SShape() {
    layout = new int[][] {
          {0, 1, 1},
          {1, 1, 0},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
