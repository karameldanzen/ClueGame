package ClueGame;

public class BoardCell {
	int row;
	int col;
	private String tileType;
	public boolean isWalkway(){
		if (tileType == "W") return true;
		else return false;
	}
	public boolean isDoorway(){
		if (tileType.length() == 2) return true;
		else return false;
	}
	public boolean isRoom(){
		if (!isWalkway() && !isDoorway()) return true;
		else return false;
	}
	public BoardCell(int a, int b, String r){
		row = b;
		col = a;
		tileType = r;
	}
	public void setTileType(String t) {
		tileType = t;
	}
	public String getTileType() {
		return tileType;
	}

	public DoorDirection getDoorDirection() {	//What Direction
		if (tileType.length() > 1) {
			if (tileType.charAt(1) == 'U') {
				return DoorDirection.UP;
			}
			else if (tileType.charAt(1) == 'D') {
				return DoorDirection.DOWN;
			}
			else if (tileType.charAt(1) == 'R') {
				return DoorDirection.RIGHT;
			}
			else if (tileType.charAt(1) == 'L') {
				return DoorDirection.LEFT;
			}
		}
		return DoorDirection.NONE;
}
	public BoardCell(){
	}
	public BoardCell(int x, int y){
		row = x;
		col = y;
	}
}
