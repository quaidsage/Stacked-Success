package com.stackedsuccess.tetriminos;

public class ZShape extends Tetrimino {
  public ZShape() {
    layout = new int[][] {
          {1, 1, 0},
          {0, 1, 1},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = Tetrimino.DEFAULT_SPAWN_X;
    yPos = Tetrimino.DEFAULT_SPAWN_Y;
  }
}
