package clueGame;
import java.util.*;
import java.io.*;

/**
 * Board - sets up the game board and calculates adjacent tiles and valid tiles for movement
 * @author Kyle Strayer
 * @author Garrett Van Buskirk
 */
public class Board {
	private int rows = 0;
	private int cols = 0;
	private int MAX_SIZE = 50;
	private Map<BoardCell, HashSet<BoardCell>> adjacentTileMap;
	private HashSet<BoardCell> validTargets;
	private HashSet<BoardCell> visited;
	private String boardConf;
	private String roomConf;
	private Map<Character, String> roomTypes;
	public BoardCell[][] grid; // game grid
	private static Board gameBoard;

	public Board() {
		super();
	}
	public static Board getInstance() { // Singleton method
		if (gameBoard == null) {
			gameBoard = new Board();
		}
		return gameBoard;

	}
	public void setConfigFiles(String boardConf, String roomConf) { 
		this.boardConf = boardConf;
		this.roomConf  = roomConf;
	}
	public void initialize() {			// Initializes the game
		roomTypes = new HashMap<Character, String>();
		adjacentTileMap = new HashMap<BoardCell, HashSet<BoardCell>>();
		validTargets = new HashSet<BoardCell>(); 
		visited = new HashSet<BoardCell>();
		grid = new BoardCell[MAX_SIZE][MAX_SIZE];
		try {
			loadRoomConfig();
			loadBoardConfig();
		}
		catch (FileNotFoundException f) {
			System.out.println("Config not found!");
		}
		catch (BadConfigFormatException c) {
			System.out.println("Bad config format!");
		}
		calcAdjacencies();
	}
	/* LoadRoomConfig()
	 * returns nothing
	 * throws FileNotFoundException, BadConfigFormatException
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		try {
			//	FileInputStream fileIn = null;
			FileReader reader = new FileReader(roomConf);
			@SuppressWarnings("resource")
			Scanner inFile = new Scanner(reader);
			while (inFile.hasNextLine()) {
				String line  = inFile.nextLine();
				String[] splitLine = line.split(", ");
				if (splitLine[0].length() > 1) {
					throw new BadConfigFormatException("Error: Room labels should be one character in length");
				}
				if (!splitLine[2].toLowerCase().equals("card") && !splitLine[2].toLowerCase().equals("other")) {
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
			f = new FileNotFoundException();
			f.printStackTrace();
		}
		catch (BadConfigFormatException c) {
			System.out.println("Bad room config format!");
			c = new BadConfigFormatException();
			c.printStackTrace();
		}
	}
	/* LoadBoardConfig()
	 * returns nothing
	 * throws FileNotFoundException, BadConfigFormatException
	 */
	public void loadBoardConfig() {
		int r = 0, c = 0;
		File file = new File (boardConf);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine(); 
				String[] array = line.split(",");
				cols = array.length;
				for (c = 0; c < cols; c++) {
					if (array[c].length() == 1) {
						char letter = array[c].charAt(0);
						BoardCell cell = new BoardCell(r, c, DoorDirection.NONE, letter);
						grid[r][c] = cell;

						System.out.print(cell.getInitial() + " ");
					}
					if (array[c].length() == 2) {
						char letter = array[c].charAt(0);
						char dir = array[c].charAt(1);
						BoardCell cell = new BoardCell();
						if (dir == 'U' || dir == 'u') cell = new BoardCell(r, c, DoorDirection.UP, letter);
						if (dir == 'D' || dir == 'd') cell = new BoardCell(r, c, DoorDirection.DOWN, letter);
						if (dir == 'N' || dir == 'n') cell = new BoardCell(r, c, DoorDirection.NONE, letter);
						if (dir == 'L' || dir == 'l') cell = new BoardCell(r, c, DoorDirection.LEFT, letter);
						if (dir == 'R' || dir == 'r') cell = new BoardCell(r, c, DoorDirection.RIGHT, letter);
						grid[r][c] = cell;
						System.out.print(cell.getInitial() + " ");
					}
				}
				System.out.println();
				r++;
			}
			rows = r;
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Board config not found!");

		}
		catch (NullPointerException a) 
		{
			BadConfigFormatException b = new BadConfigFormatException(a.getMessage()); 
			System.out.println(b.getMessage());
		}
		finally {
			scan.close();
		}
	}

	/* calcAdjacencies()
	 * calculates adjacent tiles. should take into account room/doors.
	 */
	public void calcAdjacencies() {
		for (int i = 0; i < rows; i++) { 		// i = x
			for (int j = 0; j < cols; j++) {	// j = y
				Set<BoardCell> adj = new HashSet<BoardCell>();
				if (i - 1 >= 0) {
					//checking if it is a walkway possibility
					if (grid[i - 1][j].isWalkway()) {
						adj.add(grid[i - 1][j]);
					}
					//checking if it is a door with the correct direction
					if (grid[i - 1][j].isDoorway() && grid[i - 1][j].getDoorDirection() == DoorDirection.DOWN) {
						adj.add(grid[i - 1][j]);
					}
				}
				if (i + 1 < rows) {
					//checking if it is a walkway possibility
					if (grid[i + 1][j].isWalkway()) {
						adj.add(grid[i + 1][j]);
					}
					//checking if it is a door with the correct direction
					if (grid[i + 1][j].isDoorway() && grid[i + 1][j].getDoorDirection() == DoorDirection.UP) {
						adj.add(grid[i + 1][j]);
					}
				}
				if (j - 1 >= 0) {
					//checking if it is a walkway possibility
					if (grid[i][j - 1].isWalkway()) {
						adj.add(grid[i][j - 1]);
					}
					//checking if it is a door with the correct direction
					if (grid[i][j - 1].isDoorway() && grid[i][j - 1].getDoorDirection() == DoorDirection.RIGHT) {
						adj.add(grid[i][j - 1]);
					}
				}
				if (j + 1 < cols) {
					//checking if it is a walkway possibility
					if (grid[i][j + 1].isWalkway()) {
						adj.add(grid[i][j + 1]);
					}
					//checking if it is a door with the correct direction
					if (grid[i][j + 1].isDoorway() && grid[i][j + 1].getDoorDirection() == DoorDirection.LEFT) {
						adj.add(grid[i][j + 1]);
					}
				}
				adjacentTileMap.put(grid[i][j], new HashSet<BoardCell>(adj));
				adj.clear();
			}
		}
	}
	public HashSet<BoardCell> getAdjList(int x, int y) {
		BoardCell cell = getCellAt(x, y);
		HashSet<BoardCell> adjList = adjacentTileMap.get(cell);                                                                                                                                                                                                                                                                                                                                                   
		return adjList;
	}
	public BoardCell getCellAt(int x, int y) {
		return grid[x][y];
	}
	/* calcTargets()
	 * calculates valid targets
	 * calls find
	 */
	public void calcTargets(int row, int col, int pathLength) { 
		// set visited list to empty
		visited.clear();
		// initially set targets to an empty list
		validTargets.clear();
		// add start location to the visited list
		find(row, col, pathLength, grid[row][col]);
	}
	public void find (int row, int col, int pathLength, BoardCell startCell) {
		Set<BoardCell> adjCell = new HashSet<BoardCell>();
		adjCell = adjacentTileMap.get(grid[row][col]);
		if (validTargets.contains(startCell)) {
			validTargets.remove(startCell);
		}
		if (pathLength % 2 != 0) {
			validTargets.addAll(adjCell);
		}
		if (pathLength > 0) {
			for (BoardCell test : adjCell) {
				if (test.isDoorway()) {
					validTargets.add(test);
				}
				find(test.row, test.col, pathLength - 1, startCell);
			}
		}
		
	/*
	 	if (adjacentTileMap.containsKey(grid[row][col])) {		
			for (BoardCell test : adjCell) {
				System.out.println(test.col + ", " + test.row);
				if (test.isDoorway() && !visited.contains(test)) {
					visited.add(test);
				}
				if (visited.contains(test)) {
					continue; 
				}
				else {
					visited.add(test);
				}
				if (pathLength == 1) {
					validTargets.add(test);
				}
				else {
					find(test.col, test.row, pathLength - 1);
				}
			}
		}
		else {
			System.out.println("Not found in adjacent tile map");
		}
	 	*/
	}
	public HashSet<BoardCell> getTargets(){	
		return validTargets;
	}
	public Map<Character, String> getLegend(){	
		return roomTypes;
	}
	public int getNumRows(){	
		return rows;
	}
	public int getNumColumns(){	
		return cols;
	}
}
