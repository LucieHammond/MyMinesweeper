package MyMinesweeper.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MyMinesweeper.Core.Game;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static MainWindow sharedInstance;
	
	private JButton newGame;
	
	private GamePanel gamePanel;

	private MainWindow() throws HeadlessException {
		super("Minesweeper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		MainWindow.getSharedInstance().initWindow(Game.EXPERT);
	}
	
}
