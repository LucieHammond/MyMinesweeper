package MyMinesweepe.Core;

/**
 * @author Lucie
 *
 */
public class Square {
	
	private static enum State {Normal,Flagged,Open}
	
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
	
	public void changeFlag(){
		if (state == State.Normal){
			state = State.Flagged;
			nb_flags++;
		}
		else if (state == State.Flagged){
			state = State.Normal;
			nb_flags--;
		}
		else{
			System.out.println("Error : you shouldn't be able to access this method is square is open");
		}
	}
	
	public boolean open(){
		if (state == State.Open){return false;}
		
		state = State.Open;
		remainingSquares--;
		return (adjacentMines==0);
	}
	
}