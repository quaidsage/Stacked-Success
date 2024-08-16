package com.stackedsuccess.tetriminos;

public class SShape extends Tetrimino {
  public SShape() {
    layout =
        new int[][] {
          {0, 1, 1},
          {1, 1, 0},
          {0, 0, 0}
        };
    width = 3;
    height = 3;
    xPos = 3;
    yPos = 0;
  }
}
