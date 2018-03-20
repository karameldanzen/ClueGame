package ClueGame;

public class BoardCell {
	int row;
	int col;
	public String tileType;
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
	public BoardCell(){
	}
}
