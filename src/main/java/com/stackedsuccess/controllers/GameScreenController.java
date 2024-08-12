package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameScreenController {

    @FXML private AnchorPane basePane;

    GameInstance game = new GameInstance();

    /** On game screen initialization, start game and set window exit handle. */
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            game.start();
            setWindowCloseHandler(getStage());
        });
    }

    /**
     * Sends the key pressed event to game instance to utilise.
     *
     * @param event the key event
     */
    @FXML
    public void onKeyPressed(KeyEvent event) {
        game.handleInput(event);
    }

    /**
     * Creates event handler to stop the game on window close.
     *
     * @param stage the stage containing the game scene
     */
    private void setWindowCloseHandler(Stage stage) {
        stage.setOnCloseRequest(event -> {
            System.out.println("Game ended due to window close. ");
            game.isGameOver = true;

            // TODO: Remove later...
            System.exit(0);
        });
    }

    /**
     * Get current stage, accessed by Anchor pane 'node'.
     *
     * @return current stage
     */
    private Stage getStage() { return (Stage) basePane.getScene().getWindow(); }
}
