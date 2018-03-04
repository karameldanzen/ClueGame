package experiment;
import java.util.*;
public class IntBoard {
	private int MAX_SIZE = 3;
	private Map<BoardCell, Set<BoardCell>> adjacentTileMap;
	private Set<BoardCell> visitedTiles;
	private Set<BoardCell> validTargets;
	public BoardCell a; 
	public BoardCell[][] grid;
	public IntBoard() {
		grid = new BoardCell[MAX_SIZE][MAX_SIZE] ;
		for (int i = 0; i < MAX_SIZE; i++){
			for (int j = 0; j < MAX_SIZE; j++){			
				grid[i][j] =  a;
			}
		}
		calcAdjacencies();
	}
	public Map<BoardCell, Set<BoardCell>> calcAdjacencies(){
		for(int i = 0; i < grid.length; i++) { // i equals columns j = rows 
			for (int j = 0; j < grid[i].length; j++) {
				Set<BoardCell> adjSet = new HashSet<BoardCell>(); // adjacent set for this for loop
				if ( i == 0){ // handles top of the board
					adjSet.add(grid[i+1][j]);
					if (j+1 <= grid[i].length){
						adjSet.add(grid[i][j+1]);
					}
					if (j-1 >= 0){
						adjSet.add(grid[i][j-1]);
					}
				}
				else if (i == grid.length){ // handles bottom
					adjSet.add(grid[i-1][j]);
					if (j+1 <= grid[i].length){
						adjSet.add(grid[i][j+1]);
					}
					if (j-1 >= 0){
						adjSet.add(grid[i][j-1]);
					}
					
				}
				else {
					adjSet.add(grid[i+1][j]);
					adjSet.add(grid[i-1][j]);
					if (j+1 <= grid[i].length){
						adjSet.add(grid[i][j+1]);
					}
					if (j-1 >= 0){
						adjSet.add(grid[i][j-1]);
					}
				}
				this.adjacentTileMap.put(grid[i][j], adjSet); // sets the adjSet to the corresponding grid space
					
				}
			}
		return this.adjacentTileMap;
		}
	public Set<BoardCell> getAdjList(BoardCell cell) {
		Set<BoardCell> adjList = adjacentTileMap.get(cell);                                                                                                                                                                                                                                                                                                                                                   
		return adjList;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		Stack<BoardCell> noPass = new Stack<BoardCell>();
		Set<BoardCell> validSpace = new HashSet<BoardCell>();
		if (pathLength % 2 == 0){
			for (int i = pathLength; i <=0; -- i ){
			if (i % 2 == 0){
			int col	= startCell.col + i;
			int row = (i - pathLength)  + startCell.row ; 
			int row2 = (pathLength - i)  + startCell.row ;
			validSpace.add(grid[col][row]);
			validSpace.add(grid[col][row2]);
			validSpace.add(grid[startCell.col][col]);
			validSpace.add(grid[col][startCell.row]);
			col	= startCell.col - i;
			validSpace.add(grid[col][row]);
			validSpace.add(grid[col][row2]);
			validSpace.add(grid[startCell.col][col]);
			validSpace.add(grid[col][startCell.row]);
			}
			else {
				int col	= startCell.col + i;
				int row = (i - pathLength)  + startCell.row ; 
				int row2 = (pathLength - i)  + startCell.row ;
				validSpace.add(grid[col][row]);
				validSpace.add(grid[col][row2]);
				col	= startCell.col - i;
				validSpace.add(grid[col][row]);
				validSpace.add(grid[col][row2]);
				}
			}
		}
			else {
				for (int i = pathLength; i <=0; -- i ){
					if (i % 2 == 0){
						int col	= startCell.col + i;
						int row = (i - pathLength)  + startCell.row ; 
						int row2 = (pathLength - i)  + startCell.row ;
						validSpace.add(grid[col][row]);
						validSpace.add(grid[col][row2]);
						col	= startCell.col - i;
						validSpace.add(grid[col][row]);
						validSpace.add(grid[col][row2]);
					}
					else {
						int col	= startCell.col + i;
						int row = (i - pathLength)  + startCell.row ; 
						int row2 = (pathLength - i)  + startCell.row ;
						validSpace.add(grid[col][row]);
						validSpace.add(grid[col][row2]);
						validSpace.add(grid[startCell.col][col]);
						validSpace.add(grid[col][startCell.row]);
						col	= startCell.col - i;
						validSpace.add(grid[col][row]);
						validSpace.add(grid[col][row2]);
						validSpace.add(grid[startCell.col][col]);
						validSpace.add(grid[col][startCell.row]);
					}
				}
		}
		validTargets = validSpace; 
}

	public Set<BoardCell> getTargets(){	
		return validTargets;
	}

	
}
