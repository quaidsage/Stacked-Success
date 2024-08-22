package com.stackedsuccess.controllers;

import com.stackedsuccess.Main;
import com.stackedsuccess.SceneManager;
import com.stackedsuccess.SceneManager.AppUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeScreenController {
  @FXML Slider difficultySlider;

  @FXML private Button pastScoresButton;
  @FXML private ListView<String> pastScores;

  /**
   * Initialises the Home Screen controller by setting up the home screen.
   *
   * <p>This method sets the initial focus on the difficulty slider (1) and loads past scores
   * from a file into the list view component that displays previous game scores if the players
   * clicks on the past scores button</p>
   */
  @FXML
  public void initialize() {
    difficultySlider.requestFocus();
    List<String> scores = loadScoresFromFile("score.txt");
    pastScores.getItems().addAll(scores);
    pastScoresButton.setFocusTraversable(false);
  }

  /**
   * Reads scores from a file and returns them as a list of strings.
   *
   * @param filePath The path to the score file that will be read.
   * @return A list of scores as strings, where each string represents one line from the file.
   */
  private List<String> loadScoresFromFile(String filePath) {
    List<String> scores = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      //reads each line from the file and adds to the score list
      while ((line = reader.readLine()) != null) {
        scores.add(line);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Issue regarding Score file", e);
    }
    return scores;
  }

  /**
   * Closes the game when the close button is clicked
   *
   * <p>This method is linked to the close button. When the
   * button is clicked, the application will terminate</p>
   */
  public void exitGame() {
    System.exit(0);
  }

  /**
   * Starts a new game by loading the game board UI and initialising the game at the selected difficulty level.
   *
   * <p>This method retrieves the initial difficulty level from the slider of 1-20, loads the game board
   * from the corresponding FXML file, and updates the game board controller with the selected level.
   * It then switches the UI to the game screen.</p>
   *
   * @throws IOException If there is an issue loading the FXML file for the game board.
   */
  @FXML
  public void startGame() throws IOException {
    // Get the level from the slider
    int initialLevel = (int) difficultySlider.getValue();

    // loads the game board fxml file
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/GameBoard.fxml"));
    Parent root = loader.load();
    GameBoardController controller = loader.getController();

    // Set the initial level
    controller.updateLevel(initialLevel);
    SceneManager.addScene(AppUI.GAME, root);
    Main.setUi(AppUI.GAME);
  }

  /**
   * Handles key press events, focusing the difficulty slider and starting the game if the spacebar is pressed.
   *
   * <p>This method is triggered whenever a key is pressed. It first sets the focus to the difficulty slider.
   * If the spacebar is pressed, it attempts to start the game</p>
   *
   * @param event The key event triggered by the key press.
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    difficultySlider.requestFocus();
    if (event.getCode() == KeyCode.SPACE) {
      try {
        startGame();
      } catch (IOException e) {
        // Do nothing for now
      }
    }
  }

  /**
   * Shows the players past scores in a list view
   *
   * <p>When the button is pressed, a list of the players previous scores that are saved are shown so that
   * the player can check their high scores in the game</p>
   */
  @FXML
  public void showPastScores() {
    if (pastScores.isVisible()) {
      pastScores.setVisible(false);
    } else {
      pastScores.setVisible(true);
    }
  }
}
