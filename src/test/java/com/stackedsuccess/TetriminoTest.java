package com.stackedsuccess;

import com.stackedsuccess.controllers.GameBoardController;
import com.stackedsuccess.tetriminos.Tetrimino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TetriminoTest {

    private GameBoard gameBoard;
    private GameBoardController mockController;

    @BeforeEach
    void setUp() {
        mockController = Mockito.mock(GameBoardController.class);
        gameBoard = new GameBoard();
        gameBoard.setController(mockController);
    }

    @Test
    void testTetriminoLeft() {
        Tetrimino t = gameBoard.getCurrentTetrimino();
        int before = t.getXPos();
        t.updateTetrimino(gameBoard, Action.MOVE_LEFT);
        int after = t.getXPos();
        assertEquals(before - 1, after);
    }

    @Test
    void testTetriminoRight() {
        Tetrimino t = gameBoard.getCurrentTetrimino();
        int before = t.getXPos();
        t.updateTetrimino(gameBoard, Action.MOVE_RIGHT);
        int after = t.getXPos();
        assertEquals(before + 1, after);
    }

    @Test
    void testTetriminoDown() {
        Tetrimino t = gameBoard.getCurrentTetrimino();
        int before = t.getYPos();
        t.updateTetrimino(gameBoard, Action.MOVE_DOWN);
        int after = t.getYPos();
        assertEquals(before + 1, after);
    }

    @Test
    void testTetriminoPause() {
        Tetrimino t = gameBoard.getCurrentTetrimino();
        int before = t.getYPos();
        t.updateTetrimino(gameBoard, Action.PAUSE);
        int after = t.getYPos();
        assertEquals(before, after);
    }

    @Test
    void testCollisionWithBoardBottomBoundary() {
        Tetrimino t = gameBoard.getCurrentTetrimino();
        t.setXPos(0);
        t.setYPos(0);

        assertFalse(gameBoard.checkCollision(0, 1), "Tetrimino should not collide when moving down while it is in bounds");
        t.setYPos(gameBoard.getHeight() - t.getHeight());
        assertTrue(gameBoard.checkCollision(0, gameBoard.getHeight()), "Tetrimino should collide when moving down out of bounds");
    }


}
