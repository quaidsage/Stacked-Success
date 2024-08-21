package com.stackedsuccess.controllers;

import com.stackedsuccess.Main;
import com.stackedsuccess.SceneManager;
import com.stackedsuccess.SceneManager.AppUI;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
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

    @FXML
    public void initialize() {
         difficultySlider.requestFocus();
         List<String> scores = loadScoresFromFile("score.txt");
         pastScores.getItems().addAll(scores);
    }

    /**
     * Reads scores from a file and returns them as a list of strings.
     *
     * @param filePath the path to the score file
     * @return list of scores as strings
     */
    private List<String> loadScoresFromFile(String filePath) {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Issue regarding Score file", e);
        }
        return scores;
    }

  public void exitGame() {
    System.exit(0);
  }

  @FXML
  public void startGame() throws IOException {
    int initialLevel = (int) difficultySlider.getValue(); // Get the level from the slider
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/GameBoard.fxml"));
    Parent root = loader.load();
    GameBoardController controller = loader.getController();
    controller.updateLevel(initialLevel); // Set the initial level
    SceneManager.addScene(AppUI.GAME, root);
    Main.setUi(AppUI.GAME);
  }

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

    @FXML
    public void showPastScores() {
        if (pastScores.isVisible()) {
            pastScores.setVisible(false);
        } else {
            pastScores.setVisible(true);
        }
    }
}
