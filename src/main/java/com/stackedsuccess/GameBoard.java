package com.stackedsuccess;

// This class defines the game board and functionality to check board state
public class GameBoard {
    private static final int DEFAULT_BOARD_WIDTH = 10;
    private static final int DEFAULT_BOARD_HEIGHT = 20;

    private int[][] board;

    public GameBoard() {
        board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
    }

    public GameBoard(int width, int height) {
        board = new int[width][height];
    }

    public void update() {
        // TODO: Add update cycle for the game board
    }

    private void clearLines() {
        // TODO: Add functionality to check whether lines are full, clear lines, and move lines above downwards.
    }
}
