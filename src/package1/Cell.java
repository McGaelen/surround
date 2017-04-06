
package package1;
 
public class Cell {
     
    /** the player that owns this cell */
    private int playerNum;
    /** remembers if this cell is a landmine */
    private boolean landmine;
    /** remembers if this cell exploded or not*/
    private boolean exploded;
     
    /******************************************************************
     * Constructor with no parameters is called when it is to become a
     * landmine.
     *****************************************************************/
    public Cell() {
        landmine = true;
        exploded = false;
    }
     
    /******************************************************************
     * Constructor to assign this Cell to the player's number.
     *
     * @param playerNum the number of the player to assign this Cell to.
     *****************************************************************/
    public Cell(int playerNum) {
        landmine = false;
        this.playerNum = playerNum;
    }
     
    /******************************************************************
     * Sets the player number this Cell is assigned to.
     *
     * @param num the player number to be assigned to.
     *****************************************************************/
    public void setPlayerNumber(int num) {
        playerNum = num;
    }
     
    /******************************************************************
     * Gets the number this Cell is assigned to.
     *
     *  @return playerNum this Cell's number.
     *****************************************************************/
    public int getNum() {
        return playerNum;
    }
     
    /******************************************************************
     * Show if this cell is a landmine.
     *
     * @return landmine true if this Cell is a landmine, false if not.
     *****************************************************************/
    public boolean isLandmine() {
        return landmine;
    }
     
    /******************************************************************
     * Set exploded to true.
     *****************************************************************/
    public void explode() {
        exploded = true;
    }
     
    /******************************************************************
     * See if this Cell has already previously exploded.
     *
     * @return exploded true if this Cell is used, false if not.
     *****************************************************************/
    public boolean used() {
        return exploded;
    }
}
