package minesweeper.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

	private int lines;
	private int columns;
	private int mines;
	
	private final List<Square> squares = new ArrayList<>();

	public Board(int lines, int columns, int mines) {
		
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateSquares();
		generateNeighbors();
		drawMines();
	}
	
	public void openSquare(int line, int column) {
		try {
			
			squares.parallelStream()
				.filter(s -> s.getLine() == line && s.getColumn() == column)
				.findFirst()
				.ifPresent(s -> s.openSquare());
			
		} catch (Exception e) {
			//FIXME Ajustar a implementação do método abrir
			squares.forEach(s -> s.setOpen(true));
			throw e;
		}
		
		
		
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
				squares.add(new Square(l, c));
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
	
}
