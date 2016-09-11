package MyMinesweeper.Core;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import MyMinesweeper.GUI.GamePanel;

/**
 * This class represents a game of Minesweeper
 * @author Lucie
 */
public class Game {
	
	/**
	 * Current grid of the game
	 */
	private Grid grid;
	
	// Constants that represent the available levels for the game
	public final static int BEGINNER = 0;
	public final static int INTERMEDIATE = 1;
	public final static int ADVANCED = 2;
	public final static int EXPERT = 3;
	
	/**
	 * Level of the game
	 */
	private int level;
	
	/**
	 * Time counter : represents the time elapsed (in seconds) since the first square was opened
	 */
	private int counter;
	
	/**
	 * Timer used to schedule all changes in time counter.
	 */
	private Timer timer;
	
	/**
	 * Link to the panel that displays the game on User Interface.
	 * The Game object will call some of the GamePanel update functions when necessary.
	 */
	private GamePanel observer;

	// Constructor
	public Game(int level) {
		super();
		this.level = level;
		this.counter = 0;
		this.timer = new Timer();
		initGame(level);
	}

	// Useful Getters and Setters
	public Grid getGrid() {
		return grid;
	}

	public int getLevel() {
		return level;
	}

	public void setObserver(GamePanel observer) {
		this.observer = observer;
	}

	/**
	 * Initialize the grid of the game with proper characteristics of the level chosen 
	 * (rows, columns and number of mines)
	 * @param level : level of the game you want to initialize
	 */
	public void initGame(int level){
		int rows = 1;
		int columns = 1;
		int nb_mines = 0;
		if(level>=0 && level < 4){
			int[] possibleRows = {9,10,16,16};
			int[] possibleColumns = {9,12,16,30};
			int[] possibleMinesNb = {10,20,40,99};
			rows = possibleRows[level];
			columns = possibleColumns[level];
			nb_mines = possibleMinesNb[level];
		}
		else{
			System.out.println("Error : there is no such predefined level");
		}
		grid = new Grid(rows, columns, nb_mines);
	}
	
	/**
	 * Start time counter by scheduling periodic updates of the counter witch is displayed to user.
	 * The task are repeated at each second.
	 */
	public void startCounter(){
		System.out.println("Game started : Go !");
	    timer.schedule(new TimerTask(){ public void run() {
	    	observer.updateCounter(counter); // Calling the GamePanel instance for update
	    	counter++;} // Increment the counter 
	    },Calendar.getInstance().getTime(),1000);
	}

	/**
	 * Manage the response to be provided when user generates right click event on square(x,y).
	 * @param x : square row
	 * @param y : square column
	 */
	public void rightClicOn(int x, int y){
		grid.flagSquare(x, y);
	}
	
	/**
	 * Manage the response to be provided when user generates left click event on square(x,y).
	 * @param x : square row
	 * @param y : square column
	 */
	public void leftClicOn(int x, int y){
		if(counter==0){startCounter();} // Start counter when user try to open first square
		
		// Open square and test if it is a mine
		if(!grid.openSquare(x, y)){
			gameOver(x,y);
		}
		// Otherwise test if all non mine squares have been opened
		else if(grid.getNb_mines()==Square.getRemainingSquares()){
			victory();
		}
	}
	
	/**
	 * End game and manage actions to be made when user touch a mine and loose
	 * (stop timer, reveal all mines, freeze screen so that user can't continue...)
	 * @param x : row of touched mine square
	 * @param y : column of touched mine square
	 */
	public void gameOver(int x,int y){
		System.out.println("Game Over : You Loose");
		timer.cancel();
		Square[][] squares = grid.getSquares();
		for(int i=0;i<grid.getRows();i++){
			for(int j=0;j<grid.getColumns();j++)
			{
				if(squares[i][j].isMine()){
					observer.updateSquare(i,j,9); // ask GamePanel observer to reveal mines
				}else if(squares[i][j].getState()==Square.State.Normal){
					observer.updateSquare(i,j,11); // ask observer to freeze normal square buttons
				}else if(squares[i][j].getState()==Square.State.Flagged){
					observer.updateSquare(i,j,12); // as observer to freeze flagged square buttons
				}
			}
		}
		observer.updateSquare(x,y,10); // The mine touched is displayed in red
		observer.endGame(false, counter); // inform observer that game has been lost.
	}
	
	/**
	 * End game and manage actions to be made when user has found all mines and won
	 * (stop timer, freeze screen...)
	 */
	public void victory(){
		System.out.println("Victory : You Win");
		timer.cancel();
		Square[][] squares = grid.getSquares();
		for(int i=0;i<grid.getRows();i++){
			for(int j=0;j<grid.getColumns();j++)
			{
				if(squares[i][j].getState()==Square.State.Normal){
					observer.updateSquare(i,j,11); // ask observer to freeze normal square buttons
				}else if(squares[i][j].getState()==Square.State.Flagged){
					observer.updateSquare(i,j,12); // as observer to freeze flagged square buttons
				}
			}
		}
		observer.endGame(true, counter); // inform GamePanel observer that game has been won.
	}
}
