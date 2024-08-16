package com.stackedsuccess.tetriminos;

public class LShape extends Tetrimino {
  public LShape() {
    layout =
        new int[][] {
          {0, 0, 1},
          {1, 1, 1},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = 3;
    yPos = 0;
  }
}
