package com.stackedsuccess.tetriminos;

public abstract class Tetrimino {
    protected int[][] layout;
    protected int width;
    protected int height;

    public int xPos;
    public int yPos;

    /** Rotate tetrimino layout clockwise. */
    public void rotateClockwise() {
        int[][] rotatedLayout = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rotatedLayout[width - j - 1][i] = layout[i][j];
            }
        }
        layout = rotatedLayout;
    }

    /** Rotate tetrimino layout counter-clockwise. */
    public void rotateCounterClockwise() {
        for (int i = 0; i < 3; i ++) rotateClockwise();
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
}
