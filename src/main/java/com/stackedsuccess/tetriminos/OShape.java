package com.stackedsuccess.tetriminos;

public class OShape extends Tetrimino {
  public OShape() {
    layout = new int[][] {
          {0, 0, 0, 0},
          {0, 1, 1, 0},
          {0, 1, 1, 0},
          {0, 0, 0, 0}
        };
    width = 4;
    height = 4;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y - 1;
  }
}
