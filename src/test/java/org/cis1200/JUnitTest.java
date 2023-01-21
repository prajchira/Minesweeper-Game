package org.cis1200;

import org.cis1200.minesweeper.Cell;
import org.cis1200.minesweeper.MineSweeper;
import org.junit.jupiter.api.Test;

import static org.cis1200.minesweeper.MineSweeper.NUM_WIDTH;
import static org.cis1200.minesweeper.MineSweeper.NUM_HEIGHT;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {


    @Test
    public void testCreateBoard() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        int numCell = 0;
        int numCovered = 0;
        int numMines = 0;
        int numFlagged = 0;
        int numStatus = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                numCell++;
                if (board[j][i].getIsCovered()) {
                    numCovered++;
                }
                if (board[j][i].getIsMine()) {
                    numMines++;
                }
                if (board[j][i].getIsFlagged()) {
                    numFlagged++;
                }
                if (board[j][i].getStatus().equals("*")) {
                    numStatus++;
                }
            }
        }
        assertEquals(numCell, 100);
        assertEquals(numCovered, 100);
        assertEquals(numMines, 10);
        assertEquals(numFlagged, 0);
        assertEquals(numStatus, 100);
    }

    @Test
    public void testChangeFlags() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        mc.changeFlags(8, 9);
        mc.changeFlags(4, 2);
        assertTrue(board[9][8].getIsFlagged());
        assertTrue(board[2][4].getIsFlagged());
        mc.changeFlags(4, 2);
        assertTrue(board[9][8].getIsFlagged());
        assertFalse(board[2][4].getIsFlagged());
    }


    @Test
    public void testChangeFlagsEqual10() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        mc.changeFlags(8, 9);
        mc.changeFlags(4, 2);
        mc.changeFlags(1, 9);
        mc.changeFlags(2, 2);
        mc.changeFlags(3, 9);
        mc.changeFlags(5, 2);
        mc.changeFlags(7, 9);
        mc.changeFlags(4, 7);
        mc.changeFlags(8, 8);
        mc.changeFlags(4, 8);

        mc.changeFlags(1, 2);
        assertFalse(board[2][1].getIsFlagged());
        assertEquals(10, mc.currFlags());
    }

    @Test
    public void testChangeFlagsAfterLost() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        board[5][5].setIsMine(true);
        mc.uncoverCell(5, 5);
        mc.changeFlags(4, 8);
        assertFalse(board[8][4].getIsFlagged());
    }

    @Test
    public void testChangeFlagsAtUncoveredCell() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        board[5][5].setIsMine(false);
        mc.uncoverCell(5, 5);
        mc.changeFlags(5, 5);
        assertFalse(board[5][5].getIsFlagged());
    }

    @Test
    public void testUncoverFlaggedCell() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        mc.changeFlags(5, 5);
        mc.uncoverCell(5, 5);
        assertTrue(board[5][5].getIsFlagged());
        assertTrue(board[5][5].getIsCovered());
    }

    @Test
    public void testUncoverMine() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        board[6][5].setIsMine(true);
        mc.uncoverCell(5, 6);
        assertTrue(mc.getGameOver());
    }

    @Test
    public void testUncoverNonMine() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (mc.numberOfAdjacentMines(j,i) == 0 && !board[i][j].getIsMine()) {
                    mc.uncoverCell(j, i);
                    assertFalse(board[i][j].getIsCovered());
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if (y + j >= 0 && x + i >= 0 &&
                                    y + j < NUM_HEIGHT && x + i < NUM_WIDTH) {
                                assertFalse(board[i + x][j + y].getIsCovered());
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testCheckWinnerLose() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        assertEquals(0, mc.checkWinner());
        board[6][5].setIsMine(true);
        mc.uncoverCell(5, 6);
        assertEquals(2, mc.checkWinner());
    }

    @Test
    public void testCheckWinnerWin() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        assertEquals(0, mc.checkWinner());
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!board[j][i].getIsMine()) {
                    mc.uncoverCell(i, j);
                }
            }
        }
        assertEquals(1, mc.checkWinner());
    }

    @Test
    public void testCurrFlags() {
        MineSweeper mc = new MineSweeper();
        Cell[][] board = mc.getBoard();
        board[4][7].setIsFlagged(true);
        board[5][7].setIsFlagged(true);
        assertEquals(2, mc.currFlags());
        board[5][7].setIsFlagged(false);
        assertEquals(1, mc.currFlags());
    }

}
