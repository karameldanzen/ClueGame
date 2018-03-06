package experiment;
import java.util.*;
public class IntBoard {
	private int MAX_SIZE = 4;
	private Map<BoardCell, Set<BoardCell>> adjacentTileMap = new HashMap<BoardCell, Set<BoardCell>>();
	// private Set<BoardCell> visitedTiles;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> validTargets = new HashSet<BoardCell>();
	public BoardCell a; 
	public BoardCell[][] grid;
	public IntBoard() {
		grid = new BoardCell[MAX_SIZE][MAX_SIZE] ;
		for (int i = 0; i < MAX_SIZE; i++){
			for (int j = 0; j < MAX_SIZE; j++){			
				grid[i][j] =  new BoardCell(i,j);
			}
		}
		calcAdjacencies();
	}
	public void calcAdjacencies(){
		for(int i = 0; i < grid.length; i++) { // i equals columns j = rows 
			for (int j = 0; j < grid[i].length; j++) {
				Set<BoardCell> adjSet = new HashSet<BoardCell>(); // adjacent set for this for loop
				if (i == 0){ // handles top of the board
					adjSet.add(grid[i+1][j]);
					if (j + 1 < (grid[i].length)){
						adjSet.add(grid[i][j+1]);
					}
					if (j-1 >= 0){
						adjSet.add(grid[i][j-1]);
					}
				}
				else if (i == grid.length - 1){ // handles bottom
					adjSet.add(grid[i-1][j]);
					if (j+1 < grid[i].length - 1){
						adjSet.add(grid[i][j+1]);
					}
					if (j-1 >= 0){
						adjSet.add(grid[i][j-1]);
					}

				}
				else {
					adjSet.add(grid[i+1][j]);
					adjSet.add(grid[i-1][j]);
					if (j+1 <= (grid[i].length- 1)){
						adjSet.add(grid[i][j+1]);
					}
					if (j-1 >= 0){
						adjSet.add(grid[i][j-1]);
					}
				}
				adjacentTileMap.put(grid[i][j], adjSet); // sets the adjSet to the corresponding grid space

			}
		}
	}
	public Set<BoardCell> getAdjList(BoardCell cell) {
		Set<BoardCell> adjList = adjacentTileMap.get(cell);                                                                                                                                                                                                                                                                                                                                                   
		return adjList;
	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		Set<BoardCell> hold = adjacentTileMap.get(startCell);
		for (BoardCell s : hold){	
			if (!visited.contains(s)) {
				visited.add(startCell);
				if (pathLength == 1) {
					
					validTargets.addAll(hold);
				}
				else{
					calcTargets(s, pathLength -1);
				}
			}
		}
	}

	public Set<BoardCell> getTargets(){	
		return validTargets;
	}


}
