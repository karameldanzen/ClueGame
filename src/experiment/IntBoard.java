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
		for(int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Set adjacencySet = new HashSet<BoardCell>();
				if (i-1 >= 0) {
					// TODO: actually add to adjacencySet
					adjacentTileMap.put(grid[i][j], adjacencySet);
				}
				else continue;
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
