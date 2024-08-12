package com.stackedsuccess;

import com.stackedsuccess.tetriminos.*;

// This class defines the game board and functionality to check board state
public class GameBoard {
    private static final int DEFAULT_BOARD_WIDTH = 10;
    private static final int DEFAULT_BOARD_HEIGHT = 20;

    private int[][] board;
    private Tetrimino currentTetrimino = TetriminoFactory.createRandomTetrimino();
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
        updateCount++;
        currentTetrimino = new IShape();
        addTetrimino(currentTetrimino);
        printBoard();
        //currentTetrimino = TetriminoFactory.createRandomTetrimino();
    }

    /** Appends new tetrimino to the game board. */
    private void addTetrimino(Tetrimino tetrimino) {
        int xPos = tetrimino.getXPos();
        int yPos = tetrimino.getYPos();
        int[][] layout = tetrimino.getTetriminoLayout();
        int width = tetrimino.getWidth();
        int height = tetrimino.getHeight();

        for (int layoutY = 0; layoutY < height; layoutY++) {
            for (int layoutX = 0; layoutX < width; layoutX++) {
                if (layout[layoutY][layoutX] != 0) {
                    board[yPos + layoutY][xPos + layoutX] = layout[layoutY][layoutX];
                }
            }
        }
    }

    /** Utility to help separate game board from JavaFX elements.*/
    private void printBoard() {
        System.out.println("===| " + updateCount + " |===");
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private void moveDown() {
        // TODO: Add functionality.
    }

    private void checkCollision() {
        // TODO: Add collision detection for future steps
    }

    private void clearLines() {
        // TODO: Add functionality to check whether lines are full, clear lines, and move lines above downwards.
    }
}
