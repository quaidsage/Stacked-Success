package com.stackedsuccess;

import com.stackedsuccess.controllers.GameBoardController;
import com.stackedsuccess.tetriminos.*;
import java.io.IOException;

// This class defines the game board and functionality to check board state
public class GameBoard {
  private static final int DEFAULT_BOARD_WIDTH = 10;
  private static final int DEFAULT_BOARD_HEIGHT = 22;

  private final int[][] board;
  private final int width;
  private final int height;
  private long frameCount;
  private boolean forceUpdate;

  private Tetrimino currentTetrimino;
  private Tetrimino nextTetrimino;
  private Tetrimino holdTetrimino;
  private boolean holdUsed = false;

  private int score = 0;
  private int level = 1;
  private int totalLinesCleared = 0;
  private int gameSpeed;

  private GameBoardController controller;

  public GameBoard() {
    board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
    this.width = DEFAULT_BOARD_WIDTH;
    this.height = DEFAULT_BOARD_HEIGHT;
    initializeBoard();
  }

  public GameBoard(int width, int height) {
    board = new int[width][height];
    this.width = width;
    this.height = height;
    initializeBoard();
  }

  /** Setup initial tetrimino pieces and board metrics. */
  private void initializeBoard() {
    currentTetrimino = TetriminoFactory.createRandomTetrimino();
    nextTetrimino = TetriminoFactory.createRandomTetrimino();

    frameCount = 0;
    gameSpeed = 100;
  }

  /**
   * Set the link to a controller for the game board.
   *
   * @param controller the controller to set
   */
  public void setController(GameBoardController controller) {
    this.controller = controller;
  }

  /**
   * Get the current score.
   *
   * @return the current score
   */
  public GameBoardController getController() {
    return controller;
  }

  /** Update the state of the board. */
  public void update() throws IOException {
    frameCount++;
    controller.updateDisplay(board);

    // Stagger automatic tetrimino movement based on frame count
    if (frameCount % gameSpeed == 0 || forceUpdate) {
      forceUpdate = false;
      if (currentTetrimino.canMoveDown(this)) {
        currentTetrimino.updateTetrimino(this, Action.MOVE_DOWN);
      } else {
        handleTetriminoPlacement();
      }
    }
  }

  /**
   * Checks if current tetrimino will collide with borders or existing cells.
   *
   * @param x the x position to start check for collision
   * @param y the y position to start check for collision
   * @return true if current tetrimino will collide with border or existing cells
   */
  public boolean checkCollision(int x, int y) {
    int[][] layout = currentTetrimino.getTetriminoLayout();
    int newX;
    int newY;

    for (int layoutY = 0; layoutY < currentTetrimino.getHeight(); layoutY++) {
      for (int layoutX = 0; layoutX < currentTetrimino.getWidth(); layoutX++) {
        if (layout[layoutY][layoutX] != 0) {
          newX = x + layoutX;
          newY = y + layoutY;

          // Check for out of bound collisions
          if (isOutOfBounds(newX, newY)) return true;

          // Check for existing tetrimino cells
          if (isCellOccupied(newX, newY)) return true;
        }
      }
    }
    return false;
  }

  /**
   * Check to see whether the default tetrimino spawn location is occupied.
   *
   * @return if the spawn location is occupied
   */
  public boolean isSpawnLocationOccupied() {
    return checkCollision(Tetrimino.DEFAULT_SPAWN_X, Tetrimino.DEFAULT_SPAWN_Y);
  }

  /** Forces the game loop to update once, primarily used to place tetrimino pieces instantly. */
  public void forceUpdate() {
    forceUpdate = true;
  }

  /**
   * Get-type function.
   *
   * @return the current tetrimino for game board
   */
  public Tetrimino getCurrentTetrimino() {
    return currentTetrimino;
  }

  /**
   * Get the height of the board.
   *
   * @return the current height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Get the width of the board.
   *
   * @return the current width
   */
  public int getWidth() {
    return width;
  }

  /** Handle the placement of the current tetrimino piece in its current position. */
  private void handleTetriminoPlacement() throws IOException {
    holdUsed = false;

    placeTetrimino(currentTetrimino);
    if (checkGameOver()) return;

    clearFullRows();

    currentTetrimino = nextTetrimino;
    nextTetrimino = TetriminoFactory.createRandomTetrimino();

    // If spawn location is occupied, move tetrimino visually outside of board
    // If the tetrimino is placed visually outside the board, the game will end.
    if (isSpawnLocationOccupied()) currentTetrimino.setYPos(0);

    controller.setNextPieceView(nextTetrimino);
  }

  /**
   * Appends new tetrimino to the game board.
   *
   * @param tetrimino the tetrimino to place on the game board.
   */
  private void placeTetrimino(Tetrimino tetrimino) {
    int[][] layout = tetrimino.getTetriminoLayout();
    for (int layoutY = 0; layoutY < tetrimino.getHeight(); layoutY++) {
      for (int layoutX = 0; layoutX < tetrimino.getWidth(); layoutX++) {
        if (layout[layoutY][layoutX] != 0) {

          int spawnX = tetrimino.getXPos() + layoutX;
          int spawnY = tetrimino.getYPos() + layoutY;

          board[spawnY][spawnX] = layout[layoutY][layoutX];
        }
      }
    }
  }

  /** Check top two rows of the board for pieces visually out of bounds. */
  private boolean checkGameOver() throws IOException {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y <= 1; y++) {
        if (board[y][x] != 0) {
          controller.gameOver();
          return true;
        }
      }
    }
    return false;
  }

  /** Clears full rows and moves rows above downwards. */
  private void clearFullRows() {
    int newLinesCleared = 0;
    for (int y = 0; y < board.length; y++) {
      if (isRowFull(y, board[y])) {
        newLinesCleared++;
        shiftRowsDown(y);
      }
    }
    calculateScore(newLinesCleared);
    totalLinesCleared += newLinesCleared;

    controller.updateLine(totalLinesCleared);
    updateLevel();
    changeGameSpeed();
  }

  /** Updates the level based on the number of lines cleared. */
  private void updateLevel() {
    level = (totalLinesCleared / 10) + 1;
    controller.updateLevel(level);
  }

  /**
   * Calculates the score based on the number of lines cleared.
   *
   * @param linesCleared the number of lines cleared
   */
  private void calculateScore(int linesCleared) {
    switch (linesCleared) {
      case 1:
        score += 40;
        break;
      case 2:
        score += 100;
        break;
      case 3:
        score += 300;
        break;
      case 4:
        score += 1200;
        break;
      default:
        break;
    }
    controller.updateScore(score);
  }

  /**
   * Moves rows above certain row downwards and creates empty line at top of game board.
   *
   * @param fromYAxis the start y-axis for moving subsequent rows downward
   */
  private void shiftRowsDown(int fromYAxis) {
    for (int y = fromYAxis; y > 0; y--) {
      System.arraycopy(board[y - 1], 0, board[y], 0, board[0].length);
    }

    for (int x = 0; x < board[0].length; x++) board[0][x] = 0;
  }

  /**
   * Checks if coordinates are outside the bounds of the game board.
   *
   * @param x the x position to check
   * @param y the y position to check
   * @return true if the coordinates are out of bounds
   */
  public boolean isOutOfBounds(int x, int y) {
    return x < 0 || x >= board[0].length || y < 0 || y >= board.length;
  }

  /**
   * Check if coordinates are occupied in the game board.
   *
   * @param x the x position to check
   * @param y the y position to check
   * @return true if cell is occupied in game board
   */
  public boolean isCellOccupied(int x, int y) {
    return board[y][x] != 0;
  }

  /**
   * Check if the contents within a row are full of tetrimino cells.
   *
   * @param rowY the y level or row number of given row
   * @param row the row to check
   * @return whether the row is full or not
   */
  private boolean isRowFull(int rowY, int[] row) {
    for (int x = 0; x < row.length; x++) {
      if (!isCellOccupied(x, rowY)) return false;
    }
    return true;
  }

  /**
   * Hold the current tetrimino and swap with the hold tetrimino if available. Block the user from
   * holding if they are already used it
   */
  public void holdTetrimino() {
    if (holdUsed) return;

    if (holdTetrimino == null) {
      holdTetrimino = currentTetrimino;
      currentTetrimino = nextTetrimino;
      nextTetrimino = TetriminoFactory.createRandomTetrimino();
    } else {
      Tetrimino temp = holdTetrimino;
      holdTetrimino = currentTetrimino;
      currentTetrimino = temp;
      currentTetrimino.resetPosition();
    }

    controller.setHoldPieceView(holdTetrimino);
    controller.setNextPieceView(nextTetrimino);

    holdUsed = true;
  }

  /**
   * Varies game speed based on level. Levels are easier up to 10, with difficulty jump at 10, and
   * 15, and kill screen at level 20
   */
  private void changeGameSpeed() {

    if (level < 10) {
      gameSpeed = 100 - (level * 5);
    } else if (level < 15) {
      gameSpeed = 50 - level;
    } else if (level < 20) {
      gameSpeed = 30 - level;
    } else {
      gameSpeed = 3;
    }
  }
}
