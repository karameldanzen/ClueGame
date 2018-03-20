package ClueGame;
import java.util.*;
import java.io.*;


public class Board {

	public void setConfigFiles(String boardConf, String roomConf) {
		this.boardConf = boardConf;
		this.roomConf  = roomConf;
	}

	private int rows = 23;
	private int cols = 25;
	private int MAX_SIZE = 50;
	private Map<Character, String> cellMap;
	private Map<BoardCell, Set<BoardCell>> adjacentTileMap = new HashMap<BoardCell, Set<BoardCell>>();
	// private Set<BoardCell> visitedTiles;
	private Set<BoardCell> visited;
	private Set<BoardCell> validTargets = new HashSet<BoardCell>(); 
	private String boardConf;
	private String roomConf;
	private HashMap<Character, String> roomTypes;
	public BoardCell[][] grid;

	private static Board gameBoard = new Board();

	public Board() {
		super();
	}

	public static Board getInstance() {
		return gameBoard;
	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		try {
			//	FileInputStream fileIn = null;
			FileReader reader = new FileReader(roomConf);
			Scanner inFile = new Scanner(reader);
			while (inFile.hasNextLine()) {
				String line  = inFile.nextLine();
				String[] splitLine = line.split(",");
				if (splitLine[0].length() > 1) {
					throw new BadConfigFormatException("Error: Room labels should be one character in length");
				}
				if (splitLine[2].toLowerCase() != "card" || splitLine[2].toLowerCase() != "other") {
					throw new BadConfigFormatException("Error: Room type should be Card or Other");
				}
				if (splitLine[0] == null || splitLine[1] == null || splitLine[2] == null) {
					throw new BadConfigFormatException("Error: There should be no null values");
				}
				roomTypes.put(splitLine[0].toUpperCase().charAt(0),splitLine[1]);
			}
		}
		catch (FileNotFoundException f) {
			System.out.println("Room config not found!");
		}
		catch (BadConfigFormatException c) {
			System.out.println("Bad room config format!");
		}
	}
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		BufferedReader reader = null;
		String line;
		String split = ",";
		int row = 0;
		int col = 0;
		try {
			reader = new BufferedReader(new FileReader(boardConf));
			while ((line = reader.readLine()) != null) {
				
			}
		}
		catch (FileNotFoundException f) {
			System.out.println("Board config not found!");
		}
		catch (BadConfigFormatException c) {
			System.out.println("Bad board config format!");
		} catch (IOException e) {
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
	public Set<BoardCell> getAdjList(int x, int y) {
		BoardCell cell = new BoardCell(x,y);
		Set<BoardCell> adjList = adjacentTileMap.get(cell);                                                                                                                                                                                                                                                                                                                                                   
		return adjList;
	}

	public BoardCell getCellAt(int x, int y) {
		return grid[x][y];
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
