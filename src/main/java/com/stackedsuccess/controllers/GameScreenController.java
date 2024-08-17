package com.stackedsuccess.controllers;

import com.stackedsuccess.GameInstance;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GameScreenController {

  @FXML private AnchorPane basePane;
  @FXML private Text temp;

  GameInstance game = new GameInstance();

  /** On game screen initialization, start game and set window exit handle. */
  @FXML
  public void initialize() {
    // Empty currently as this screen has not been completed yet -> could be deleted if not needed
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
}
