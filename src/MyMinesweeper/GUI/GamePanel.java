package MyMinesweeper.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MyMinesweeper.Core.*;

public class GamePanel extends JPanel implements ActionListener {
	
	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private JLabel smiley;
	
	private JLabel counter;
	
	private JLabel minesNb;
	
	private JComponent[][] squareComponents;
	
	public GamePanel(Game game) {
		super(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.game = game;
		
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
		
		int row = game.getGrid().getRows();
		int col = game.getGrid().getColumns();
		JPanel gridPanel = new JPanel(new GridLayout(row,col));
		squareComponents = new JComponent[row][col];
		for (int i=0; i<row;i++){
			for (int j = 0; j<col; j++){
				squareComponents[i][j] = new JButton(scaleImage(new ImageIcon("res/square.png"),20,20));
				squareComponents[i][j].setBorder(BorderFactory.createEmptyBorder());
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
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
