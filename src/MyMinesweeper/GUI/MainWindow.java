package MyMinesweeper.GUI;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MyMinesweeper.Core.Game;

/**
 * This class represents the main Java window that displays the game and stays opened 
 * until you close it. This window enable the user to restart new games.
 * @author Lucie
 */
public class MainWindow extends JFrame implements ActionListener {

	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Static unique instance of main window
	 */
	private static MainWindow sharedInstance;
	
	/**
	 * Button used to start a new game
	 */
	private JButton newGame;
	
	/**
	 * Panel that displays and manage the current game in detail.
	 */
	private GamePanel gamePanel;

	// Private constructor
	private MainWindow() throws HeadlessException {
		super("Minesweeper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	/**
	 * Gets the shared unique instance of class. This Window is instantiated only once.
	 * @return the unique instance of MainWindow
	 */
	public static MainWindow getSharedInstance(){
		if (sharedInstance==null){
			sharedInstance = new MainWindow();
		}
		return sharedInstance;
	}
	
	/**
	 * Initialize main Window with a given level for the game
	 * @param level the level of the Minesweeper that was chosen.
	 */
	public void initWindow(int level){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		
		// Options buttons (here there is only one : the "new Game" button)
		JPanel options = new JPanel(new FlowLayout(FlowLayout.LEFT,5,10));
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		options.add(newGame);
		panel.add(options);
		
		// Create instance of GamePanel corresponding to level
		gamePanel = new GamePanel(new Game(level));
		gamePanel.setMaximumSize(gamePanel.getPreferredSize());
		panel.add(gamePanel);
		panel.setBorder(BorderFactory.createEmptyBorder(0,15,20,15));
		
		// Add options and GamePanel to the view and make it visible
		setContentPane(panel);
		pack();
		setVisible(true);
	}
	
	/**
	 * Open Dialog Box that enable user to choose a level for a new game
	 */
	public void newGame(){
		String[] choices = {"beginner","intermediate","advanced","expert"};
		String input = (String) JOptionPane.showInputDialog(this,"Please choose a level :",
				"New Game", JOptionPane.PLAIN_MESSAGE, null ,choices,null);
		if(input=="beginner"){MainWindow.getSharedInstance().initWindow(Game.BEGINNER);}
		else if(input=="intermediate"){MainWindow.getSharedInstance().initWindow(Game.INTERMEDIATE);}
		else if(input=="advanced"){MainWindow.getSharedInstance().initWindow(Game.ADVANCED);}
		else if(input=="expert"){MainWindow.getSharedInstance().initWindow(Game.EXPERT);}
	}

	/**
	 * Manage response to provide when user click on "New Game" button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		newGame();
	}
	
	/**
	 * Main function : starting point of the application. 
	 * Here we will just call the "New Game" function
	 * @param args args
	 */
	public static void main(String[] args) {
		MainWindow.getSharedInstance().newGame();
	}
	
}
