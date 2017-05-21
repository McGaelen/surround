package package1;

/******************************************************************
 * All the possible states the game can be in.
 *****************************************************************/
public enum GameStatus {
	/** Any player has won the game. */
	Win,
	/** The game is unwinnable. */
	Cats,
	/** The game is being played. */
	InProgress
}
