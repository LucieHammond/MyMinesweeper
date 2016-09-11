package MyMinesweeper.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import MyMinesweeper.GUI.GamePanel;

/**
 * This class represents a grid of Minesweeper
 * @author Lucie
 *
 */
public class Grid {

	/**
	 * Matrix of squares representing the grid
	 */
	private Square[][] squares;
	
	/**
	 * Number of rows in grid
	 */
	private int rows;
	
	/**
	 * Number of columns in grid
	 */
	private int columns;
	
	/**
	 * Number of mines hidden in grid
	 */
	private int nb_mines;
	
	/**
	 * Link to the panel that displays the game on User Interface.
	 * The Grid object will call some of the GamePanel update functions when necessary.
	 */
	private GamePanel observer;

	// Constructor with number of rows, number of columns and number of mines
	public Grid(int rows, int columns, int nb_mines) {
		super();
		this.rows = rows;
		this.columns = columns;
		this.nb_mines = nb_mines;
		initialize();
	}

	// Useful getters and setters
	public Square[][] getSquares() {
		return squares;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getNb_mines() {
		return nb_mines;
	}
	
	public GamePanel getObserver() {
		return observer;
	}

	public void setObserver(GamePanel observer) {
		this.observer = observer;
	}

	/**
	 * Initialize grid with the correct dimension (rows, columns) and the right number of mines.
	 * Mines are allocated randomly to the squares in the grid.
	 */
	public void initialize(){
		// Set (or reset) the static shared attributes of class Square
		Square.setRemainingSquares(rows*columns);
		Square.setNb_flags(0);
		
		// Create empty grid with new squares
		squares = new Square[rows][columns];
		for (int i=0; i<rows; i++){
			for(int j = 0; j<columns; j++){
				squares[i][j] = new Square();
			}
		}
		
		// Choose randomly nb_mines numbers in [1, rows*columns- 1]
		List<Integer> random = new ArrayList<Integer>();
		for (int i = 0; i< rows*columns ; i++){
			random.add(i);
		}
		Collections.shuffle(random); // Make random permutation of list
		random = random.subList(0, nb_mines); // Take only the nb_mines first numbers of list
		
		// Allocate a mine to the corresponding chosen squares
		for(int number : random){
			int r = (int) number/columns;
			int c = number%columns;
			squares[r][c].setMine(true);
			
			// For each one, increase the characteristic number of all adjacent squares
			for (int i = -1; i<=1; i++){
				for(int j = -1; j<=1; j++)
				{
					if((i != 0 || j != 0) && r+i>=0 && r+i <rows && c+j>=0 && c+j<columns){
						squares[r+i][c+j].addAdjacentMine();
					}
				}
			}
		}
	}
	
	/**
	 * Manage the opening of the squares with implementation of cascade opening for isolated 
	 * squares with no mines around. This function won't open a square with a flag.
	 * @param x : square row
	 * @param y : square column
	 * @return false if the square is a non flagged mine that will explode
	 * true in other cases
	 */
	public boolean openSquare(int x, int y){
		// Test conditions of game over and return false
		if (squares[x][y].isMine() && squares[x][y].getState()==Square.State.Normal){return false;}
		
		// Elsewhere, try to open square and test if possible (no flag and not already opened)
		if(squares[x][y].open()){
			// Call observer to display square open state with number
			observer.updateSquare(x, y, squares[x][y].getAdjacentMines());
			
			// If no mines around, try to open adjacent squares
			if(squares[x][y].getAdjacentMines()==0)
			{
				for (int i = -1; i<=1; i++){
					for(int j = -1; j<=1; j++)
					{
						if((i != 0 || j != 0) && x+i>=0 && x+i <rows && y+j>=0 && y+j<columns){
							openSquare(x+i,y+j); // Recursive call
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Manage the action of adding or removing the flag on a square, and updating mines counter
	 * @param x : square row
	 * @param y : square column
	 */
	public void flagSquare(int x, int y){
		if(squares[x][y].changeFlag()){
			// Add Flag and call the GamePanel observer to display the change on GUI.
			observer.updateSquare(x,y,-1);
		}else{
			// Remove Flag and call the GamePanel observer to display the change on GUI.
			observer.updateSquare(x,y,-2);
		}
		// Update nb of mines in panel
		observer.updateMines(nb_mines - Square.getNb_flags());
	}	
}
