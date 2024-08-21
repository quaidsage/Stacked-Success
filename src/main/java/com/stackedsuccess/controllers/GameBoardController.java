package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.ScoreRecorder;
import com.stackedsuccess.tetriminos.*;
import java.io.IOException;
import java.util.*;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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

  private ImageView tetriminoBlock;
  private ImageView tetriminoHighlight;
  private final Image blockImage = new Image("file:src/main/resources/images/block.png");
  private final Image highlightImage = new Image("file:src/main/resources/images/highlight.png");

  private final GameInstance gameInstance = new GameInstance();
  private final ArrayList<Node> previousGhostTetriminos = new ArrayList<>();
  private final static Map<String, String> allTetriminoStyles = new HashMap<>();

  /**
   * Initialises the game board controller, setting up the game grid and starting the game instance.
   *
   * <p>Set everything to default, and start the game instance while also setting listeners for
   * tetrimino updates and setting the game board controller to the current instance.
   */
  @FXML
  public void initialize() {
    displayGrid.gridLinesVisibleProperty().set(true);

    initTetriminoStyles();
    resetLabels();

    Platform.runLater(
        () -> {
          gameInstance.start();
          gameInstance.getGameBoard().setController(this);
          setWindowCloseHandler(getStage());
        });
  }

  // TODO: JavaDocs
  @FXML
  public void updateDisplay(int[][] board) {
    int[][] updatedBoard = addMovingPieces(board);

    Platform.runLater(() -> {
      displayGrid.getChildren().clear();
      for (int y = 0; y < displayGrid.getRowCount(); y++) {
        for (int x = 0; x < displayGrid.getColumnCount(); x++) {
          int blockValue = updatedBoard[y][x];
          if (blockValue == 0) {continue;}
          displayGrid.add(getBlock(blockValue), x, y);
        }
      }
    });
  }

  /**
   * Adds moving tetrimino piece and ghost piece to game board
   * array to display on screen.
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

  // TODO: Javadocs
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

  // TODO: Javadocs
  private ImageView getBlock(int blockValue) {
    tetriminoBlock = new ImageView((blockValue != -1) ? blockImage : highlightImage);
    tetriminoBlock.setFitHeight(45);
    tetriminoBlock.setFitWidth(45);

    if (blockValue == -1) { return tetriminoBlock;}

    ColorAdjust colorAdjust = new ColorAdjust();
    switch (blockValue) {
      case IShape.VALUE:
        colorAdjust.setHue(-0.5);
        break;
      case JShape.VALUE:
        colorAdjust.setHue(-0.3);
        break;
      case LShape.VALUE:
        colorAdjust.setHue(-0.15);
        break;
      case OShape.VALUE:
        colorAdjust.setHue(0);
        break;
      case SShape.VALUE:
        colorAdjust.setHue(0.15);
        break;
      case TShape.VALUE:
        colorAdjust.setHue(0.3);
        break;
      case ZShape.VALUE:
        colorAdjust.setHue(0.5);
        break;
      default:
        throw new IllegalArgumentException("Unknown shape " + blockValue);
    }
    tetriminoBlock.setEffect(colorAdjust);
    return tetriminoBlock;
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
  }

  // TODO: JavaDocs
  private void resetLabels() {
    scoreLabel.setText("0");
    levelLabel.setText("1");
    lineLabel.setText("0");
  }

  // TODO: javadocs
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

  @FXML
    void onClickExit(ActionEvent event) {
        System.exit(0);
    }

  @FXML
    void onClickRestart(ActionEvent event) {
        // will add functionality once main menu is made
    }

  /** Method for initialising the hashmap of Tetrimino colours */
  private void initTetriminoStyles() {
    allTetriminoStyles.clear();
    allTetriminoStyles.put("IShape", "#ff7e00");
    allTetriminoStyles.put("JShape", "#2c349c");
    allTetriminoStyles.put("LShape", "#ec1c24");
    allTetriminoStyles.put("OShape", "#24b44c");
    allTetriminoStyles.put("SShape", "#a424f4");
    allTetriminoStyles.put("TShape", "#fcf404");
    allTetriminoStyles.put("ZShape", "#04b4ec");
  }

  /** Method for playing game over animation. */
  public void playGameOverAnimation() {
    int rows = displayGrid.getRowCount();
    int cols = displayGrid.getColumnCount();
    Timeline animationTimeline = new Timeline();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
          final int curRow = row;
          final int curCol = col;
          int delay = (row * 50); 
          KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), event -> {
              Pane pane = new Pane();
              pane.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; -fx-border-width: 2px;");
              displayGrid.add(pane, curCol, curRow);
          });
          animationTimeline.getKeyFrames().add(keyFrame);
      }
    }

    KeyFrame actionsKeyFrame = new KeyFrame(Duration.millis(1000), event -> {
      displayGrid.gridLinesVisibleProperty().set(false);
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
        e.printStackTrace();
      }
    });
    animationTimeline.getKeyFrames().add(actionsKeyFrame);

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
          final int curRow = row;
          final int curCol = col;
          int delay = 2000 + (row * 50); 
          KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), event -> {
              displayGrid.getChildren().removeIf(node -> {
                  
                  Integer rowIndex = GridPane.getRowIndex(node);
                  Integer colIndex = GridPane.getColumnIndex(node);
                  return rowIndex != null && colIndex != null && rowIndex.intValue() == curRow && colIndex.intValue() == curCol;
              });
          });
          animationTimeline.getKeyFrames().add(keyFrame);
      }
    }
        
    animationTimeline.play();
  }

  /**
   * Method for handling game over event, when tetriminos spawn and collide into each other. Exits
   * the game when called
   *
   * @throws IOException
   */
  @FXML
  public void gameOver() throws IOException {
    gameInstance.setGameOver(true);

    // Save if score is a high score
    if (ScoreRecorder.isHighScore(scoreLabel.getText())) {
      ScoreRecorder.saveScore(scoreLabel.getText());
    }

    playGameOverAnimation();
    

    //System.exit(0);
  }
}
