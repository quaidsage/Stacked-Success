package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.tetriminos.Tetrimino;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameBoardController implements GameInstance.TetriminoUpdateListener {

  @FXML Pane basePane;
  @FXML Pane holdPiece;
  @FXML GridPane gameGrid;
  @FXML GridPane displayGrid;

  @FXML Label scoreLabel;
  @FXML Label levelLabel;

  @FXML ImageView holdPieceView;
  @FXML ImageView nextPieceView;

  private GameInstance gameInstance = new GameInstance();
  private Tetrimino currentTetrimino;
  private int score = 0;

  @FXML
  public void initialize() {

    scoreLabel.setText("Score:" + score);
    levelLabel.setText("Level: 1");
    gameGrid.gridLinesVisibleProperty().setValue(true);
    gameInstance.setTetriminoUpdateListener(this);
    Platform.runLater(
        () -> {
          gameInstance.start();
          gameInstance.getGameBoard().setController(this); // Set the controller
          currentTetrimino = gameInstance.getCurrentTetrimino();
          setWindowCloseHandler(getStage());
        });
  }

  @Override
  public void onTetriminoUpdate(Tetrimino tetrimino) {
    Platform.runLater(() -> addTetrimino(tetrimino));
  }

  @FXML
  private void addTetrimino(Tetrimino tetrimino) {
    renderTetrimino(tetrimino);
  }

  @FXML
  private void renderTetrimino(Tetrimino tetrimino) {
    gameGrid.getChildren().clear(); // Clear previous tetrimino
    gameGrid.gridLinesVisibleProperty().setValue(true);

    int[][] layout = tetrimino.getTetriminoLayout();
    for (int row = 0; row < layout.length; row++) {
      for (int col = 0; col < layout[row].length; col++) {
        if (layout[row][col] != 0) {
          Pane pane = new Pane();
          pane.setStyle("-fx-background-color: black;");
          gameGrid.add(pane, tetrimino.xPos + col, tetrimino.yPos + row);
        }
      }
    }
  }

  @FXML
  public void updateDisplayGrid(Tetrimino tetrimino) {
    Platform.runLater(
        () -> {
          int[][] layout = tetrimino.getTetriminoLayout();
          for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
              if (layout[row][col] != 0) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: black;");
                displayGrid.add(pane, tetrimino.xPos + col, tetrimino.yPos + row);
              }
            }
          }
        });
  }

  @FXML
  public void clearLine(int lineIndex) {
    Platform.runLater(
        () -> {
          displayGrid
              .getChildren()
              .removeIf(
                  node -> {
                    Integer rowIndex = GridPane.getRowIndex(node);
                    return rowIndex != null && rowIndex.intValue() == lineIndex;
                  });

          // Shift rows down
          for (Node node : displayGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            if (rowIndex != null && rowIndex < lineIndex) {
              GridPane.setRowIndex(node, rowIndex + 1);
            }
          }
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
