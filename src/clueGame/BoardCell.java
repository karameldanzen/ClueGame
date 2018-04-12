package clueGame;

public class BoardCell {
	int row;
	int col;
	private DoorDirection direction;
	private char tileType;
	public boolean isWalkway(){
		if (tileType == 'W') {
			return true;
		}
		return false;
	}
	public boolean isDoorway() {
		if (direction != DoorDirection.NONE) {  
			return true; 
		}
		return false;
	}
	public boolean isRoom() {
		if (tileType != 'W' && tileType != 'X') return true;
		return false;
	}
	public char getInitial() {
		return tileType;
	}
	public DoorDirection getDoorDirection() {	// returns door direction
		return direction;
	}
	public BoardCell(){
		row = 0;
		col = 0;
		direction = DoorDirection.NONE;
		tileType = 'W'; 
	}
	public BoardCell(int x, int y, DoorDirection d, char initial){
		row = x;
		col = y;
		direction = d;
		tileType = initial; 
	}
}
