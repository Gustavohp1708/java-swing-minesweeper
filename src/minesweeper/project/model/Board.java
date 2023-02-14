package minesweeper.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements SquareObserver{

	private int lines;
	private int columns;
	private int mines;
	
	private final List<Square> squares = new ArrayList<>();
	private final List<Consumer<ResultEvent>> observers = new ArrayList<>();

	public Board(int lines, int columns, int mines) {
		
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateSquares();
		generateNeighbors();
		drawMines();
	}
	
	public void registerObservers(Consumer<ResultEvent> observer) {
		observers.add(observer);
	}
	
	private void notifyObservers(boolean result) {
		observers.stream().forEach(o -> o.accept(new ResultEvent(result)));
	}
	
	public void openSquare(int line, int column) {
		squares.parallelStream()
		.filter(s -> s.getLine() == line && s.getColumn() == column)
		.findFirst()
		.ifPresent(s -> s.openSquare());			
	} 	
	
	public void toggleMarked(int line, int column) {
		squares.parallelStream()
		.filter(s -> s.getLine() == line && s.getColumn() == column)
		.findFirst()
		.ifPresent(s -> s.toggleMarked());
	}

	private void generateSquares() {
		for (int l = 0; l < lines; l++) {
			for (int c = 0; c < columns; c++) {
				Square square = new Square(l, c);
				square.registerObserver(this);
				squares.add(square);
			}
		}		
	}

	
	private void generateNeighbors() {
		for (Square s1: squares) {
			for (Square s2: squares) {
				s1.addNeighbor(s2);
			}
		}
		
	}

	private void drawMines() {
		long squaresWithMines = 0;
		Predicate<Square> mined = s -> s.isMine();
		
		do {			
			int random = (int)(Math.random() * squares.size());
			squares.get(random).insertMines();
			squaresWithMines = squares.stream().filter(mined).count();
		} while (squaresWithMines < mines);
		
	}
	
	public boolean objectives() {
		return squares.stream().allMatch(s -> s.objectives());
	}
	
	public void restartGame() {
		squares.stream().forEach(s -> s.restartGame());
		drawMines();
	}	
	
	@Override
	public void getEvent(Square square, SquareEvent event) {
		if(event == SquareEvent.EXPLODE) {
			showMines();
			notifyObservers(false);
		}else if(objectives()){			
			notifyObservers(true);
		}
		
	}
	
	private void showMines() {
		squares.stream()
		.filter(s -> s.isMine())
		.forEach(s -> s.setOpen(true));
	}
	
}
