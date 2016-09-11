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
 * @author Lucie
 *
 */
public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static MainWindow sharedInstance;
	
	private JButton newGame;
	
	private GamePanel gamePanel;

	private MainWindow() throws HeadlessException {
		super("Minesweeper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	public static MainWindow getSharedInstance(){
		if (sharedInstance==null){
			sharedInstance = new MainWindow();
		}
		return sharedInstance;
	}
	
	public void initWindow(int level){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		
		JPanel options = new JPanel(new FlowLayout(FlowLayout.LEFT,5,10));
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		options.add(newGame);
		panel.add(options);
		
		gamePanel = new GamePanel(new Game(level));
		gamePanel.setMaximumSize(gamePanel.getPreferredSize());
		panel.add(gamePanel);
		panel.setBorder(BorderFactory.createEmptyBorder(0,15,20,15));
		setContentPane(panel);
		pack();
		setVisible(true);
	}
	
	public void newGame(){
		String[] choices = {"beginner","intermediate","advanced","expert"};
		String input = (String) JOptionPane.showInputDialog(this,"Please choose a level :",
				"New Game", JOptionPane.PLAIN_MESSAGE, null ,choices,null);
		if(input=="beginner"){MainWindow.getSharedInstance().initWindow(Game.BEGINNER);}
		else if(input=="intermediate"){MainWindow.getSharedInstance().initWindow(Game.INTERMEDIATE);}
		else if(input=="advanced"){MainWindow.getSharedInstance().initWindow(Game.ADVANCED);}
		else if(input=="expert"){MainWindow.getSharedInstance().initWindow(Game.EXPERT);}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		newGame();
	}
	
	public static void main(String[] args) {
		MainWindow.getSharedInstance().newGame();
	}
	
}
