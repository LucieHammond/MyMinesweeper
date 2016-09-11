package MyMinesweeper.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
		minesInfos.setBackground(Color.LIGHT_GRAY);
		this.minesNb = new JLabel(String.valueOf(game.getGrid().getNb_mines()));
		minesInfos.add(minesNb);
		minesInfos.add(new JLabel(scaleImage(new ImageIcon("res/Mine.png"),22,22)));
		minesInfos.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		this.add(minesInfos,BorderLayout.WEST);
		
		//Smiley
		this.smiley = new JLabel(scaleImage(new ImageIcon("res/SmileFace.png"),35,35));
		this.add(smiley,BorderLayout.CENTER);
		
		//Counter
		this.counter = new JLabel("00:00");
		counter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		this.add(counter, BorderLayout.EAST);
		
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
		gridPanel.repaint();
		gridPanel.revalidate();
	}
	
	public void updateSmiley(Boolean hasWon){
		String image = (hasWon) ? "res/CoolFace.png" : "res/DeadFace.png";
		smiley = new JLabel(scaleImage(new ImageIcon(image),35,35));
		smiley.repaint();
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
		// TODO Auto-generated method stub
		
	}

}
