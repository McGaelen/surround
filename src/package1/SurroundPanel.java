
package package1;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class SurroundPanel extends JPanel{
     
    /** a 2D array of buttons to represent the game board */
    private JButton[][] board;
    /** the game's "engine" */
    private SurroundGame game;
    /** enum to represent the game's current status */
    private GameStatus status;
    /** side length of the game board */
    private static int bdSize = 10;
    /** new game choice in menu bar */
    private JMenuItem newGameItem;
    /** quit choice in menu bar */
    private JMenuItem quitItem;
     
    /** panel to hold all labels*/
    private JPanel labelPanel;
    /** panel to hold labels that track player wins */
    private JPanel winsPanel;
    /** panel to hold all buttons */
    private JPanel buttonPanel;
     
    /** displays who's turn it is*/
    private JLabel turnLabel;
    /** displays each player's win count*/
    private JLabel[] playerWins;
     
    /** the action listener*/
    private ButtonListener listener;
     
    /** keeps track of each player's win count*/
    private int[] winsTracker;
     
    /******************************************************************
     * Constructor sets up the GUI, buttonlistener, and winsTracker.
     * Calls appropriate private methods.
     *
     * @param newItem the object reference to the newGame menu item
     * @param quitItem the object reference to the quit menu item
     *****************************************************************/
    public SurroundPanel(JMenuItem newItem, JMenuItem quitItem) {
        listener = new ButtonListener();
        setLayout(new BorderLayout());
        turnLabel = new JLabel();
         
        winsTracker = new int[10];
         
        // Buttons
        setUpButtonsAndGame();
         
         
        // Menu Items
        this.newGameItem = newItem;
        this.quitItem = quitItem;
         
        this.newGameItem.addActionListener(listener);
        this.quitItem.addActionListener(listener);
         
         
        // Labels
        setUpLabels();
 
         
        status = GameStatus.InProgress;
    }
         
    /******************************************************************
     * Private helper method asks the user for required information
     * and instantiates the game "engine" and game board.
     *****************************************************************/
    private void setUpButtonsAndGame() {
         
        askForBdSize();
        int numPlayers = askForNumberOfPlayers();
        int startPlayer = askForStartingPlayer(numPlayers);
         
        board = new JButton[bdSize][bdSize];
        game = new SurroundGame(bdSize, numPlayers, startPlayer);
        if (JOptionPane.showConfirmDialog(null,
                "Play in MineField mode?") == JOptionPane.YES_OPTION) {
            game.minefield();
        }
     
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(bdSize, bdSize));
         
        for (int row = 0; row < bdSize; row++) {
            for (int col = 0; col < bdSize; col++) {
                board[row][col] = new JButton(" ");
                board[row][col].addActionListener(listener);
                 
                buttonPanel.add(board[row][col]);
            }
        }
         
        add(buttonPanel, BorderLayout.SOUTH);
         
        turnLabel.setText
            ("Player " + game.getCurrentPlayer() + "'s turn.");
    }
     
    /******************************************************************
     * Private helper method sets up all labels and adds them to the
     * labelPanel.
     *****************************************************************/
    private void setUpLabels() {
        labelPanel = new JPanel();
        winsPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());
         
        playerWins = new JLabel[game.getTotalPlayers()];
        for (int i = 0; i < playerWins.length; i++) {
            playerWins[i] = new JLabel
                    ("P" + (i+1) + " Wins: " + winsTracker[i] + "  ");
            winsPanel.add(playerWins[i]);
        }
         
        labelPanel.add(turnLabel, BorderLayout.CENTER);
        labelPanel.add(winsPanel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.NORTH);
    }
     
    /******************************************************************
     * Private helper method re-creates a game by removing all panels
     * and again calling the appropriate set-up helper methods.
     *****************************************************************/
    private void newGame() {
        labelPanel.remove(turnLabel);
        labelPanel.remove(winsPanel);
        remove(buttonPanel);
        remove(labelPanel);
         
         
        setUpButtonsAndGame();
        setUpLabels();
         
        revalidate();
        repaint();
        Surround.resize();
         
        status = GameStatus.InProgress;
    }
     
    /******************************************************************
     * Private helper method asks the user for input with a dialog box
     * and has input validation. Sets bdSize to the entered value.
     * Allows a range of 3 - 20.
     *****************************************************************/
    private void askForBdSize() {
        boolean validSize = false;
        while (validSize == false) {
            try {
                String strBdSize = JOptionPane.showInputDialog
                        (null, "Enter in the size of the board: ");
                bdSize = Integer.parseInt(strBdSize);
 
                while (bdSize < 3 || bdSize > 20) {
                    JOptionPane.showMessageDialog(null, "The size must "
                            + "be greater than 3 or less than 20.");
                    strBdSize = JOptionPane.showInputDialog (null,
                            "Enter in the size of the board: ");
                    bdSize = Integer.parseInt(strBdSize);
                }
                 
                validSize = true;
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "You may only enter a number.");
            }
        }
    }
     
    /******************************************************************
     * private helper method gets the number of players, similar to
     * askForBdSize(), but allows a range of 2 - 10.
     *
     * @return num the number of players given by the user.
     *****************************************************************/
    private int askForNumberOfPlayers() {
        int num = 0;
        while (num < 2 || num > 10) {
            try {
                String strNum = JOptionPane.showInputDialog
                        ("How many players are there?");
                num = Integer.parseInt(strNum);
                 
                if (num < 2 || num > 10)
                    JOptionPane.showMessageDialog(null,
                            "The number must be between 2 and 10.");
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,
                        "You may only enter a number.");
            }
        }
        return num;
    }
     
    /******************************************************************
     * Private helper method obtains the player that will start the
     * game, and won't let the range exceed the total number of players.
     *
     * @param total the total amount of players, to restrict input
     * @return num the number of the player to start.
     *****************************************************************/
    private int askForStartingPlayer(int total) {
        int num = 0;
        while (num < 1 || num > total) {
            try {
                String strNum = JOptionPane.showInputDialog
                        ("Which player will start?");
                num = Integer.parseInt(strNum);
                 
                if (num < 1 || num > total)
                    JOptionPane.showMessageDialog(null, "The number "
                            + "must be between 1 and " + total + ".");
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,
                        "You may only enter a number.");
            }
        }
        return num;
    }
     
    /******************************************************************
     * Gets the size of the board.
     *
     * @return bdSize the board size.
     *****************************************************************/
    public int getBdSize() {
        return bdSize;
    }
     
    /******************************************************************
     * Private helper method updates all the button's icons when called.
     *****************************************************************/
    private void updateIcons() {
        for (int row = 0; row < bdSize; row++) {
            for (int col = 0; col < bdSize; col++) {
                 
                Cell c = game.getCell(row, col);
                 
                // Set the icon to the cell owner if the cell is not
                // null and is not a landmine
                if (c != null && c.isLandmine() == false) {
                    for (int i = 0; i <= 10; i++) {
                        if (c.getNum() == i) {
                            board[row][col].setIcon(new ImageIcon
                                    ("number" + i + ".png"));
                        }
                    }
                }
                // Reveal position of exploded landmines
                else if (c != null && c.used()) {
                    board[row][col].setIcon(new ImageIcon
                            ("explosion.png"));
                }
                // Otherwise nothing shows.
                else {
                    board[row][col].setText("");
                }
            }
        }
    }
     
 
     
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            updateIcons();
             
            // Menu Items
            if (e.getSource() == quitItem) {
                System.exit(1);
            }
            if (e.getSource() == newGameItem) {
                newGame();
            }
             
            // Buttons
            for (int row = 0; row < bdSize; row++) {
                for (int col = 0; col < bdSize; col++) {
 
                    if (e.getSource() == board[row][col]) {
                         
                        // If the selected button is a landmine
                        if (game.isLandMine(row, col)) {
                            // Tell the user, set cell to exploded,
                            // and eliminate them.
                            JOptionPane.showMessageDialog(null,
                                    "Player " + game.getCurrentPlayer()
                                    + " has exploded!");
                            game.explode(row, col);
                            game.eliminate(game.getCurrentPlayer());
                             
                            // Determine if there's a winner due to
                            // other exploding players
                            if (game.isWinner() != -1) {
                                JOptionPane.showMessageDialog(null,
                                        "Player " + game.isWinner()
                                        + " has won the game!");
                                turnLabel.setText("Player " +
                                        game.isWinner() + " has won!");
                                winsTracker[game.isWinner()-1]++;
                                status = GameStatus.Win;
                            } else {
                                game.nextPlayer();
                                turnLabel.setText("Player "
                                        + game.getCurrentPlayer()
                                        + "'s turn.");
                            }
                             
                        }
                         
                        // If the selected button is not null and not
                        // a landmine
                        else if (game.select(row, col)) {
                             
                            // Set the cell corresponding to the button
                            game.setCell(row, col);
                             
                            // Eliminate all surrounded players
                            while (game.isSurrounded() != -1) {
                                JOptionPane.showMessageDialog(null,
                                        "Player " + game.isSurrounded()
                                        + " has been eliminated!");
                                game.eliminate(game.isSurrounded());
                            }
                             
                            // Determine if there's a winner or cats,
                            // otherwise continue to the next player
                            if (game.isWinner() != -1) {
                                JOptionPane.showMessageDialog(null,
                                        "Player " + game.isWinner()
                                        + " has won the game!");
                                turnLabel.setText("Player "
                                        + game.getCurrentPlayer()
                                        + " has won!");
                                winsTracker[game.isWinner()-1]++;
                                status = GameStatus.Win;
                            } else if (game.cats()){
                                JOptionPane.showMessageDialog(null,
                                        "The game has ended in cats,"
                                        + " no one wins!");
                                turnLabel.setText("Cats game!");
                                status = GameStatus.Cats;
                            } else {
                                game.nextPlayer();
                                turnLabel.setText("Player "
                                        + game.getCurrentPlayer()
                                        + "'s turn.");
                            }
                             
                        }
                    }
                     
                }
            }
             
            updateIcons();
             
            // Disable the game board if a player has won or cats game
            if (status == GameStatus.Win || status == GameStatus.Cats) {
                for (int row = 0; row < bdSize; row++) {
                    for (int col = 0; col < bdSize; col++) {
                        board[row][col].setEnabled(false);
                    }
                }
            }
             
        }
    }
}
