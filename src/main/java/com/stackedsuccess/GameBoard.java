package com.stackedsuccess;

import com.stackedsuccess.tetriminos.*;

// This class defines the game board and functionality to check board state
public class GameBoard {
    private static final int DEFAULT_BOARD_WIDTH = 10;
    private static final int DEFAULT_BOARD_HEIGHT = 20;

    private final int[][] board;
    private Tetrimino currentTetrimino;
    private int frameCount;

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
        frameCount = 0;
    }

    // TODO: Change logic of how speed of current tetrimino is calculated based on frame rate.
    /** Update the state of the board. */
    public void update() {
        frameCount++;
        if (frameCount % 100 == 0) {
            if (!checkCollision(currentTetrimino.xPos, currentTetrimino.yPos + 1)) {
                moveDown();
            } else {
                placeTetrimino(currentTetrimino);
                currentTetrimino = TetriminoFactory.createRandomTetrimino();
            }
        }
        printBoard();
    }

    // TODO: Refactor the movement functions to tetrimino object to separate movement from game board.
    /** Move current tetrimino downwards by one cell. */
    public void moveDown() {
        if (!checkCollision(currentTetrimino.xPos, currentTetrimino.yPos+1)) currentTetrimino.yPos++;;
    }

    /** Move current tetrimino left by one cell. */
    public void moveLeft() {
        if (!checkCollision(currentTetrimino.xPos-1, currentTetrimino.yPos)) currentTetrimino.xPos--;;
    }

    /** Move current tetrimino right by one cell. */
    public void moveRight() {
        if (!checkCollision(currentTetrimino.xPos+1, currentTetrimino.yPos)) currentTetrimino.xPos++;;
    }

    /** Rotate current tetrimino clockwise. */
    public void rotateClockwise() {
        currentTetrimino.rotateClockwise();
    }

    /** Rotate current tetrimino counter-clockwise. */
    public void rotateCounterClockwise() {
        currentTetrimino.rotateCounterClockwise();
    }

    /**
     * Appends new tetrimino to the game board.
     *
     * @param tetrimino the tetrimino to place on the game board.
     */
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

    // TODO: Remove when visuals are ported to JavaFX.
    /** Debug utility to help separate game board from JavaFX elements. */
    private void printBoard() {
        int[][] layout = currentTetrimino.getTetriminoLayout();
        System.out.println("===| " + frameCount + " |===");
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

    /**
     * Checks if current tetrimino will collide with borders or existing cells.
     *
     * @param x the x position to start check for collision
     * @param y the y position to start check for collision
     * @return true if current tetrimino will collide with border or existing cells
     */
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

    /**
     * Checks if coordinates are outside the bounds of the game board.
     *
     * @param x the x position to check
     * @param y the y position to check
     * @return true if the coordinates are out of bounds
     */
    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= board[0].length || y < 0 || y >= board.length;
    }

    /**
     * Check if coordinates are occupied in the game board.
     *
     * @param x the x position to check
     * @param y the y position to check
     * @return true if cell is occupied in game board
     */
    private boolean isCellOccupied(int x, int y) {
        return board[y][x] != 0;
    }

    private void clearLines() {
        // TODO: Add functionality to check whether lines are full, clear lines, and move above lines above downwards.
    }
}
