package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameScreenController {

    @FXML private AnchorPane basePane;

    GameInstance game = new GameInstance();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            game.start();
            setWindowCloseHandler(getStage());
        });
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
        });
    }

    /** Get current stage, accessed by Anchor pane 'node'. */
    private Stage getStage() { return (Stage) basePane.getScene().getWindow(); }
}
