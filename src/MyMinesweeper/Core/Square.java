package MyMinesweeper.Core;

import MyMinesweeper.GUI.GamePanel;

/**
 * @author Lucie
 *
 */
public class Square {
	
	public static enum State {Normal,Flagged,Open}
	
	private State state;
	
	private boolean mine;
	
	private int adjacentMines;
	
	private static int nb_flags = 0;
	
	private static int remainingSquares = 0;
	
	public Square(){
		super();
		this.mine = false;
		this.state = State.Normal;
		this.adjacentMines = 0;
	}

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

	public void setState(State state) {
		this.state = state;
	}

	public int getAdjacentMines() {
		return adjacentMines;
	}

	public void setAdjacentMines(int adjacentMines) {
		this.adjacentMines = adjacentMines;
	}

	public static int getNb_flags() {
		return nb_flags;
	}

	public static void setNb_flags(int nb_flags) {
		Square.nb_flags = nb_flags;
	}

	// return true if there is flag and false if not
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
	
	// Return true if square has been opened, so GUI must be updated
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
