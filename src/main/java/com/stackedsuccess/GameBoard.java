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
  private int baseLevel = 1;
  private int totalLinesCleared = 0;
  private int gameSpeed;

  private GameBoardController controller;

  /**
   * Initialises a new instance of the Game Board class with the default board dimensions.
   *
   * <p>This constructor sets up the game board by creating a 2D array with the default width and height.
   * It then calls the initializeBoard() method to prepare the board for gameplay.</p>
   */
  public GameBoard() {
    //creates the boards default dimensions
    board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
    this.width = DEFAULT_BOARD_WIDTH;
    this.height = DEFAULT_BOARD_HEIGHT;
    initializeBoard();
  }

  /**
   * Sets up the initial Tetrimino pieces and initialises board-related metrics.
   *
   * <p>This method is responsible for creating the current and next Tetrimino pieces
   * using the Tetrimino factory. It also initialises the frame count and
   * game speed to their starting values.</p>
   */
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

  /**
   * Updates the state of the game board. This method is called once per frame.
   * It handles the timing of tetrimino movement and updates the display.
   *
   * @throws IOException if an error occurs during display update
   */
  public void update() throws IOException {
    frameCount++;
    controller.updateDisplay(board);

    // Stagger automatic tetrimino movement based on frame count
    // Moves the tetrimino down if the framecount is a multiple of the gamespeed
    // Or if the forceUpdate (such as hard drop) is true
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

  public void setBaseLevel(int baseLevel) {
    this.baseLevel = baseLevel;
    updateLevel();
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
   * Appends a new tetrimino to the game board at its current position.
   * This method places the blocks of the tetrimino onto the game board.
   *
   * @param tetrimino the tetrimino to place on the game board
   */
  private void placeTetrimino(Tetrimino tetrimino) {
    int[][] layout = tetrimino.getTetriminoLayout();
    for (int layoutY = 0; layoutY < tetrimino.getHeight(); layoutY++) {
      for (int layoutX = 0; layoutX < tetrimino.getWidth(); layoutX++) {
        // checks if the current pos is occupied
        if (layout[layoutY][layoutX] != 0) {
          // then calculates the position on the board where it will be placed
          int spawnX = tetrimino.getXPos() + layoutX;
          int spawnY = tetrimino.getYPos() + layoutY;
          // then places the piece at the calculated position
          board[spawnY][spawnX] = layout[layoutY][layoutX];
        }
      }
    }
  }

  /**
   * Checks the top two rows of the game board to determine if any pieces are
   * out of bounds, indicating a game-over condition.
   *
   * @return true if the game is over due to pieces in the top rows, false otherwise
   * @throws IOException if an error occurs while handling the game over condition
   */
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

  /**
   * Method to clear any full rows on the board
   *
   * <p>Clears full rows on the game board and shifts the rows above downward.
   * It also updates the score, the total number of cleared lines, and adjusts
   * the game level and speed accordingly.</p>
   */
  private void clearFullRows() {
    int newLinesCleared = 0;
    for (int y = 0; y < board.length; y++) {
      if (isRowFull(y, board[y])) {
        //moves all the rows that are above, down one
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

  /** Updates the level based on the number of lines cleared.
   *
   * <p>For every 10 lines that are cleared, the game will increase
   * by one level</p>
   */
  private void updateLevel() {
    level = (totalLinesCleared / 10) + baseLevel;
    controller.updateLevel(level);
    changeGameSpeed();
  }

  /**
   * Calculates the score based on the number of lines cleared.
   *
   * @param linesCleared the number of lines cleared
   */
  private void calculateScore(int linesCleared) {
    switch (linesCleared) {
      // the different calculations of scores, whether 1,2,3, or 4 lines are cleared at once
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
   * Holds the current tetrimino and swaps it with the held tetrimino if one is already held.
   * The user is blocked from holding another tetrimino until the current one is placed.
   */
  public void holdTetrimino() {
    // check if a player is already holding a piece
    if (holdUsed) return;

    // if there is no tetrimino being currently held, store the current one
    if (holdTetrimino == null) {
      holdTetrimino = currentTetrimino;
      currentTetrimino = nextTetrimino;
      nextTetrimino = TetriminoFactory.createRandomTetrimino();
    } else {
      //if a tetriminio is already being held swap the current with the held
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
   * Adjusts the game speed based on the current level. The game speed decreases (game gets faster) as
   * the player progresses through the levels, with noticeable difficulty jumps at levels 10 and 15.
   * At level 20, the game reaches a "kill screen" speed where it becomes extremely difficult to continue.
   * Essentially creating a hard cap to the game.
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

  /**
   * Get the next tetrimino piece.
   *
   * @return the next tetrimino piece
   */
  public Tetrimino getNextTetrimino() {
    return nextTetrimino;
  }
}
