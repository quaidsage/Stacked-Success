package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.tetriminos.Tetrimino;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameBoardController {

  @FXML Pane basePane;
  @FXML Pane holdPiece;
  @FXML GridPane gameGrid;

  @FXML Label scoreLabel;
  @FXML Label levelLabel;

  @FXML ImageView holdPieceView;
  @FXML ImageView nextPieceView;

  private GameInstance gameInstance = new GameInstance();
  private Tetrimino currentTetrimino;

  @FXML
  public void initialize() {

    scoreLabel.setText("Score: 0");
    levelLabel.setText("Level: 1");
    gameGrid.gridLinesVisibleProperty().setValue(true);

    Platform.runLater(
        () -> {
          gameInstance.start();
          currentTetrimino = gameInstance.getCurrentTetrimino();
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
    gameInstance.handleInput(event);
  }

  /**
   * Creates event handler to stop the game on window close.
   *
   * @param stage the stage containing the game scene
   */
  private void setWindowCloseHandler(Stage stage) {
    stage.setOnCloseRequest(
        event -> {
          System.out.println("Game ended due to window close. ");
          gameInstance.isGameOver = true;

          // TODO: Remove when more scenes added.
          System.exit(0);
        });
  }

  /**
   * Get current stage, accessed by Anchor pane 'node'.
   *
   * @return current stage
   */
  private Stage getStage() {
    return (Stage) basePane.getScene().getWindow();
  }
}
