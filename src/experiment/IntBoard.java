package experiment;
import java.util.*;
public class IntBoard {
	private int MAX_SIZE = 3;
	private Map<BoardCell, Set<BoardCell>> adjacentTileMap;
	private Set<BoardCell> visitedTiles;
	private Set<BoardCell> validTargets;
	private BoardCell[][] grid;
	public IntBoard() {
		calcAdjacencies();
	}
	void calcAdjacencies(){
		for(int i = 0; i < grid.length; i++) { // i equals columns j = rows 
			for (int j = 0; j < grid[i].length; j++) {
				Set adjSet = new HashSet<BoardCell>(); // adjacent set for this for loop
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
				adjacentTileMap.put(grid[i][j], adjSet); // sets the adjSet to the corrisponding grid space
					
				}
			}
		}
	}
	Set<BoardCell> getAdjList(BoardCell cell) {
		Set adjList = new HashSet<BoardCell>();
		return adjList;
	}
	void calcTargets(BoardCell startCell, int pathLength) {
	
	}
	Set<BoardCell> getTargets(){	
		return validTargets;
	}

	
}