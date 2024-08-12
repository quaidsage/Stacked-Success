package com.stackedsuccess;

// This class defines the game instance, controlling the game loop for the current game.
public class GameInstance {

    public GameInstance() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.update();
    }

}
