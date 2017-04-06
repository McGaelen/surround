
package package1;
 
import javax.swing.*;
 
public class Surround {
    /** the container for everything */
    private static JFrame frame;
     
    /** to hold an options menu */
    private JMenuBar menuBar;
    /** to hold a quit and new game item */
    private JMenu optionsMenu;
    /** quits the game */
    private JMenuItem quitItem;
    /** triggers a new game */
    private JMenuItem newGameItem;
     
    /** the main panel*/
    private static SurroundPanel panel;
     
    /******************************************************************
     * Constructor adds the GUI components into the frame and displays
     * the frame.
     *****************************************************************/
    public Surround() {
        frame = new JFrame("Surround");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        menuBar = new JMenuBar();
        optionsMenu = new JMenu("Options");
        quitItem = new JMenuItem("Quit");
        newGameItem = new JMenuItem("New Game");
         
        panel = new SurroundPanel(newGameItem, quitItem);
         
        optionsMenu.add(newGameItem);
        optionsMenu.add(quitItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);
         
        frame.getContentPane().add(panel);
        frame.setSize((panel.getBdSize() * 50),
                ((panel.getBdSize()) * 40) + 100);
        frame.setVisible(true);
    }
     
    /******************************************************************
     * Main method creates an instance of a game of Surround.
     *****************************************************************/
    public static void main(String[] args) {
        Surround GUI = new Surround();
    }
 
    /******************************************************************
     * Resizes the frame as needed to try and give all elements in the
     * GUI an acceptable amount of space.
     *****************************************************************/
    public static void resize() {
        frame.setSize((panel.getBdSize() * 50),
                ((panel.getBdSize() * 40) + 100));
    }
}
