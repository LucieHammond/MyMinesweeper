package MyMinesweeper.GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MyMinesweeper.Core.Game;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static MainWindow sharedInstance;
	
	private JButton newGame;
	
	private JButton stats;
	
	private GamePanel gamePanel;

	private MainWindow() throws HeadlessException {
		super("Minesweeper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 300);
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
		JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER,10,5));
		title.add(new JLabel("--- MINESWEEPER GAME ---"));
		panel.add(title);
		
		JPanel options = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
		newGame = new JButton("New Game");
		stats = new JButton("Statistics");
		options.add(newGame);
		options.add(stats);
		//options.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(options);
		
		gamePanel = new GamePanel();
		panel.add(gamePanel);
		
		
		setContentPane(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		MainWindow.getSharedInstance().initWindow(Game.BEGINNER);
	}
	
}
