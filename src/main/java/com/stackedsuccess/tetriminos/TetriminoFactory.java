package com.stackedsuccess.tetriminos;

import java.util.Random;

public class TetriminoFactory {

  private static final Random random = new Random();

  // Private constructor to prevent instantiation
  private TetriminoFactory() {
    throw new IllegalStateException();
  }

  /**
   * Factory to produce random tetrimino object.
   *
   * @return tetrimino object
   */
  public static Tetrimino createRandomTetrimino() {
    int type = random.nextInt(7);
    return switch (type) {
      case 0 -> new IShape();
      case 1 -> new JShape();
      case 2 -> new LShape();
      case 3 -> new OShape();
      case 4 -> new SShape();
      case 5 -> new TShape();
      case 6 -> new ZShape();
      default -> throw new AssertionError();
    };
  }
}
