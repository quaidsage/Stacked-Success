package com.stackedsuccess;

import com.stackedsuccess.tetriminos.Tetrimino;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.input.KeyEvent;

// This class defines the game instance, controlling the game loop for the current game.
public class GameInstance {

  private boolean isGameOver;
  private int score;

  private GameControls gameControls;
  private GameBoard gameBoard;
  private int gameDelay;

  private boolean isPaused;

  private Tetrimino currentTetrimino;
  private TetriminoUpdateListener tetriminoUpdateListener;

  /**
   * Constructs a new instance of the game with default initial settings.
   *
   * <p>This constructor initialises the game state by setting the initial score,
   * game delay, and flags for pause and game over status</p>
   */
  public GameInstance() {
    score = 0;
    gameDelay = 10;
    isPaused = false;
    isGameOver = false;
  }

  /**
   * An interface for listening to updates of a Tetrimino.
   */
  public interface TetriminoUpdateListener {
    void onTetriminoUpdate(Tetrimino tetrimino);
  }

  /**
   * Sets the listener for Tetrimino updates
   *
   * @param listener The TetriminoUpdateListener to be notified of updates.
   */
  public void setTetriminoUpdateListener(TetriminoUpdateListener listener) {
    this.tetriminoUpdateListener = listener;
  }

  /**
   * Notifies the registered listener that the current Tetrimino has been updated
   *
   * <p>This method is called whenever the Tetrimino's state changes. It checks if a
   * listener has been registered, and if so, it triggers the listener's
   * onTetriminoUpdate() method, passing the updated Tetrimino as an argument.</p>
   */
  private void notifyTetriminoUpdate() {
    if (tetriminoUpdateListener != null) {
      tetriminoUpdateListener.onTetriminoUpdate(currentTetrimino);
    }
  }

  /**
   * Starts the game instance to periodically update the game board and handle game movement.
   *
   * <p>This method initialises the game board, retrieves the current Tetrimino, and sets up the
   * game controls. It also creates a timer that periodically triggers the game loop, which updates
   * the game state, handles Tetrimino movements, and checks whether the game is paused or over.</p>
   */
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
              try {
                gameBoard.update();
              } catch (IOException e) {
                // Do nothing for now
              }
              currentTetrimino = gameBoard.getCurrentTetrimino();
              notifyTetriminoUpdate();
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
      if (updatesTetrimino(action) && !isPaused) {
        gameBoard.getCurrentTetrimino().updateTetrimino(gameBoard, action);
      } else {
        if (action == Action.PAUSE) {
          togglePause();
        } else if (action == Action.HOLD) {
          gameBoard.holdTetrimino();
        }
      }
    }
  }

  /** Toggles the game to be paused, halting game updates. */
  public void togglePause() {
    if (!isGameOver) isPaused = !isPaused;
  }

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
   * Get the current game board.
   *
   * @return current game board
   */
  public GameBoard getGameBoard() {
    return gameBoard;
  }

  /**
   * Get the current game score.
   *
   * @return current game score
   */
  public int getScore() {
    return score;
  }

  /**
   * Get game over status
   *
   * @return if game instance is over
   */
  public boolean isGameOver() {
    return isGameOver;
  }

  /**
   * Set game over status
   *
   * @param isGameOver the game over status to set
   */
  public void setGameOver(boolean isGameOver) {
    this.isGameOver = isGameOver;
    this.isPaused = isGameOver;
  }

  /**
   * This method checks whether the game is currently paused
   *
   * @return if the game is currently paused
   */
  public boolean isPaused() {
    return isPaused;
  }

}
