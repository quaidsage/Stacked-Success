package com.stackedsuccess.tetriminos;

import com.stackedsuccess.Action;
import com.stackedsuccess.GameBoard;

public abstract class Tetrimino {
  protected int[][] layout;
  protected int width;
  protected int height;

  public int xPos;
  public int yPos;

  /**
   * Updates tetrimino based on given action and game board state.
   *
   * @param gameBoard the current game board
   * @param action the movement action on the tetrimino
   */
  public void updateTetrimino(GameBoard gameBoard, Action action) {

    switch (action) {
      case MOVE_LEFT:
        if (!gameBoard.checkCollision(xPos - 1, yPos)) xPos--;
        break;
      case MOVE_RIGHT:
        if (!gameBoard.checkCollision(xPos + 1, yPos)) xPos++;
        break;
      case MOVE_DOWN:
        if (!gameBoard.checkCollision(xPos, yPos + 1)) yPos++;
        break;
      case ROTATE_CLOCKWISE:
        rotateClockwise(gameBoard);
        break;
      case ROTATE_COUNTERCLOCKWISE:
        rotateCounterClockwise(gameBoard);
        break;
      case HARD_DROP:
        while (!gameBoard.checkCollision(xPos, yPos + 1)) yPos++;
        break;
      default:
        return;
    }
    // Calculate ghost position
    int ghostY = calculateGhostY(gameBoard);
    gameBoard.getController().updateGhostBlock(this, ghostY);
  }

  /**
   * Get-type function.
   *
   * @return tetrimino layout as 2D integer array
   */
  public int[][] getTetriminoLayout() {
    return layout;
  }

  /**
   * Get-type function.
   *
   * @return tetrimino width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get-type function.
   *
   * @return tetrimino height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Calculates the y position of the ghost tetrimino.
   *
   * @param gameBoard the current game board
   * @return the y position of the ghost tetrimino
   */
  public int calculateGhostY(GameBoard gameBoard) {
    int ghostY = yPos;
    while (!gameBoard.checkCollision(xPos, ghostY + 1)) {
      ghostY++;
    }
    return ghostY;
  }

  /** Rotate tetrimino layout clockwise. */
  private void rotateClockwise(GameBoard gameBoard) {
    int[][] rotatedLayout = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rotatedLayout[width - j - 1][i] = layout[i][j];
      }
    }

    // Check for out of bounds and adjust position
    int newX = xPos;
    int newY = yPos;
    for (int i = 0; i < rotatedLayout.length; i++) {
      for (int j = 0; j < rotatedLayout[i].length; j++) {
        if (rotatedLayout[i][j] != 0) {
          int tempX = xPos + j;
          int tempY = yPos + i;
          if (gameBoard.isOutOfBounds(tempX, tempY)) {
            if (tempX < 0) newX++;
            if (tempX >= gameBoard.getWidth()) newX--;
            if (tempY < 0) newY++;
            if (tempY >= gameBoard.getHeight()) newY--;
          }
        }
      }
    }

    // Check for collisions and adjust position
    for (int i = 0; i < rotatedLayout.length; i++) {
      for (int j = 0; j < rotatedLayout[i].length; j++) {
        if (rotatedLayout[i][j] != 0) {
          int tempX = newX + j;
          int tempY = newY + i;
          if (gameBoard.isCellOccupied(tempX, tempY)) {
            return;
          }
        }
      }
    }

    xPos = newX;
    yPos = newY;
    layout = rotatedLayout;
  }

  /** Rotate tetrimino layout counter-clockwise. */
  private void rotateCounterClockwise(GameBoard gameBoard) {
    for (int i = 0; i < 3; i++) rotateClockwise(gameBoard);
  }
}
