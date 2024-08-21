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
    int type = random.nextInt(7) + 1;
    return switch (type) {
      case IShape.VALUE -> new IShape();
      case JShape.VALUE -> new JShape();
      case LShape.VALUE -> new LShape();
      case OShape.VALUE -> new OShape();
      case SShape.VALUE -> new SShape();
      case TShape.VALUE -> new TShape();
      case ZShape.VALUE -> new ZShape();
      default -> throw new AssertionError();
    };
  }
}
