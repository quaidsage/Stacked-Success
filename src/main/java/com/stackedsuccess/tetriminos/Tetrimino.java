package com.stackedsuccess.tetriminos;

import com.stackedsuccess.Action;
import com.stackedsuccess.GameBoard;

public abstract class Tetrimino {
    protected int[][] layout;
    protected int width;
    protected int height;

    public int xPos;
    public int yPos;

    /**
     * Updates tetrimino based on given action and game board state.
     *
     * @param gameBoard the current game board
     * @param action the movement action on the tetrimino
     */
    public void updateTetrimino(GameBoard gameBoard, Action action) {
        // TODO: Add tetrimino highlight update

        switch (action) {
            case MOVE_LEFT:
                if (!gameBoard.checkCollision(xPos-1,yPos)) xPos--;
                break;
            case MOVE_RIGHT:
                if (!gameBoard.checkCollision(xPos+1,yPos)) xPos++;
                break;
            case MOVE_DOWN:
                if (!gameBoard.checkCollision(xPos,yPos+1)) yPos++;
                break;
            case ROTATE_CLOCKWISE:
                rotateClockwise();
                break;
            case ROTATE_COUNTERCLOCKWISE:
                rotateCounterClockwise();
                break;
            case HARD_DROP:
                while (!gameBoard.checkCollision(xPos,yPos+1)) yPos++;
                break;
            default:
                return;
        }
    }

    /**
     * Get-type function.
     *
     * @return tetrimino layout as 2D integer array
     */
    public int[][] getTetriminoLayout() { return layout; }

    /**
     * Get-type function.
     *
     * @return tetrimino width
     */
    public int getWidth() { return width; }

    /**
     * Get-type function.
     *
     * @return tetrimino height
     */
    public int getHeight() { return height; }

    /** Rotate tetrimino layout clockwise. */
    private void rotateClockwise() {
        int[][] rotatedLayout = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rotatedLayout[width - j - 1][i] = layout[i][j];
            }
        }
        layout = rotatedLayout;
    }

    /** Rotate tetrimino layout counter-clockwise. */
    private void rotateCounterClockwise() {
        for (int i = 0; i < 3; i ++) rotateClockwise();
    }

}
