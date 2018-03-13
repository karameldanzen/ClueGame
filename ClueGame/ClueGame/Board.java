package ClueGame;
import java.util.*;
import java.io.*;


public class Board {

	public void setConfigFiles(String boardConf, String roomConf) {
		this.boardConf = boardConf;
		this.roomConf  = roomConf;
	}

	private int rows;
	private int cols;
	private int MAX_SIZE = 50;
	private Map<Character, String> cellMap;
	private Map<BoardCell, Set<BoardCell>> adjacentTileMap = new HashMap<BoardCell, Set<BoardCell>>();
	// private Set<BoardCell> visitedTiles;
	private Set<BoardCell> visited;
	private Set<BoardCell> validTargets = new HashSet<BoardCell>(); 
	private String boardConf;
	private String roomConf;
	public BoardCell[][] grid;

	private static Board gameBoard = new Board();

	public Board() {
		super();
	}

	public static Board getInstance() {
		return gameBoard;
	}

	public void loadRoomConfig() {
		File roomConfig = new File(roomConf);
	//	FileInputStream fileIn = null;
		BufferedReader reader = null;
		String line = "";
		String csvSplit = ",";
		try {
			reader = new BufferedReader(new FileReader(roomConfig));
			while ((line = reader.readLine()) != null) {
				String[] tile = line.split(csvSplit);
				cols = tile.length;
				System.out.print("Row: ");
				for (String s : tile) {
					System.out.print(s);
				}
				System.out.println();
			}
		}
		catch (FileNotFoundException f) {
			System.out.println("Room config not found!");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadBoardConfig() {
		File boardConfig = new File(boardConf);
		//	FileInputStream fileIn = null;
		BufferedReader reader = null;
		String line = "";
		String csvSplit = ",";
		try {
			reader = new BufferedReader(new FileReader(boardConfig));
			while ((line = reader.readLine()) != null) {
				String[] tile = line.split(csvSplit);
				cols = tile.length;
				System.out.print("Row: ");
				for (String s : tile) {
					System.out.print(s);
				}
				System.out.println();
			}
		}
		catch (FileNotFoundException f) {
			System.out.println("Board config not found!");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void initializeBoard() {
		loadRoomConfig();
		loadBoardConfig();
		calcAdjacencies();
		visited = new HashSet<BoardCell>();
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
