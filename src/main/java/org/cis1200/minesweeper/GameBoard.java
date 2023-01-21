package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private final MineSweeper ms; // model for the game
    private final JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;


    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ms = new MineSweeper(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                // updates the model given the coordinates of the mouseclick
                if (e.getButton() == MouseEvent.BUTTON1) {
                    ms.uncoverCell(
                            p.x / (BOARD_WIDTH / MineSweeper.NUM_WIDTH),
                            p.y / (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT)
                    );
                    updateStatus(); // updates the status JLabel
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ms.changeFlags(
                            p.x / (BOARD_WIDTH / MineSweeper.NUM_WIDTH),
                            p.y / (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT)
                    );
                    updateStatus();
                }
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ms.reset();
        status.setText("Number of Remaining Flags: " + (10 - ms.currFlags()));
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void saveGame() {
        try {
            String filePath = "minesweeper_save.txt";
            BufferedWriter r = new BufferedWriter(new FileWriter(filePath, false));

            String sf = "";

            for (int i = 0; i < MineSweeper.NUM_WIDTH; i++) {
                for (int j = 0; j < MineSweeper.NUM_HEIGHT; j++) {
                    sf = sf + ms.getBoard()[j][i].getIsMine() + ","
                            + ms.getBoard()[j][i].getIsFlagged() + "," +
                            ms.getBoard()[j][i].getIsCovered() + ","
                            + ms.getBoard()[j][i].getStatus() + "," +
                            ms.getBoard()[j][i].getNumAdjBombs() + "," + ms.getGameOver() + ",\n";
                }
            }
            r.write(sf);
            r.flush();
            r.close();
        } catch (IOException e) {
            System.out.print("Can't Save File");
        }
    }

    public void loadGame() {
        try {
            try {
                String filePath = "minesweeper_save.txt";
                BufferedReader r = new BufferedReader(new FileReader(filePath));
                for (int i = 0; i < 100; i++) {
                    String[] s = r.readLine().split(",");
                    boolean mine = Boolean.parseBoolean(s[0]);
                    boolean flag = Boolean.parseBoolean(s[1]);
                    boolean cover = Boolean.parseBoolean(s[2]);
                    String symbol = s[3];
                    int adjBombs = Integer.parseInt(s[4]);
                    Cell newCell = new Cell(i / 10, i % 10, mine, flag);
                    newCell.setIsCovered(cover);
                    newCell.setStatus(symbol);
                    newCell.setNumAdjBombs(adjBombs);
                    ms.getBoard()[i % 10][i / 10] = newCell;
                    boolean gameOver = Boolean.parseBoolean(s[5]);
                    ms.setGameOver(gameOver);
                    updateStatus();

                }
                repaint();
            } catch (IOException e) {
                System.out.println("Can't Load File");
            }
        } catch (Exception e) {
            System.out.println("File Doesn't Exist");
        }
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        int winner = ms.checkWinner();
        if (winner == 1) {
            status.setText("You Won!");
            // stop game
        } else if (winner == 0) { // if no winner
            // add the number of adjacent bombs on where I clicked.
            status.setText("Number of Remaining Flags:" + (10 - ms.currFlags()));
        } else { // if loser
            status.setText("You Lost! Press Reset to Try Again");

        }
        // if they win, see a text that you win. and game is stopped. else keep playing
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        for (int i = 0; i < MineSweeper.NUM_WIDTH; i++) {
            for (int j = 0; j < MineSweeper.NUM_HEIGHT; j++) {
                g.drawLine(
                        0, (i * (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT)),
                        400, (i * (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT))
                );
                g.drawLine(
                        (i * (BOARD_WIDTH / MineSweeper.NUM_WIDTH)),
                        0, (i * (BOARD_WIDTH / MineSweeper.NUM_WIDTH)), 400
                );
            }
        }
        // // Color squares depending on whether it's flagged, contains bomb, etc.
        for (int i = 0; i < MineSweeper.NUM_WIDTH; i++) {
            for (int j = 0; j < MineSweeper.NUM_HEIGHT; j++) {
                Cell coords = ms.getBoard()[j][i];
                String symbol = coords.getStatus();
                if (symbol.equals("F")) {
                    g.setColor(Color.RED);
                    g.fillRect(
                            (BOARD_WIDTH / MineSweeper.NUM_WIDTH) * i + 5,
                            (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT) * j + 5,
                            (BOARD_WIDTH / MineSweeper.NUM_WIDTH) - 10,
                            (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT) - 10
                    );
                } else if (symbol.equals("*")) {
                    g.setColor(Color.GRAY);
                    g.fillRect(
                            (BOARD_WIDTH / MineSweeper.NUM_WIDTH) * i + 5,
                            (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT) * j + 5,
                            (BOARD_WIDTH / MineSweeper.NUM_WIDTH) - 10,
                            (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT) - 10
                    );
                } else if (symbol.equals("B")) {
                    g.setColor(Color.BLACK);
                    g.fillRect(
                            (BOARD_WIDTH / MineSweeper.NUM_WIDTH) * i + 5,
                            (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT) * j + 5,
                            (BOARD_WIDTH / MineSweeper.NUM_WIDTH) - 10,
                            (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT) - 10
                    );
                } else {
                    g.setColor(Color.GRAY);
                    g.drawString(
                            String.valueOf(Integer.parseInt(symbol)),
                            (BOARD_WIDTH / MineSweeper.NUM_WIDTH) * (i) + 15,
                            (BOARD_HEIGHT / MineSweeper.NUM_HEIGHT) * (j + 1) - 15
                    );
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
