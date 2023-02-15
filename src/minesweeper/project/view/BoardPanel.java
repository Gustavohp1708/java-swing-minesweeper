package minesweeper.project.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import minesweeper.project.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	
		public BoardPanel(Board board) {
		
			setLayout(new GridLayout(board.getLines(), board.getColumns())); 
			
			board.forEachSquares(s -> add(new SquaresView(s)));
			board.registerObservers(e ->{				
				
				SwingUtilities.invokeLater(() -> {					
					if(e.isWin()) {
						JOptionPane.showMessageDialog(this,  "You win!");
					}else {
						JOptionPane.showMessageDialog(this,  "You lose!");
					}			
					
				});				
					
			});
	}

}
