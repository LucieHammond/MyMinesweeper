package MyMinesweeper.Core;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import MyMinesweeper.GUI.GamePanel;

public class Game {
	
	private Grid grid;
	
	public final static int BEGINNER = 0;
	public final static int INTERMEDIATE = 1;
	public final static int ADVANCED = 2;
	public final static int EXPERT = 3;
	
	private int level;
	
	private int counter;
	
	private Timer timer;
	
	private GamePanel observer;

	public Game(int level) {
		super();
		this.level = level;
		this.counter = 0;
		initGame(level);
	}

	public Grid getGrid() {
		return grid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public GamePanel getObserver() {
		return observer;
	}

	public void setObserver(GamePanel observer) {
		this.observer = observer;
	}

	public void initGame(int level){
		int rows = 1;
		int columns = 1;
		int nb_mines = 1;
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
	
	public void startCounter(){
		System.out.println("Game started : Go !");
		timer = new Timer();
	    timer.schedule(new TimerTask(){ public void run() {
	    	counter++;
	    	observer.updateCounter(counter);}
	    },Calendar.getInstance().getTime(),1000);
	}

	public void rightClicOn(int x, int y){
		grid.flagSquare(x, y);
	}
	
	public void leftClicOn(int x, int y){
		if(counter==0){startCounter();}
		
		if(!grid.openSquare(x, y)){
			gameOver(x,y);
		}
		else if(grid.getNb_mines()==Square.getRemainingSquares()){
			victory();
		}
	}
	
	public void gameOver(int x,int y){
		System.out.println("Game Over : You Loose");
		timer.cancel();
		Square[][] squares = grid.getSquares();
		for(int i=0;i<grid.getRows();i++){
			for(int j=0;j<grid.getColumns();j++){
				if(squares[i][j].isMine()){
					observer.updateSquare(i,j,9);
				}else if(squares[i][j].getState()==Square.State.Normal){
					observer.updateSquare(i,j,11);
				}
			}
		}
		observer.updateSquare(x,y,10);
		observer.endGame(false);
	}
	
	public void victory(){
		System.out.println("Victory : You Win");
		timer.cancel();
		observer.endGame(true);
	}
}
