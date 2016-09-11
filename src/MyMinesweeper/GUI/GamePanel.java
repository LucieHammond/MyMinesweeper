package MyMinesweeper.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MyMinesweeper.Core.*;

/**
 * This class represents the panel that manage the GUI for a game.
 * @author Lucie
 *
 */
public class GamePanel extends JPanel implements MouseListener {
	
	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Number of rows in the grid
	 */
	private int rows;
	
	/**
	 * Number of columns in the grid
	 */
	private int cols;
	
	/**
	 * Instance of the current game associated to the panel
	 */
	private Game game;
	
	/**
	 * Image of smiley placed on top of the game
	 */
	private JLabel smiley;
	
	/**
	 * Label that displays the time counter
	 */
	private JLabel counter;
	
	/**
	 * Label that displays the number of remaining mines (total - nb of flags)
	 */
	private JLabel minesNb;
	
	/**
	 * Panel that represents the grid of Minesweeper in itself
	 */
	private JPanel gridPanel;
	
	/**
	 * Matrix of components representing the squares (buttons or labels depending on their state)
	 */
	private JComponent[][] squareComponents;
	
	/**
	 * Constructor that initialize all graphical elements of game.
	 * @param game : the instance of game to witch the gamePanel will be associated
	 */
	public GamePanel(Game game) {
		super(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		// Initialize Grid and Game observer with current instance of GamePanel
		game.getGrid().setObserver(this);
		game.setObserver(this);
		
		// Params rows and cols are initialized with instance of game.
		this.rows = game.getGrid().getRows();
		this.cols = game.getGrid().getColumns();
		this.game = game;
		
		// Mines counter
		JPanel minesInfos = new JPanel();
		minesNb = new JLabel(String.valueOf(game.getGrid().getNb_mines()));
		minesNb.setFont(new Font("Arial",Font.BOLD,21));
		minesNb.setForeground(Color.DARK_GRAY);
		minesNb.setBorder(BorderFactory.createEmptyBorder(3, 0 , 0, 0));
		minesInfos.setBackground(Color.LIGHT_GRAY);
		minesInfos.add(minesNb);
		minesInfos.add(new JLabel(scaleImage("res/Mine.png",22,22)));
		minesInfos.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		minesInfos.setPreferredSize(new Dimension(75,30));
		this.add(minesInfos,BorderLayout.WEST);
		
		// Smiley
		this.smiley = new JLabel(scaleImage("res/SmileFace.png",35,35));
		this.add(smiley,BorderLayout.CENTER);
		
		// Time counter
		counter = new JLabel("00:00");
		counter.setBorder(BorderFactory.createLoweredBevelBorder());
		counter.setFont(new Font("Arial", Font.BOLD, 17));
		counter.setForeground(Color.RED);
		counter.setPreferredSize(new Dimension(55,25));
		counter.setHorizontalAlignment(JLabel.CENTER);
		JPanel counterPanel = new JPanel();
		counterPanel.setOpaque(false);
		counterPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 15));
		counterPanel.add(counter);
		this.add(counterPanel, BorderLayout.EAST);
		
		// Grid
		gridPanel = new JPanel(new GridLayout(rows,cols));
		squareComponents = new JComponent[rows][cols];
		for (int i=0; i<rows;i++){
			for (int j = 0; j<cols; j++){
				squareComponents[i][j] = new JButton(scaleImage("res/square.png",20,20));
				squareComponents[i][j].setBorder(BorderFactory.createEmptyBorder());
				((JButton) squareComponents[i][j]).addMouseListener(this);
				gridPanel.add(squareComponents[i][j]);
			}
		}
		gridPanel.setBackground(Color.LIGHT_GRAY);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(5,15,15,15));
		this.add(gridPanel, BorderLayout.SOUTH);
	}

	/**
	 * Returns an rescaled version of images in order to use it as icons
	 * @param imgPath : path of initial image we want to scale
	 * @param width : width of new ImageIcon
	 * @param height : height of new ImageIcon
	 * @return a rescaled ImageIcon with given dimensions
	 */
	private ImageIcon scaleImage(String imgPath, int width, int height){
		ImageIcon img = new ImageIcon(imgPath);
		Image scaleImg = img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(scaleImg);
	}
	
	/**
	 * Update the graphical representation of a square on screen when its state has changed
	 * @param i : the row of the square to update
	 * @param j : the row of the square to update
	 * @param state : an integer representing the new state of the square 
	 * Normal = -2, Flag = -1, From 0 to 8 : Open with such number of adjacent mines
	 * BlackMine = 9, RedMine = 10, Frozen Normal = 11, Frozen Flag = 12
	 */
	public void updateSquare(int i, int j, int state){ 
		
		switch (state){
		case -2: // Remove Flag
			squareComponents[i][j] = new JButton(scaleImage("res/Square.png",20,20));
			((JButton) squareComponents[i][j]).addMouseListener(this);
			squareComponents[i][j].setBorder(BorderFactory.createEmptyBorder());
			break;
		case -1:// Add Flag
			squareComponents[i][j] = new JButton(scaleImage("res/Flag.png",20,20));
			((JButton) squareComponents[i][j]).addMouseListener(this);
			squareComponents[i][j].setBorder(BorderFactory.createEmptyBorder());
			break;
		case 0: 
		case 1: 
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			String[] images = {"res/Normal.png","res/1.png","res/2.png","res/3.png","res/4.png",
					"res/5.png","res/6.png","res/7.png","res/8.png","res/BlackMine.png",
					"res/RedMine.png","res/Square.png","res/Flag.png"};
			squareComponents[i][j] = new JLabel(scaleImage(images[state],20,20));
			break;
		default:
			break;
		}
		
		// Remove and add component again to make change effective
		gridPanel.remove(i*cols+j);
		gridPanel.add(squareComponents[i][j],i*cols+j);
		gridPanel.revalidate();
	}
	
	/**
	 * Update the graphical display of mine counter when flags are added or removed.
	 * Of course the position and number of flags are not necessarily right, so the number 
	 * displayed can be negative.
	 * @param num The remaining mines not found (total - nb of flags)
	 */
	public void updateMines(int num){
		minesNb.setText(String.valueOf(num));
		
		// Remove and add again component to make change effective
		JPanel minesInfos = (JPanel) getComponent(0);
		minesInfos.remove(0);
		minesInfos.add(minesNb,0);
		minesInfos.revalidate();
	}
	
	/**
	 * Update graphical display of time counter every second when asked by Game instance
	 * @param count Number of seconds that elapsed since game began.
	 */
	public void updateCounter(int count){
		// counter in seconds
		if (count<6000){
			int min = (int) count/60;
			int sec = count%60;
			String minText = (min<10) ? "0"+min : String.valueOf(min);
			String secText = (sec<10) ? "0"+sec : String.valueOf(sec);
			counter.setText(minText + ":" + secText);
			
			// Remove and add again component to make change effective
			JPanel counterPanel = (JPanel) getComponent(2);
			counterPanel.remove(0);
			counterPanel.add(counter);
			counterPanel.repaint();
		}
		/* If user spend more that 5999 seconds (1h40 !) on the game, counter still work but
		 * changes are not displayed because there is not enough place
		 */
		
	}
	
	/**
	 * Manage graphical response to provide when game is finished (dialog box with results, 
	 * change of the smiley on top with cool or dead face)
	 * @param hasWon : true in case of victory, false in case of defeat
	 * @param timeSpent : number of seconds since the beginning of the game
	 */
	public void endGame(Boolean hasWon, int timeSpent){
		String image = (hasWon) ? "res/CoolFace.png" : "res/DeadFace.png";
		
		// Change smiley on top of the game
		smiley = new JLabel(scaleImage(image,35,35));
		this.remove(1);
		this.add(smiley,BorderLayout.CENTER);
		revalidate();
		
		// Message with time to display on case of victory
		int heures = (int) timeSpent/3600;
		int min = (int) (timeSpent - 3600*heures)/60;
		int sec = timeSpent%60;
		String time = (heures==0) ? "\n\nYour time : " + min + "min " + sec + "s" :
			"\n\nYour time : " + heures + "h "+ min + "min " + sec + "s";
		
		// Display dialog box
		String[] choices = {"OK", "Play again"};
		String message = (hasWon)? "Congratulations ! \nYou win that game ! " + time
			: "Oops... Game Over ! \nSorry, you loose !";
		
		String title = (hasWon) ? "Victory": "Defeat";
		int input = JOptionPane.showOptionDialog(MainWindow.getSharedInstance(), message
				,title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				new ImageIcon(image), choices, choices[0]);
		if (input==JOptionPane.NO_OPTION){MainWindow.getSharedInstance().initWindow(game.getLevel());}
	}
	
	/**
	 * Manage response to Mouse click events made by user (right or left clik on squares).
	 * The event taken into account is a click event, so if you move the mouse when clicking 
	 * it will not work 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// getting the right square
		for (int i=0; i<rows;i++){
			for (int j = 0; j<cols; j++){
				if(e.getSource()==squareComponents[i][j]){
											
					// Left click
					if(e.getButton()==MouseEvent.BUTTON1){
						game.leftClicOn(i, j);
					}
					// Right click
					else{
						game.rightClicOn(i, j);
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
