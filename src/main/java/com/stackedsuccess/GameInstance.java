package com.stackedsuccess;

import com.stackedsuccess.tetriminos.Tetrimino;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.input.KeyEvent;

// This class defines the game instance, controlling the game loop for the current game.
public class GameInstance {
  public boolean isGameOver;
  public int score;

  private GameControls gameControls;
  private GameBoard gameBoard;
  private int gameDelay;
  private boolean isPaused;

  private Tetrimino currentTetrimino;

  public GameInstance() {
    score = 0;
    gameDelay = 10;
    isPaused = false;
    isGameOver = false;
  }

  /** Starts the game instance to periodically update the game board and handle game movement. */
  public void start() {
    gameBoard = new GameBoard();
    currentTetrimino = gameBoard.getCurrentTetrimino();
    gameControls = new GameControls();

    // Create timer to regularly update game loop when not paused.
    Timer timer = new Timer();
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            if (!isPaused && !isGameOver) {
              gameBoard.update();
              currentTetrimino = gameBoard.getCurrentTetrimino();
            }
          }
        },
        0,
        gameDelay);
  }

  /**
   * Handles the key events for current game based on set controls.
   *
   * @param key the KeyEvent triggered by user in game window
   */
  public void handleInput(KeyEvent key) {
    Action action = gameControls.getAction(key);
    if (action != null) {
      if (updatesTetrimino(action)) {
        gameBoard.getCurrentTetrimino().updateTetrimino(gameBoard, action);
      } else {
        switch (action) {
          case PAUSE -> togglePause();
        }
      }
    }
  }

  /** Toggles the game to be paused, halting game updates. */
  public void togglePause() {
    isPaused = !isPaused;
  }

  // TODO: Refactor how an action is determined as a 'movement' action.
  /**
   * Check if the given Action will update tetrimino.
   *
   * @param action the action to check
   * @return whether action will update tetrimino or not
   */
  private boolean updatesTetrimino(Action action) {
    return action == Action.MOVE_LEFT
        || action == Action.MOVE_RIGHT
        || action == Action.MOVE_DOWN
        || action == Action.HARD_DROP
        || action == Action.ROTATE_CLOCKWISE
        || action == Action.ROTATE_COUNTERCLOCKWISE;
  }

  /**
   * Get the current Tetrimino.
   *
   * @return current tetrimino
   */
  public Tetrimino getCurrentTetrimino() {
    return currentTetrimino;
  }
}
