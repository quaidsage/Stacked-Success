package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import com.stackedsuccess.Main;
import com.stackedsuccess.Main.SceneName;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class HomeScreenController {

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

}