package minesweeper.project.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import minesweeper.project.model.Square;
import minesweeper.project.model.SquareEvent;
import minesweeper.project.model.SquareObserver;

@SuppressWarnings("serial")
public class SquaresView extends JButton implements SquareObserver, MouseListener{
	
	private final Color BG_DEFAULT = new Color(184, 184, 184);
	private final Color BG_MARKED = new Color(8, 179, 247);
	private final Color BG_EXPLODE = new Color(189, 6, 68);
	private final Color BG_TEXT_GREEN = new Color(0, 100, 0);
	
	private Square square;

	public SquaresView(Square square) {
		this.square = square;
		setBackground(BG_DEFAULT);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
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
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(square.isMine()) {
			setBackground(Color.BLACK);
			setForeground(Color.RED);
			setText("💥");
			return;
		}
		
		setBackground(BG_DEFAULT);
		
		
		switch (square.minesNeighborhood()) {
		case 1:
			setForeground(BG_TEXT_GREEN);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		String value = !square.openNeighbor() ? square.minesNeighborhood() + "" : "";
		setText(value);
	}

	private void applyMarked() {
		setBackground(BG_MARKED);
		setText("🚩");
		
	}

	private void applyExplode() {
		setBackground(Color.BLACK);
		setForeground(Color.RED);
		setText("💥");
		
	}

	private void applyDefault() {
		setBackground(BG_DEFAULT);
		setBorder(BorderFactory.createBevelBorder(0));
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			square.openSquare();
		}else {
			square.toggleMarked();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
