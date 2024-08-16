package com.stackedsuccess.tetriminos;

public class ZShape extends Tetrimino {
  public ZShape() {
    layout =
        new int[][] {
          {1, 1, 0},
          {0, 1, 1},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = 3;
    yPos = 0;
  }
}
