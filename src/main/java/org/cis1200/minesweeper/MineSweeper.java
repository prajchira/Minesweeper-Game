package org.cis1200.minesweeper;

import java.util.*;

/**
 * This class is a model for MineSweeper.
 *
 * Run this file to see the main method play a game of MineSweeper,
 * visualized with Strings printed to the console.
 *
 *
 */
public class MineSweeper {
    // 1. create board
    // 2. reveal cell

    private Cell[][] board;
    public static final int NUM_WIDTH = 10;
    public static final int NUM_HEIGHT = 10;
    private boolean gameOver;
    private final int numMines = 10;

    /**
     * Constructor sets up game state.
     */
    public MineSweeper() {
        reset();
    }

    // create board methods

    public Cell[][] createBoard() {
        board = new Cell[NUM_WIDTH][NUM_HEIGHT];
        this.gameOver = false;
        // adding the mines
        Random rng = new Random();
        ArrayList<Integer> mineXCoords = new ArrayList<>(
                rng.ints(0, NUM_HEIGHT).distinct().limit(numMines).boxed().toList()
        );
        ArrayList<Integer> mineYCoords = new ArrayList<>(
                rng.ints(0, NUM_WIDTH).distinct().limit(numMines).boxed().toList()
        );
        // initializing the board's properties;
        for (int i = 0; i < NUM_WIDTH; i++) {
            for (int j = 0; j < NUM_HEIGHT; j++) {
                Cell temp = new Cell(j, i, false, false);
                board[j][i] = temp;
            }
        }

        for (int i = 0; i < mineXCoords.size(); i++) {
            int x = mineXCoords.get(i);
            int y = mineYCoords.get(i);
            Cell temp = new Cell(x, y, true, false);
            board[y][x] = temp;
        }

        return board;
        // add random bombs: use math rand and make random bomb.
        // make everything covered.
    }
    // getter

    public Cell[][] getBoard() {
        return board;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public boolean setGameOver(boolean b) {
        return gameOver = b;
    }

    public void changeFlags(int x, int y) {
        Cell coords = board[y][x];
        if (!gameOver) {
            if (coords.getIsCovered()) {
                if (coords.getIsFlagged()) { // if flagged, change it to unflagged
                    coords.setIsFlagged(false);
                    coords.setStatus("*");
                } else if (!coords.getIsFlagged() && currFlags() < 10) { // add flag is there's 9
                                                                         // flags or less
                    coords.setIsFlagged(true);
                    coords.setStatus("F");
                }
            }
        }
    }

    // reveal cell methods

    // edit of its flagged. If flagged, then can't click cell
    public void uncoverCell(int x, int y) {
        if (!gameOver) {
            Cell coords = board[y][x];
            if (!coords.getIsFlagged() && coords.getIsCovered()) {
                if (coords.getIsMine()) {
                    gameOver = true;
                    coords.setStatus("B");
                } else {
                    clearSurroundings(x, y);
                }
            }
        }
    }

    // recursion
    public void clearSurroundings(int x, int y) {
        Cell coords = board[y][x];
        coords.setStatus("" + numberOfAdjacentMines(x, y));
        coords.setIsCovered(false);

        if (numberOfAdjacentMines(x, y) == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (y + j >= 0 && x + i >= 0 && y + j < NUM_WIDTH && x + i < NUM_WIDTH) {
                        if (board[y + j][x + i].getNumAdjBombs() == 0
                                && board[y + j][x + i].getIsCovered() &&
                                !board[y + j][x + i].getIsMine()
                                && !board[y + j][x + i].getIsFlagged()) {
                            uncoverCell(x + i, y + j);
                        }
                    }
                }
            }
        }
    }

    public int numberOfAdjacentMines(int x, int y) {
        int numMines = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if ((x + dx < NUM_WIDTH) && (y + dy < NUM_HEIGHT)
                        && (x + dx >= 0) && (y + dy >= 0)) {
                    if ((dx != 0 || dy != 0) && board[y + dy][x + dx].getIsMine()) {
                        numMines = numMines + 1;
                    }
                }
            }
        }
        return numMines;
    }

    // public int currMines(){
    // int count = 0;
    // for (int i = 0; i < NUM_HEIGHT; i++) {
    // for (int j = 0; j < NUM_WIDTH; j++) {
    // Cell temp = board[i][j];
    // if (temp.getIsMine() && !temp.getIsFlagged()) {
    // count++;
    // }
    // }
    // }
    // return count;
    // }

    public int currFlags() {
        int count = 0;
        for (int i = 0; i < NUM_HEIGHT; i++) {
            for (int j = 0; j < NUM_WIDTH; j++) {
                Cell temp = board[i][j];
                if (temp.getIsFlagged()) {
                    count++;
                }
            }
        }
        return count;
    }

    // game state methods

    /**
     * checkWinner checks whether the game has reached a win condition.
     *
     * @return 0 if nobody has won yet, 1 if player has won, 2 if lost
     */
    public int checkWinner() {
        // Check horizontal win
        if (gameOver) {
            return 2; // lost
        }
        int countMine = 0;
        int countNoMine = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Cell temp = board[i][j];
                if (temp.getIsCovered() && temp.getIsMine()) {
                    countMine++;
                }
                if (!temp.getIsCovered() && !temp.getIsMine()) {
                    countNoMine++;
                }
            }
        }
        if (countMine == numMines && countNoMine == 100 - numMines) {
            return 1; // if number of uncovered cells is 90 and number of covered cells is 10
        } else {
            return 0; // nobody has won
        }
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j].getStatus() + " ");
            }
            System.out.println();
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    // BASICALLY CONSTRUCTOR
    public void reset() {
        createBoard();
        // add random bombs: use math rand and make random bomb.
        // make everything covered.

    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        MineSweeper t = new MineSweeper();

        t.uncoverCell(1, 1);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.uncoverCell(4, 0);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.uncoverCell(1, 2);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.uncoverCell(8, 2);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.uncoverCell(3, 5);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.uncoverCell(2, 2);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.changeFlags(2, 2);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.changeFlags(4, 3);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.changeFlags(5, 5);
        t.printGameState();
        System.out.println();
        System.out.println();

        t.uncoverCell(9, 9);
        t.printGameState();
        System.out.println();
        System.out.println();
    }
}
