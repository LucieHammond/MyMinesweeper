package MyMinesweeper.Core;

import java.util.ArrayList;
import java.util.Collections;

public class Grid {

	private Square[][] squares;
	
	private int rows;
	
	private int columns;
	
	private int nb_mines;

	public Grid(int rows, int columns, int nb_mines) {
		super();
		this.rows = rows;
		this.columns = columns;
		this.nb_mines = nb_mines;
		initialize();
	}

	public Square[][] getSquares() {
		return squares;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getNb_mines() {
		return nb_mines;
	}

	public void setNb_mines(int nb_mines) {
		this.nb_mines = nb_mines;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public void initialize(){
		
		squares = new Square[rows][columns];
		ArrayList<Integer> random = new ArrayList<Integer>();
		for (int i = 1; i<= rows*columns ; i++){
			random.add(i);
		}
		Collections.shuffle(random);
		random.subList(0, nb_mines);
		
		for(int number : random){
			int r = number%columns;
			int c = number-r*columns;
			squares[r][c].setMine(true);
			
			for (int i = -1; i<=1; i++){
				for(int j = -1; j<=1; j++)
				{
					if(i != 0 && j != 0){
						squares[r+i][c+j].addAdjacentMine();
					}
				}
			}
		}
	}
	
	public boolean openSquare(int x, int y){
		if (squares[x][y].isMine()){return false;}
		
		if(squares[x][y].open())
		{
			for (int i = -1; i<=1; i++){
				for(int j = -1; j<=1; j++)
				{
					if(i != 0 && j != 0){
						openSquare(x+i,y+j);
					}
				}
			}
		}
		return true;
	}
	
	public void flagSquare(int x, int y){
		squares[x][y].changeFlag();
	}	
}
