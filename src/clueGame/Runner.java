package clueGame;
import clueGame.Board;

public class Runner {
	public static void main(String args[]) {
		Board board = new Board();
		board.setConfigFiles("boardConfig.csv", "roomConfig.txt");
		board.initialize();
	}
}
