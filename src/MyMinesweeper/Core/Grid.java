package MyMinesweeper.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import MyMinesweeper.GUI.GamePanel;

public class Grid {

	private Square[][] squares;
	
	private int rows;
	
	private int columns;
	
	private int nb_mines;
	
	private GamePanel observer;

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
	
	public GamePanel getObserver() {
		return observer;
	}

	public void setObserver(GamePanel observer) {
		this.observer = observer;
	}

	public void initialize(){
		Square.setRemainingSquares(rows*columns);
		squares = new Square[rows][columns];
		for (int i=0; i<rows; i++){
			for(int j = 0; j<columns; j++){
				squares[i][j] = new Square();
			}
		}
		List<Integer> random = new ArrayList<Integer>();
		for (int i = 0; i< rows*columns ; i++){
			random.add(i);
		}
		Collections.shuffle(random);
		random = random.subList(0, nb_mines);
		
		for(int number : random){
			int r = (int) number/columns;
			int c = number%columns;
			squares[r][c].setMine(true);
			
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
	
	public boolean openSquare(int x, int y){
		if (squares[x][y].isMine()){return false;}
		if(squares[x][y].open()){
			observer.updateSquare(x, y, squares[x][y].getAdjacentMines());
			if(squares[x][y].getAdjacentMines()==0)
			{
				for (int i = -1; i<=1; i++){
					for(int j = -1; j<=1; j++)
					{
						if((i != 0 || j != 0) && x+i>=0 && x+i <rows && y+j>=0 && y+j<columns){
							openSquare(x+i,y+j);
						}
					}
				}
			}
		}
		return true;
	}
	
	public void flagSquare(int x, int y){
		if(squares[x][y].changeFlag()){
			//Flag
			observer.updateSquare(x,y,-1);
		}else{
			//DeFlag
			observer.updateSquare(x,y,-2);
		}
		// Update nb of mines in panel
		observer.updateMines(nb_mines - Square.getNb_flags());
	}	
}
