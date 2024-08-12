package com.stackedsuccess;

import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

// This class defines the game instance, controlling the game loop for the current game.
public class GameInstance {
    public boolean isGameOver;
    public int score;

    private Timer timer;
    private boolean isPaused;

    public GameInstance() {
        score = 0;
        isPaused = false;
        isGameOver = false;
    }

    /** Starts the game instance to periodically update the game board and handle game movement. */
    public void start() {
        GameBoard gameBoard = new GameBoard();

        // Create timer to regularly update game loop when not paused.
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused && !isGameOver) {
                    gameBoard.update();
                }
            }
        },0,100);
    }

    /** Toggles the game to be paused, halting game updates. */
    public void togglePause() { isPaused = !isPaused; }

}
