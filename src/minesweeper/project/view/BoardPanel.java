package minesweeper.project.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import minesweeper.project.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	
		public BoardPanel(Board board) {
		
			setLayout(new GridLayout(board.getLines(), board.getColumns())); 
			
			board.forEachSquares(s -> add(new SquaresView(s)));
			board.registerObservers(e ->{
				// TODO mostrar resultado				
				
			});
	}

}
