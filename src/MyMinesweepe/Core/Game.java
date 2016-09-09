package MyMinesweepe.Core;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
	
	private Grid myGrid;
	
	public final static int BEGINNER = 0;
	public final static int INTERMEDIATE = 1;
	public final static int EXPERT = 2;
	public final static int CUSTOM = 3;
	
	private int level;
	
	public Calendar startDate;

	public Game(int level) {
		super();
		this.level = level;
		initGame(level);
	}
	
	public Game(int rows, int columns, int nb_mines){
		super();
		this.level = CUSTOM;
		myGrid = new Grid(rows, columns, nb_mines);
	}

	public Grid getMyGrid() {
		return myGrid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	public void initGame(int level){
		int rows = 1;
		int columns = 1;
		int nb_mines = 1;
		if(level>=0 && level < 3){
			int[] possibleRows = {9,9,16};
			int[] possibleColumns = {9,16,30};
			int[] possibleMinesNb = {10,40,99};
			rows = possibleRows[level];
			columns = possibleColumns[level];
			nb_mines = possibleMinesNb[level];
		}
		else{
			System.out.println("Error : there is no such predefined level");
		}
		myGrid = new Grid(rows, columns, nb_mines);
	}
	
	public void startCounter(){
		System.out.println("Game started : Go !");
		Timer timer = new Timer();
		startDate = Calendar.getInstance();
	    timer.schedule(new TimerTask(){ public void run() {} },
	    		startDate.getTime(),1000);
	}

	public void rightClicOn(int x, int y){
		myGrid.flagSquare(x, y);
	}
	
	public void leftClicOn(int x, int y){
		if(startDate == null){startCounter();}
		
		if(!myGrid.openSquare(x, y)){
			gameOver();
		}
		else if(myGrid.getNb_mines()==Square.getRemainingSquares()){
			victory();
		}
	}
	
	public void gameOver(){
		System.out.println("Game Over : You Loose");
	}
	
	public void victory(){
		System.out.println("Victory : You Win");
	}
}
