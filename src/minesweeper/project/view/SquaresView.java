package minesweeper.project.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import minesweeper.project.model.Square;
import minesweeper.project.model.SquareEvent;
import minesweeper.project.model.SquareObserver;

@SuppressWarnings("serial")
public class SquaresView extends JButton implements SquareObserver{
	
	private final Color BG_DEFAULT = new Color(184, 184, 184);
	private final Color BG_MARKED = new Color(8, 179, 247);
	private final Color BG_EXPLODE = new Color(189, 6, 68);
	private final Color BG_TEXT_GREEN = new Color(0, 100, 0);
	
	private Square square;

	public SquaresView(Square square) {
		this.square = square;
		setBackground(BG_DEFAULT);
		setBorder(BorderFactory.createBevelBorder(0));
		
		
		square.registerObserver(this);
	}

	@Override
	public void getEvent(Square square, SquareEvent event) {
		
		switch(event) {
		case OPEN:
			applyOpen();
			break;
		case MARKED:
			applyMarked();
			break;
		case EXPLODE:
			applyExplode();
			break;
		default:
			applyDefault();
		}
		
	}

	private void applyOpen() {
		// TODO Auto-generated method stub
		
	}

	private void applyMarked() {
		// TODO Auto-generated method stub
		
	}

	private void applyExplode() {
		// TODO Auto-generated method stub
		
	}

	private void applyDefault() {
		// TODO Auto-generated method stub
		
	}
}
