package com.stackedsuccess.tetriminos;

public class OShape extends Tetrimino {
  public OShape() {
    layout =
        new int[][] {
          {0, 0, 0, 0},
          {0, 1, 1, 0},
          {0, 1, 1, 0},
          {0, 0, 0, 0}
        };
    width = 4;
    height = 4;
    xPos = 3;
    yPos = 0;
  }
}
