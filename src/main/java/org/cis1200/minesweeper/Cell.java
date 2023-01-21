package org.cis1200.minesweeper;

public class Cell {
    // methods: is bomb bool
    // methods: bombnum: 1,9.
    // methods: iscovered
    // methods: flagged
    //
    // getters and setters

    // the Cells Position
    private int xPos;
    private int yPos;

    // properties
    private final int tileDim = 20;
    private boolean isMine;
    private int numAdjBombs;
    private boolean isFlagged;
    private boolean isCovered;

    // private Cell[][] surroundings;
    private String status;

    public Cell(int x, int y, boolean m, boolean f) {
        isMine = m;
        numAdjBombs = 0;
        isFlagged = f;
        isCovered = true;
        xPos = x;
        yPos = y;
        if (isCovered) {
            status = "*";
        } else if (isFlagged) {
            status = "F";
        } else {
            status = "" + getNumAdjBombs();
        }

        // surroundings = new Cell[][];

    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean getIsMine() {
        return isMine;
    }

    public boolean setIsMine(boolean b) {
        return isMine = b;
    }

    public int getNumAdjBombs() {
        return numAdjBombs;
    }

    public int setNumAdjBombs(int a) {
        return numAdjBombs = a;
    }

    public boolean getIsFlagged() {
        return isFlagged;
    }

    public boolean setIsFlagged(boolean b) {
        return isFlagged = b;
    }

    public boolean getIsCovered() {
        return isCovered;
    }

    public boolean setIsCovered(boolean b) {
        return isCovered = b;
    }

    public String getStatus() {
        return status;
    }

    public String setStatus(String s) {
        return status = s;
    }

}
