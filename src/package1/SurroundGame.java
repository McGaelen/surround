package package1;

import java.util.*;

public class SurroundGame {

	/** keeps track of all squares */
	private Cell[][] cells;
	/** size of the game board */
	private int bdSize;
	/** player index corresponding to the playerList array */
	private int currentPlayerIndex = 0;
	/** total players currently playing */
	private int totalPlayers;
	/** holds a list of all players */
	private int[] playerList;
	
	/******************************************************************
	 * Constructor creates a game "engine", creates a list of players,
	 * and creates the game's board.
	 * 
	 * @param bdSize the size of the game board.
	 * @param totalPlayers the amount of players in the game.
	 * @param startingPlayer the player who will go first.
	 *****************************************************************/
	public SurroundGame(int bdSize, int totalPlayers, 
			int startingPlayer) {
		
		this.bdSize = bdSize;
		this.currentPlayerIndex = startingPlayer - 1;
		this.totalPlayers = totalPlayers;
		
		playerList = new int[this.totalPlayers];
		for (int i = 0; i < this.totalPlayers; i++) {
			playerList[i] = i + 1;
		}
		
		cells = new Cell[this.bdSize][this.bdSize];
	}
	
	/******************************************************************
	 * Detects if the current Cell at row, col is selectable.
	 * 
	 * @param row the row the Cell is in.
	 * @param col the column the Cell is in.
	 * @return true if the cell is null, false if the cell is not null 
	 * (already claimed Cell).
	 *****************************************************************/
	public boolean select(int row, int col) {
		if (cells[row][col] == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/******************************************************************
	 * Show the number of the current player.
	 * 
	 * @return The element at the current player's index.
	 *****************************************************************/
	public int getCurrentPlayer() {
		return playerList[currentPlayerIndex];
	}
	
	/******************************************************************
	 * Show the total number of players.
	 * 
	 * @return the number of players.
	 *****************************************************************/
	public int getTotalPlayers() {
		return totalPlayers;
	}
	
	/******************************************************************
	 * Gets the Cell at the given coordinates.
	 * 
	 * @param row the row the Cell is in.
	 * @param col the column the Cell is in.
	 * @return the Cell at the coordinates.
	 *****************************************************************/
	public Cell getCell(int row, int col) {
		return cells[row][col];
	}
	
	/******************************************************************
	 * Sets the Cell at the given coordinates to the number of the
	 * current player, claiming that Cell.
	 * @param row the row the Cell is in.
	 * @param col the column the Cell is in.
	 *****************************************************************/
	public void setCell(int row, int col) {
		cells[row][col] = new Cell(playerList[currentPlayerIndex]);
	}
	
	/******************************************************************
	 * Advances to the next player. Loops back to zero and skips 
	 * eliminated players as needed.
	 *****************************************************************/
	public void nextPlayer() {
		currentPlayerIndex++;
		
		if (currentPlayerIndex >= playerList.length) {
			currentPlayerIndex = 0;
		}
		
		while (playerList[currentPlayerIndex] == 0) {
			currentPlayerIndex++;
			
			if (currentPlayerIndex >= playerList.length) {
				currentPlayerIndex = 0;
			}
		}
	}
	
	/******************************************************************
	 * Eliminates the given player by setting their number to 0.
	 * 
	 * @param player the player to be eliminated. 
	 *****************************************************************/
	public void eliminate(int player) {
		for (int i = 0; i < playerList.length; i++) {
			if (playerList[i] == player) {
				playerList[i] = 0;
			}
		}
		
		for (int row = 0; row < bdSize; row++) {
			for (int col = 0; col < bdSize; col++) {
				if (cells[row][col] != null && 
						cells[row][col].getNum() == player) {
					cells[row][col].setPlayerNumber(0);
				}
			}
		}
		
	}
	
	
	/******************************************************************
	 * Resets the ownership of all Cells, including landmines.
	 *****************************************************************/
	public void reset() {
		cells = new Cell[bdSize][bdSize];
	}
	
	/******************************************************************
	 * Detects if any owned Cell on the board has been surrounded by 
	 * any other owned Cells.  Allows for the game board to wrap around.
	 * 
	 * @return The number of the player surrounded, or -1 if there is
	 * no surrounded player.
	 *****************************************************************/
	public int isSurrounded() {
		boolean top;
		boolean bottom;
		boolean left;
		boolean right;
		int cellOwner;
		
		for (int row = 0; row < bdSize; row++){
			for (int col = 0; col < bdSize; col++) {
				
				if (cells[row][col] != null && 
						cells[row][col].getNum() != 0) {
					
					top = false;
					bottom = false;
					left = false;
					right = false;
					cellOwner = cells[row][col].getNum();

					// Test for Top Spot
					if ((row-1) < 0){
						if (cells[bdSize-1][col] != null && 
								cells[bdSize-1][col].getNum() != cellOwner) {
							top = true;
						}
					} else {
						if (cells[row-1][col] != null &&
								cells[row-1][col].getNum() != cellOwner) {
							top = true;
						}
					}

					// Test for Bottom Spot
					if ((row+1) > (bdSize-1)) {
						if (cells[0][col] != null &&
								cells[0][col].getNum() != cellOwner) {
							bottom = true;
						}
					} else {
						if (cells[row+1][col] != null &&
								cells[row+1][col].getNum() != cellOwner) {
							bottom = true;
						}
					}

					// Test for Left Spot
					if ((col-1) < 0) {
						if (cells[row][bdSize-1] != null &&
								cells[row][bdSize-1].getNum() != cellOwner) {
							left = true;
						}
					} else {
						if (cells[row][col-1] != null &&
								cells[row][col-1].getNum() != cellOwner) {
							left = true;
						}
					}

					// Test for Right Spot
					if ((col+1) > (bdSize-1)) {
						if (cells[row][0] != null &&
								cells[row][0].getNum() != cellOwner) {
							right = true;
						}
					} else {
						if (cells[row][col+1] != null &&
								cells[row][col+1].getNum() != cellOwner) {
							right = true;
						}
					}

					// Test for Surround
					if (top == true && bottom == true && 
							left == true && right == true) {
						return cellOwner;
					}
				}
			}
		}
		
		return -1;
	}
	
	
	/******************************************************************
	 * Determines if there is one player left standing to be declared
	 * the winner of the game.
	 * 
	 * @return The number of the winning player, or -1 if there's no 
	 * win.
	 *****************************************************************/
	public int isWinner() {
		int playerCount = 0;
		
		for (int i : playerList) {
			if (i != 0) {
				playerCount++;
			}
		}
		
		if (playerCount == 1) {
			for (int i : playerList) {
				if (i != 0) {
					return i;
				}
			}
		}
		
		return -1;
	}

	
	/******************************************************************
	 * Detects if the game is Cats, or is unbeatable/unplayable.
	 * 
	 * @return False if any Cell is still null (unclaimed), true if all
	 * cells have been claimed
	 *****************************************************************/
	public boolean cats() {
		for (int row = 0; row < bdSize; row++){
			for (int col = 0; col < bdSize; col++) {
				
				if (cells[row][col] == null){
					return false;
				}
			}
		}
		
		return true;
	}
	
	/******************************************************************
	 * If called, turns the board into a minefield by randomly 
	 * assigning Cells as landmines.
	 *****************************************************************/
	public void minefield() {
		Random r = new Random();
		
		for (int row = 0; row < bdSize; row++){
			for (int col = 0; col < bdSize; col++) {
				
				if (r.nextInt(5) == 1) {
					cells[row][col] = new Cell();
				}
			}
		}
	}
	
	/******************************************************************
	 * Determines if the Cell at the given coordinates is a landmine
	 * or not.
	 * 
	 * @param row the row the Cell is in.
	 * @param col the column the Cell is in.
	 * @return the result of calling the Cell's isLandmin() method,
	 * false if Cell null.
	 *****************************************************************/
	public boolean isLandMine(int row, int col) {
		if (cells[row][col] != null) {
			return cells[row][col].isLandmine();
		} else {
			return false;
		}
	}
	
	/******************************************************************
	 * Sets the Cell at the given coordinates to exploded.
	 * 
	 * @param row the row the Cell is in.
	 * @param col the column the Cell is in.
	 *****************************************************************/
	public void explode(int row, int col) {
		cells[row][col].explode();
	}
}
