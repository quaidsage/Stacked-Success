package com.stackedsuccess.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.Main;
import com.stackedsuccess.Main.SceneName;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class HomeScreenController {

    @FXML
    private Button pastScoresButton;
    @FXML
    private ListView<String> pastScores;

    public void initialize() {
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

    public void startGame() {
        GameInstance gameInstance = new GameInstance();
        gameInstance.start();
        Stage stage = (Stage) Main.getPrimaryStage(); // Assumes there's a method in Main to get the primary stage
        Parent gameBoard = Main.getScene(SceneName.GAMEBOARD);
        Scene scene = new Scene(gameBoard);
        stage.setScene(scene);
        gameBoard.requestFocus(); // Ensures the game board has focus for key events
    }

    public void onKeyPressed(KeyEvent event) {
        // Your code here
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