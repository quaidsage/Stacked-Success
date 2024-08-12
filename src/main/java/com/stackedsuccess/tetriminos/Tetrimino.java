package com.stackedsuccess.tetriminos;

public abstract class Tetrimino {
    protected int[][] layout;
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;

    public int[][] getTetriminoLayout() { return layout; }

    public int getXPos() { return xPos; }

    public int getYPos() { return yPos; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public void rotateClockwise() {}

    public void rotateCounterClockwise() {}
}
