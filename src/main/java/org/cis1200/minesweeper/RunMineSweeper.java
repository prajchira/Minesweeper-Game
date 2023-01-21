package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * In a Model-View-Controller framework, RunMineSweeper initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a MineSweeper object to serve as the game's model.
 */
public class RunMineSweeper implements Runnable {
    public void run() {

        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Time");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Save game button
        final JButton saveGame = new JButton("Save");
        saveGame.addActionListener(e -> board.saveGame());
        control_panel.add(saveGame);

        // Save game button
        final JButton loadGame = new JButton("Load");
        loadGame.addActionListener(e -> board.loadGame());
        control_panel.add(loadGame);

        // Instruction button
        final JButton instruction = new JButton("Instructions");
        instruction.addActionListener(e -> {
            String rule = "Instructions: \n"
                    + "The goal of this game is to uncover all the cells that don't contain mines. "
                    +
                    "You lose the game if you set \n " +
                    "the mines off, and you won't be allowed to uncover or " +
                    "\n flag any more cells until you reset the game. \n \n" +

                    "Through left clicking, you will uncover the cell. If you uncover \n" +
                    "a mine, you lose and that cell will turn black. If the cell is not a mine, \n"
                    +
                    "it will tell you the total number of mines in the eight adjacent cells. \n" +
                    "If the cell you click on has zero adjacent mines, then \n" +
                    "the game would recurse the neighboring cells until there's \n" +
                    "an adjacent bomb at cells in all directions" +
                    "\n of the cell that is clicked. \n\n" +
                    "Through right clicking, you can flag a cell, which will " +
                    "turn the grey tile into a red tile to \n" +
                    "mark where you think the mine is. Note that you can't " +
                    "uncover a flagged cell. " +
                    "\n" +
                    "To undo the flagging,"
                    + " just right click the flagged cell again. Note that you can flag \n" +
                    "only a total of 10 different cells at one time \n \n" +
                    "You can replay the game by pressing reset, \n which will randomize " +
                    "the mines " +
                    "and restart the grid. You can also save a game and load \n " +
                    "that saved game later on, despite if you "
                    +
                    "have totally closed the game. ";
            JOptionPane.showMessageDialog(
                    control_panel, rule, "Instructions: Minesweeper",
                    JOptionPane.PLAIN_MESSAGE
            );
        });
        control_panel.add(instruction);
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}