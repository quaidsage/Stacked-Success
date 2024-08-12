package com.stackedsuccess.tetriminos;

public abstract class Tetrimino {
    protected int[][] layout;
    protected int width;
    protected int height;

    public int xPos;
    public int yPos;


    public int[][] getTetriminoLayout() { return layout; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public void rotateClockwise() {
        int[][] rotatedLayout = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rotatedLayout[j][height - i - 1] = layout[i][j];
            }
        }
        layout = rotatedLayout;
    }

    public void rotateCounterClockwise() {
        for (int i = 0; i < 3; i ++) rotateClockwise();
    }
}
