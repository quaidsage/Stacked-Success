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

    public void rotateClockwise() {}

    public void rotateCounterClockwise() {}
}
