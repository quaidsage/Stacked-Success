package com.stackedsuccess;

import com.stackedsuccess.tetriminos.*;

// This class defines the game board and functionality to check board state
public class GameBoard {
    private static final int DEFAULT_BOARD_WIDTH = 10;
    private static final int DEFAULT_BOARD_HEIGHT = 20;

    private final int[][] board;
    private Tetrimino currentTetrimino;
    private int updateCount;

    public GameBoard() {
        board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
        initializeBoard();
    }

    public GameBoard(int width, int height) {
        board = new int[width][height];
        initializeBoard();
    }

    /** Setup initial tetrimino pieces and board metrics. */
    private void initializeBoard() {
        currentTetrimino = TetriminoFactory.createRandomTetrimino();
        updateCount = 0;
    }

    /** Update the state of the board. */
    public void update() {
        updateCount++;
        if (!checkCollision(currentTetrimino.xPos, currentTetrimino.yPos + 1)) {
            moveDown();
        } else {
            placeTetrimino(currentTetrimino);
            currentTetrimino = TetriminoFactory.createRandomTetrimino();
        }
        printBoard();
    }

    /** Appends new tetrimino to the game board. */
    private void placeTetrimino(Tetrimino tetrimino) {
        int[][] layout = tetrimino.getTetriminoLayout();

        for (int layoutY = 0; layoutY < tetrimino.getHeight(); layoutY++) {
            for (int layoutX = 0; layoutX < tetrimino.getWidth(); layoutX++) {
                if (layout[layoutY][layoutX] != 0) {
                    board[tetrimino.yPos + layoutY][tetrimino.xPos + layoutX] = layout[layoutY][layoutX];
                }
            }
        }
    }

    /** Debug utility to help separate game board from JavaFX elements. */
    private void printBoard() {
        // TODO: Completely refactor to be appropriate with future design.
        int[][] layout = currentTetrimino.getTetriminoLayout();
        System.out.println("===| " + updateCount + " |===");
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if ((y >= currentTetrimino.yPos && x >= currentTetrimino.xPos && y < currentTetrimino.yPos + currentTetrimino.getHeight() && x < currentTetrimino.xPos + currentTetrimino.getWidth()) && layout[y - currentTetrimino.yPos][x - currentTetrimino.xPos] != 0) {
                    System.out.print("█" + " ");
                } else if (board[y][x] != 0) {
                    System.out.print("█" + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }

    private void moveDown() {
        currentTetrimino.yPos++;
    }

    private boolean checkCollision(int x, int y) {
        int[][] layout = currentTetrimino.getTetriminoLayout();
        int newX, newY;

        for (int layoutY = 0; layoutY < currentTetrimino.getHeight(); layoutY++) {
            for (int layoutX = 0; layoutX < currentTetrimino.getWidth(); layoutX++) {
                if (layout[layoutY][layoutX] != 0) {
                    newX = x + layoutX;
                    newY = y + layoutY;

                    // Check for out of bound collisions
                    if (isOutOfBounds(newX, newY)) return true;

                    // Check for existing tetrimino cells
                    if (isCellOccupied(newX, newY)) return true;
                }
            }
        }
       return false;
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= board[0].length || y < 0 || y >= board.length;
    }

    private boolean isCellOccupied(int x, int y) {
        return board[y][x] != 0;
    }

    private void clearLines() {
        // TODO: Add functionality to check whether lines are full, clear lines, and move lines above downwards.
    }
}
