package minesweeper.project.model;

@FunctionalInterface
public interface SquareObserver {

	public void getEvent (Square square, SquareEvent event);
}
