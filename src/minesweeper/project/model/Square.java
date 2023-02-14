package minesweeper.project.model;

import java.util.ArrayList;
import java.util.List;

public class Square {
	
	private final int line;
	private final int column;
	
	private boolean open;
	private boolean mine;
	private boolean marked;
	
	private List<Square> neighbors = new ArrayList<>();
	private List<SquareObserver> observers = new ArrayList<>();
	
	public Square(int line, int column) {
		
		this.line = line;
		this.column = column;
	}
	
	public void registerObserver(SquareObserver observer) {
		observers.add(observer);
	}
	
	private void notifyObservers(SquareEvent event) {
		observers.stream().forEach(o -> o.getEvent(this, event));
	}
	
	boolean addNeighbor(Square neighbor) {
		boolean differentLine = line != neighbor.line;
		boolean differentColumn = column != neighbor.column;
		boolean diagonal = differentLine && differentColumn;
		
		int deltaLine = Math.abs(this.line - neighbor.line);
		int deltaColumn = Math.abs(this.column - neighbor.column);
		int calcDelta = deltaLine + deltaColumn;
		
		if(calcDelta == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		}else if (calcDelta == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		}else {return false;}				
		
	}
		
	void toggleMarked(){
		if(!open) {
			marked = !marked;
			
			if (marked) {
				notifyObservers(SquareEvent.MARKED);
			}else {
				notifyObservers(SquareEvent.UNMARK);
			}
		}
	}
	
	boolean openSquare(){
		
		if(!open && !marked) {
						
			if(mine) {
				notifyObservers(SquareEvent.EXPLODE);
				return true;
			}
			
			setOpen(true);
			
			if(openNeighbor()) {
				neighbors.forEach(n -> n.openSquare());
			}
			
		}
		return false;
	}
	
	boolean openNeighbor() {
		return neighbors.stream().noneMatch(n -> n.mine);
	}
	
	void insertMines() {
		mine = true;
	}
	
	
	boolean objectives() {
		boolean safeOpenSquare = !mine && open;
		boolean protectedSquare = mine && marked;
		return safeOpenSquare || protectedSquare;
	}
	
	long minesNeighborhood() {
		return neighbors.stream().filter(n -> n.mine).count();
	}
		
	void restartGame() {
		open = false;
		mine = false;
		marked = false;
	}	
		
	public boolean isMarked() {
		return marked;
	}

		
	void setOpen(boolean open) {
		this.open = open;
		
		if(open) {
			notifyObservers(SquareEvent.OPEN);
		}
	}

	public boolean isOpen() {
		return open;
	}

	public boolean isMine() {
		return mine;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
}


