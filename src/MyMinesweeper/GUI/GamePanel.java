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

public class GamePanel extends JPanel implements MouseListener {
	
	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;
	private int rows;
	private int cols;
	
	private Game game;
	
	private JLabel smiley;
	
	private JLabel counter;
	
	private JLabel minesNb;
	
	private JPanel gridPanel;
	
	private JComponent[][] squareComponents;
	
	public GamePanel(Game game) {
		super(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.rows = game.getGrid().getRows();
		this.cols = game.getGrid().getColumns();
		this.game = game;
		
		// Initialize Grid and Game observer
		game.getGrid().setObserver(this);
		game.setObserver(this);
		
		//Mines
		JPanel minesInfos = new JPanel();
		minesNb = new JLabel(String.valueOf(game.getGrid().getNb_mines()));
		minesNb.setFont(new Font("Arial",Font.BOLD,21));
		minesNb.setForeground(Color.DARK_GRAY);
		minesNb.setBorder(BorderFactory.createEmptyBorder(3, 0 , 0, 0));
		minesInfos.setBackground(Color.LIGHT_GRAY);
		minesInfos.add(minesNb);
		minesInfos.add(new JLabel(scaleImage(new ImageIcon("res/Mine.png"),22,22)));
		minesInfos.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		this.add(minesInfos,BorderLayout.WEST);
		
		//Smiley
		this.smiley = new JLabel(scaleImage(new ImageIcon("res/SmileFace.png"),35,35));
		this.add(smiley,BorderLayout.CENTER);
		
		//Counter
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
		
		//Grid
		gridPanel = new JPanel(new GridLayout(rows,cols));
		squareComponents = new JComponent[rows][cols];
		for (int i=0; i<rows;i++){
			for (int j = 0; j<cols; j++){
				squareComponents[i][j] = new JButton(scaleImage(new ImageIcon("res/square.png"),20,20));
				squareComponents[i][j].setBorder(BorderFactory.createEmptyBorder());
				((JButton) squareComponents[i][j]).addMouseListener(this);
				gridPanel.add(squareComponents[i][j]);
			}
		}
		gridPanel.setBackground(Color.LIGHT_GRAY);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(5,15,15,15));
		this.add(gridPanel, BorderLayout.SOUTH);
	}

	private ImageIcon scaleImage(ImageIcon img, int width, int height){
		Image scaleImg = img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(scaleImg);
	}
	
	public void updateSquare(int i, int j, int state){ 
		//BlackMine = 9, RedMine = 10 Normal = 11
		String[] images = {"res/Normal.png","res/1.png","res/2.png","res/3.png","res/4.png","res/5.png"
				,"res/6.png","res/7.png","res/8.png","res/BlackMine.png","res/RedMine.png","res/Square.png"};
		switch (state){
		case -2://DeFlag
			squareComponents[i][j] = new JButton(scaleImage(new ImageIcon("res/Square.png"),20,20));
			((JButton) squareComponents[i][j]).addMouseListener(this);
			squareComponents[i][j].setBorder(BorderFactory.createEmptyBorder());
			break;
		case -1://Flag
			squareComponents[i][j] = new JButton(scaleImage(new ImageIcon("res/Flag.png"),20,20));
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
			squareComponents[i][j] = new JLabel(scaleImage(new ImageIcon(images[state]),20,20));
			break;
		default:
			break;
		}
		gridPanel.remove(i*cols+j);
		gridPanel.add(squareComponents[i][j],i*cols+j);
		gridPanel.revalidate();
	}
	
	public void updateMines(int num){
		minesNb.setText(String.valueOf(num));
		JPanel minesInfos = (JPanel) getComponent(0);
		minesInfos.remove(0);
		minesInfos.add(minesNb,0);
		minesInfos.revalidate();
	}
	
	public void updateCounter(int count){
		count++; // counter in seconds
		int min = (int) count/60;
		int sec = count%60;
		String minText = (min<10) ? "0"+min : String.valueOf(min);
		String secText = (sec<10) ? "0"+sec : String.valueOf(sec);
		counter.setText(minText + ":" + secText);
		System.out.println(counter.getText());
		JPanel counterPanel = (JPanel) getComponent(2);
		counterPanel.remove(0);
		counterPanel.add(counter);
		counterPanel.repaint();
	}
	
	public void endGame(Boolean hasWon){
		String image = (hasWon) ? "res/CoolFace.png" : "res/DeadFace.png";
		smiley = new JLabel(scaleImage(new ImageIcon(image),35,35));
		this.remove(1);
		this.add(smiley,BorderLayout.CENTER);
		revalidate();
		
		String[] choices = {"OK", "Play again"};
		String message = (hasWon)? "Congratulations ! \nYou win that game !"
			: "Oops... Game Over ! \nSorry, you loose !";
		String title = (hasWon) ? "Victory": "Defeat";
		int input = JOptionPane.showOptionDialog(MainWindow.getSharedInstance(), message,title,
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
			        new ImageIcon(image), choices, choices[0]);
		if (input==JOptionPane.NO_OPTION){MainWindow.getSharedInstance().initWindow(game.getLevel());}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// getting the square
		for (int i=0; i<rows;i++){
			for (int j = 0; j<cols; j++){
				if(e.getSource()==squareComponents[i][j]){
					
					// Left click
					if(e.getButton()==MouseEvent.BUTTON1){
						game.leftClicOn(i, j);
					}
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
