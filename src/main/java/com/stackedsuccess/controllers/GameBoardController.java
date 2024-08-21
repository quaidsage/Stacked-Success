package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.Main;
import com.stackedsuccess.SceneManager;
import com.stackedsuccess.SceneManager.AppUI;
import com.stackedsuccess.ScoreRecorder;
import com.stackedsuccess.tetriminos.*;
import java.io.IOException;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameBoardController {

  @FXML Pane basePane;
  @FXML Pane holdPiece;
  @FXML GridPane displayGrid;

  @FXML Label scoreLabel;
  @FXML Label levelLabel;
  @FXML Label lineLabel;

  @FXML ImageView holdPieceView;
  @FXML ImageView nextPieceView;

  @FXML Button pauseButton;

  @FXML VBox gameOverBox;
  @FXML Label gameOverLabel;
  @FXML Label gameOverScoreLabel;
  @FXML Label gameOverHighScoreLabel;
  @FXML Button gameOverExitButton;
  @FXML Button gameOverRestartButton;

  private final Image blockImage =
      new Image("file:src/main/resources/images/block.png", 42, 42, true, false);
  private final Image highlightImage =
      new Image("file:src/main/resources/images/highlight.png", 42, 42, true, false);

  private static final int SOLID_BLOCK_VALUE = -2;

  private final GameInstance gameInstance = new GameInstance();

  /**
   * Initialises the game board controller, setting up the game grid and starting the game instance.
   *
   * <p>Set everything to default, and start the game instance while also setting listeners for
   * tetrimino updates and setting the game board controller to the current instance.
   */
  @FXML
  public void initialize() {
    resetLabels();
    basePane.requestFocus();

    Platform.runLater(
        () -> {
          gameInstance.start();
          gameInstance.getGameBoard().setController(this);
          nextPieceView.setImage(
              new Image(
                  "/images/"
                      + gameInstance.getGameBoard().getNextTetrimino().getClass().getSimpleName()
                      + ".png"));
          setWindowCloseHandler(getStage());
        });
  }

  /**
   * Updates the visual display of the game board and actively moving tetrimino pieces.
   *
   * @param board the game board to visualise
   */
  @FXML
  public void updateDisplay(int[][] board) {
    int[][] updatedBoard = addMovingPieces(board);

    Platform.runLater(
        () -> {
          displayGrid.getChildren().clear();
          for (int y = 0; y < displayGrid.getRowCount(); y++) {
            for (int x = 0; x < displayGrid.getColumnCount(); x++) {
              int blockValue = updatedBoard[y][x];
              if (blockValue == 0) {
                continue;
              }
              displayGrid.add(getBlock(blockValue), x, y);
            }
          }
        });
  }

  /**
   * Method for handling game over event, when a tetrimino is placed out of bounds.
   *
   * @throws IOException due to ScoreRecorder
   */
  @FXML
  public void gameOver() throws IOException {
    gameInstance.setGameOver(true);
    ScoreRecorder.saveScore(scoreLabel.getText());

    playGameOverAnimation();
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

  /** Pauses the game when the pause button is clicked. */
  @FXML
  public void onClickPauseButton() {
    gameInstance.togglePause();
    basePane.requestFocus();
  }

  @FXML
  void onClickExit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void onClickRestart(ActionEvent event) throws IOException {
    SceneManager.addScene(AppUI.MAIN_MENU, loadFxml("HomeScreen"));
    Main.setUi(AppUI.MAIN_MENU);
  }

  /**
   * Updates the score displayed on the game board.
   *
   * @param score the current score
   */
  public void updateScore(int score) {
    Platform.runLater(() -> scoreLabel.setText(String.valueOf(score)));
  }

  /**
   * Updates the line displayed on the game board.
   *
   * @param line the current level
   */
  public void updateLine(int line) {
    Platform.runLater(() -> lineLabel.setText(String.valueOf(line)));
  }

  /**
   * Updates the level displayed on the game board.
   *
   * @param level the current level
   */
  public void updateLevel(int level) {
    Platform.runLater(() -> levelLabel.setText(String.valueOf(level)));
  }

  /**
   * Sets the view of the next tetromino to be loaded.
   *
   * @param tetrimino the tetrimino to be displayed in the next piece view
   */
  public void setNextPieceView(Tetrimino tetrimino) {
    Image image = new Image("/images/" + tetrimino.getClass().getSimpleName() + ".png");
    nextPieceView.setImage(image);
  }

  /**
   * Sets the view of the hold tetromino to be loaded.
   *
   * @param tetrimino the tetrimino to be displayed in the hold image view
   */
  public void setHoldPieceView(Tetrimino tetrimino) {
    Image image = new Image("/images/" + tetrimino.getClass().getSimpleName() + ".png");
    holdPieceView.setImage(image);
  }

  /**
   * Adds moving tetrimino piece and ghost piece to game board array to support visualising
   * on-screen.
   *
   * @param board the game board
   * @return the game board including current tetrimino and ghost tetrimino
   */
  private int[][] addMovingPieces(int[][] board) {
    // Create clone to separate from original reference
    int[][] newBoard = new int[board.length][];
    for (int i = 0; i < board.length; i++) {
      newBoard[i] = board[i].clone();
    }

    int[][] updatedBoard = addGhostTetriminoPiece(newBoard);
    return addCurrentTetriminoPosition(updatedBoard);
  }

  /**
   * Add position of current tetrimino ghost piece to board to support visualisation.
   *
   * @param board the game board to append ghost position to
   * @return the updated game board
   */
  private int[][] addGhostTetriminoPiece(int[][] board) {
    Tetrimino currentTetrimino = gameInstance.getGameBoard().getCurrentTetrimino();
    int xPos = currentTetrimino.getXPos();
    int yPosGhost = currentTetrimino.calculateGhostY(gameInstance.getGameBoard());
    int width = currentTetrimino.getWidth();
    int height = currentTetrimino.getHeight();
    int[][] tetriminoLayout = currentTetrimino.getTetriminoLayout();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (tetriminoLayout[y][x] != 0) {
          board[yPosGhost + y][xPos + x] = -1;
        }
      }
    }

    return board;
  }

  /**
   * Add position of current tetrimino piece to board to support visualisation.
   *
   * @param board the game board to append position to
   * @return the updated game board
   */
  private int[][] addCurrentTetriminoPosition(int[][] board) {
    Tetrimino currentTetrimino = gameInstance.getGameBoard().getCurrentTetrimino();
    int xPos = currentTetrimino.getXPos();
    int yPos = currentTetrimino.getYPos();
    int width = currentTetrimino.getWidth();
    int height = currentTetrimino.getHeight();
    int[][] tetriminoLayout = currentTetrimino.getTetriminoLayout();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (tetriminoLayout[y][x] != 0) {
          board[yPos + y][xPos + x] = tetriminoLayout[y][x];
        }
      }
    }
    return board;
  }

  /**
   * Get appropriate block image to display in a single cell of the game board.
   *
   * @param blockValue tetrimino piece value in game board
   * @return ImageView of a game element
   */
  private ImageView getBlock(int blockValue) {
    if (blockValue == -1) {
      return new ImageView(highlightImage);
    }

    ImageView tetriminoBlock = new ImageView(blockImage);
    ColorAdjust colorAdjust = new ColorAdjust();

    switch (blockValue) {
      case IShape.SPAWN_VALUE:
        colorAdjust.setHue(-0.5);
        break;
      case JShape.SPAWN_VALUE:
        colorAdjust.setHue(-0.3);
        break;
      case LShape.SPAWN_VALUE:
        colorAdjust.setHue(-0.15);
        break;
      case OShape.SPAWN_VALUE:
        colorAdjust.setHue(0);
        break;
      case SShape.SPAWN_VALUE:
        colorAdjust.setHue(0.15);
        break;
      case TShape.SPAWN_VALUE:
        colorAdjust.setHue(0.3);
        break;
      case ZShape.SPAWN_VALUE:
        colorAdjust.setHue(0.5);
        break;
      case SOLID_BLOCK_VALUE:
        colorAdjust.setSaturation(-1);
        break;
      default:
        throw new IllegalArgumentException("Unknown shape " + blockValue);
    }

    tetriminoBlock.setEffect(colorAdjust);
    return tetriminoBlock;
  }

  /** Reset game scoring labels to default. */
  private void resetLabels() {
    scoreLabel.setText("0");
    levelLabel.setText("1");
    lineLabel.setText("0");
  }

  /** Method for playing game over animation. */
  private void playGameOverAnimation() {
    int rows = displayGrid.getRowCount();
    int cols = displayGrid.getColumnCount();
    Timeline animationTimeline = new Timeline();

    // Fill board with solid blocks
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        final int curRow = row;
        final int curCol = col;
        int delay = (row * 50);
        KeyFrame keyFrame =
            new KeyFrame(
                Duration.millis(delay),
                event -> {
                  displayGrid.add(getBlock(SOLID_BLOCK_VALUE), curCol, curRow);
                });
        animationTimeline.getKeyFrames().add(keyFrame);
      }
    }

    // Add delay before revealing game over elements
    KeyFrame actionsKeyFrame =
        new KeyFrame(
            Duration.millis(1000),
            event -> {
              enableGameOverElements();
            });
    animationTimeline.getKeyFrames().add(actionsKeyFrame);

    // Remove solid blocks
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int delay = 2000 + (row * 50);
        int finalRow = row;
        KeyFrame keyFrame =
            new KeyFrame(
                Duration.millis(delay),
                event -> {
                  displayGrid
                      .getChildren()
                      .removeIf(node -> finalRow == GridPane.getRowIndex(node));
                });
        animationTimeline.getKeyFrames().add(keyFrame);
      }
    }

    animationTimeline.play();
  }

  /** Handles the enabling of elements related to game over screen. */
  private void enableGameOverElements() {
    gameOverBox.setVisible(true);
    gameOverBox.setDisable(false);
    gameOverExitButton.setDisable(false);
    gameOverRestartButton.setDisable(false);
    gameOverExitButton.setVisible(true);
    gameOverRestartButton.setVisible(true);
    gameOverScoreLabel.setText("Score: " + scoreLabel.getText());
    try {
      gameOverHighScoreLabel.setText("High Score: " + ScoreRecorder.getHighScore());
    } catch (IOException e) {
      throw new IllegalArgumentException("Issue regarding ScoreReader");
    }
  }

  /**
   * Creates event handler to stop the game on window close.
   *
   * @param stage the stage containing the game scene
   */
  private void setWindowCloseHandler(Stage stage) {
    stage.setOnCloseRequest(
        event -> {
          gameInstance.setGameOver(true);

          // TODO: Remove when more scenes added.
          System.exit(0);
        });
  }

  /**
   * Get current stage pane.
   *
   * @return current stage
   */
  private Stage getStage() {
    return (Stage) basePane.getScene().getWindow();
  }

  /**
   * Loads the FXML file and returns the parent node.
   *
   * @param fxml the name of the FXML file (without extension)
   * @return the parent node of the input file
   * @throws IOException if the file is not found
   */
  public static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }
}
