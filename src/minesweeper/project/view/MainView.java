package minesweeper.project.view;

import javax.swing.JFrame;

import minesweeper.project.model.Board;

@SuppressWarnings("serial")
public class MainView extends JFrame{
	
	public MainView() {
		Board board = new Board(16, 30, 99);				
		add(new BoardPanel(board));
		
		setTitle("Minesweeper");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainView();
	}
	
}
