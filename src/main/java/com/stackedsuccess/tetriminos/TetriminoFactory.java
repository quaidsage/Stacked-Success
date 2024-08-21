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
      case IShape.SPAWN_VALUE -> new IShape();
      case JShape.SPAWN_VALUE -> new JShape();
      case LShape.SPAWN_VALUE -> new LShape();
      case OShape.SPAWN_VALUE -> new OShape();
      case SShape.SPAWN_VALUE -> new SShape();
      case TShape.SPAWN_VALUE -> new TShape();
      case ZShape.SPAWN_VALUE -> new ZShape();
      default -> throw new AssertionError();
    };
  }
}
