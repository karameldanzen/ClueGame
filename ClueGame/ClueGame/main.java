package ClueGame;
import ClueGame.Board;

public class main {
	public static void main(String args[]) {
		Board board = new Board();
		board.setConfigFiles("boardConfig.csv", "roomConfig.txt");
		board.loadBoardConfig();
		board.loadRoomConfig();
	}
}
