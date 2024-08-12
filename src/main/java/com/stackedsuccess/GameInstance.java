package com.stackedsuccess;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

// This class defines the game instance, controlling the game loop for the current game.
public class GameInstance {
    public boolean isGameOver;
    public int score;

    private GameControls gameControls;
    private GameBoard gameBoard;
    private boolean isPaused;

    public GameInstance() {
        score = 0;
        isPaused = false;
        isGameOver = false;
    }

    // TODO: Adjust timer period to be appropriate for game speed.
    /** Starts the game instance to periodically update the game board and handle game movement. */
    public void start() {
        gameBoard = new GameBoard();
        gameControls = new GameControls();

        // Create timer to regularly update game loop when not paused.
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused && !isGameOver) {
                    gameBoard.update();
                }
            }
        },0,10);
    }

    /**
     * Handles the key events for current game based on set controls.
     *
     * @param key the KeyEvent triggered by user in game window
     */
    public void handleInput(KeyEvent key) {
        Action action = gameControls.getAction(key);
        if (action != null) {
            switch (action) {
                case MOVE_DOWN -> gameBoard.moveDown();
                case MOVE_LEFT -> gameBoard.moveLeft();
                case MOVE_RIGHT -> gameBoard.moveRight();
                case ROTATE_CLOCKWISE -> gameBoard.rotateClockwise();
                case ROTATE_COUNTERCLOCKWISE -> gameBoard.rotateCounterClockwise();
                case PAUSE -> togglePause();
            }
        }
    }

    /** Toggles the game to be paused, halting game updates. */
    public void togglePause() { isPaused = !isPaused; }

}
