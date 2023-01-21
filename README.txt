=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: prajc
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. File I/O: I use the file i/o to save and load the game. This is the appropriate feature because minesweeper has to
  interact with the txt file through writing properties of each cell onto the seperate txt and then reading out the txt
  to load the saved game back onto the grid. Using file i/o is probably the easiest way to perform this feature.

  2. JUnit Test: I use JUnit to test for all the different methods in the Minesweeper class. This is appropriate because
  the actual functionality is separate from the GUI, so I don't ever have to refer to my GUI to ensure the functionality
  works.

  3. Recursion: I used recursion in the uncoverCell and clearSurroundings method in the MineSweeper class. These methods
  are used to perform what happens when you click on a non-mine cell. If the number of adjacent bombs is zero,
  the method would recurse through and open the adjacent cells and check if they have adjacent bombs. The recursion
  stops where all the cells have an adjacent mine. Recursion is the appropriate feature for revealing cells because if
  there are no adjacent bombs, it will call the uncoverCell method again but for the adjacent cells. This is crucial to
  make sure all the nearby cells with no adjacent bombs are revealed.

  4. 2D Array: I am using a 2D Array to make the grids of Minesweeper. This is an appropriate feature because it is the
  easiest way to call coordinates through the x and y axis and also because the board has a fixed 2-D array width and
  length. More particularly, I created a Cell class, denoting the many properties of each cell
  on the 2D array. This includes the number of flags, number of adjacent mines, whether it has a mine,
  whether it is flagged, etc. So, the 2D array is of type Cell[][].

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Cell.java - this class is responsible for the properties of a cell. This is so I can use the different combinations of
  properties of a cell to determine in the Minesweeper class on how the player can interact with it when uncovering a
  cell, flagging a cell, etc.
  Some properties include:
  -creating a cell object that makes up the 2D array board
  -denotes if the cell has a mine, has a flag, is covered, it's status, etc.
  -denotes the number of adjacent bombs.


  GameBoard.java - this class is responsible for all the different features of the game displayed on the board.
  This includes all the event listeners of the board and the view of the gui.
  Some properties include:
  -creating initial game board onto GUI
  -repainting the board when updated
  -saving, loading, and resetting the game
  -update status of number of flags and mines when right-clicked.
  -etc.


  MineSweeper.java - this class is used for the main function of the game. Particularly, it includes methods in
  uncovering cells, flagging cells, checking for winner, and drawing the initial bombs and uncovered cells onto the
  board. This is the primary class of the whole game because it describes the functionality of Minesweeper. I also


  RunMineSweeper.java - This class is responsible for creating a majority of the GUI. This includes implementing the
  different buttons and their features, the game board, and displaying the number of mines and flags remaining.

  Game.java - This is a simple class to run the game.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  -one of the most significant running blocks is implementing the recursion of the game. It took me a long time to debug
  as I didn't have the best grasp of recursion in java.
  -another challenge is implementing the GUI. It was difficult to display all the different features on to one screen.
  This is my first time using Swing, and I had to review many of the different properties I implemented on my GUI.
  -Reading txt files was also challenging. For instance, I had to parse the different properties seperated by the
  commas in the txt and convert each of them into the different cells. This took a lot of planning and forethought
  because although TwitterBot was a similar task, I have never created the whole functionality myself.



- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  -I think my design is pretty good. One thing that I found really helpful is having a separate Cell class to dedicate
  the different parts of my game functionality.
  -The GUI is also separate from the actual functionality, which made the game easier to implement, as I didn't have to
  add more functionalities later on.
  -I think my private state is well encapsulated because I have written many getters and setters for each class.
  -If I have the chance to refactor my code, I would try to separate to as many small files as possible instead a few
  long ones. I currently have 5 classes, and I think I can better organize it through creating more classes with fewer
  functionalities in each. I struggled to call the different functions because I don't know exactly where they are in my
  project, and there were some repeated functions in different classes.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
    - https://minesweepergame.com/strategy/how-to-play-minesweeper.php
    - https://minesweeperonline.com/
    - To make the numberOfAdjacentMines and uncoverCell method, I was inspired by the methods
    written in the midterm practice exam 18fa.
    - https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
    - https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
    - https://docs.oracle.com/javase/8/docs/api/java/io/BufferedWriter.html
