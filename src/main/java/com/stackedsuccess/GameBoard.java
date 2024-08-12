package com.stackedsuccess;

// This class defines the game board and functionality to check board state
public class GameBoard {
    private static final int DEFAULT_BOARD_WIDTH = 10;
    private static final int DEFAULT_BOARD_HEIGHT = 20;

    private int[][] board;

    private int updateCount;

    public GameBoard() {
        board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
        updateCount = 0;
    }

    public GameBoard(int width, int height) {
        board = new int[width][height];
        updateCount = 0;
    }

    /** Update the state of the board. */
    public void update() {
        // TODO: Add update cycle for the game board
        updateCount++;
        printBoard();
    }

    /** Utility to help separate game board from JavaFX elements. */
    private void printBoard() {
        System.out.println("===| " + updateCount + " |===");
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private void clearLines() {
        // TODO: Add functionality to check whether lines are full, clear lines, and move lines above downwards.
    }
}
