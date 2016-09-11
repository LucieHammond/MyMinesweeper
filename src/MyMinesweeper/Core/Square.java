package MyMinesweeper.Core;

/**
 * This class represents a square of the Minesweeper grid
 * @author Lucie
 *
 */
public class Square {
	
	/**
	 * Enumeration of all different possible states of a square (normal, flagged and open)
	 * @author Lucie
	 *
	 */
	public static enum State {Normal,Flagged,Open}
	
	/**
	 * State of the square (Normal, Flagged or Open)
	 */
	private State state;
	
	/**
	 * True if square is a mine, False if not
	 */
	private boolean mine;
	
	/**
	 * Number of mines adjacent to the square (integer between 0 and 8)
	 */
	private int adjacentMines;
	
	/**
	 * Static variable representing the number of flagged squares in grid
	 */
	private static int nb_flags;
	
	/**
	 * Static variable representing the number of squares not opened yet
	 */
	private static int remainingSquares;
	
	// Constructor
	public Square(){
		super();
		this.mine = false;
		this.state = State.Normal;
		this.adjacentMines = 0;
	}

	// Useful getters and setters
	public void addAdjacentMine(){
		adjacentMines++;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public static int getRemainingSquares() {
		return remainingSquares;
	}

	public static void setRemainingSquares(int remainingSquares) {
		Square.remainingSquares = remainingSquares;
	}

	public State getState() {
		return state;
	}

	public int getAdjacentMines() {
		return adjacentMines;
	}

	public static int getNb_flags() {
		return nb_flags;
	}

	public static void setNb_flags(int nb_flags) {
		Square.nb_flags = nb_flags;
	}

	/**
	 * Change square state from Normal to Flagged or from Flagged to Normal
	 * The static variable nb_flags is updated.
	 * @return true if a flag was added and false if a flag was removed
	 */
	public boolean changeFlag(){
		if (state == State.Normal){
			state = State.Flagged;
			nb_flags++;
			return true;
		}
		else{
			state = State.Normal;
			nb_flags--;
			return false;
		}
	}
	
	/**
	 * Try to open the square and change its state to Open if relevant (not already opened and 
	 * not flagged)
	 * @return true if square has been opened : this mean GUI must be updated
	 * false if no change has been made.
	 */ 
	public boolean open(){
		if (state == State.Open){return false;}
		else if (state == State.Flagged){
			System.out.println("You can't open this square because it has been flagged");
			return false;
		}
		
		state = State.Open;
		remainingSquares--;
		return true;
	}
	
}
