package ClueGame;
import java.util.*;
import java.io.*;


public class Board {

	public void setConfigFiles(String boardConf, String roomConf) {
		this.boardConf = boardConf;
		this.roomConf  = roomConf;
	}

	private int rows = 0;
	private int cols = 0;
	private int MAX_SIZE = 50;
	private Map<Character, String> cellMap;
	private Map<BoardCell, Set<BoardCell>> adjacentTileMap;
	// private Set<BoardCell> visitedTiles;
	private Set<BoardCell> visited;
	private Set<BoardCell> validTargets;
	private String boardConf;
	private String roomConf;
	private HashMap<Character, String> roomTypes;
	public BoardCell[][] grid;

	private static Board gameBoard;

	public Board() {
		super();
	}

	public static Board getInstance() {
		if (gameBoard == null) {
			gameBoard = new Board();
		}
		return gameBoard;

	}
	public void initialize() {
		roomTypes = new HashMap<Character, String>();
		adjacentTileMap = new HashMap<BoardCell, Set<BoardCell>>();
		validTargets = new HashSet<BoardCell>(); 
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
		}
		catch (BadConfigFormatException c) {
			System.out.println("Bad room config format!");
		}
	}
	@SuppressWarnings("resource")
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		BufferedReader reader = null;
		String line;
		int r = 0;
		int c = 0;
		grid = new BoardCell[MAX_SIZE][MAX_SIZE];
		try {
			reader = new BufferedReader(new FileReader(boardConf));
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split(",");
				cols = splitLine.length;
				for (c = 0; c < splitLine.length; c++) {
					BoardCell cell = new BoardCell(r,c, splitLine[c].toUpperCase());
					if (!roomTypes.containsKey(splitLine[c].toLowerCase().charAt(0)) && (!(splitLine[c].length() <= 2))) {
						throw new BadConfigFormatException("ERROR: Room not found in room config!");
					}
					grid[r][c] = cell;
					System.out.print(cell.getTileType() + " ");
				}
				System.out.println();
				r++;
			}
			rows = r;
		}
		catch (FileNotFoundException f) {
			System.out.println("Board config not found!");
		}
		catch (BadConfigFormatException d) {
			System.out.println("Bad board config format!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calcAdjacencies(){
		for(int i = 0; i < grid.length; i++) { // i equals columns j = rows 
			for (int j = 0; j < grid[i].length; j++) {
				BoardCell firstCell = getCellAt(i,j);
				Set<BoardCell> adjSet = new HashSet<BoardCell>(); // adjacent set for this for loop
				if (i - 1 >= 0){ // handles top of the board
					BoardCell secondCell = getCellAt(i - 1, j);
					String type = secondCell.getTileType();
					if(secondCell.isDoorway()) {
						if (secondCell.getDoorDirection() == DoorDirection.DOWN && type.equals("W")) {
							adjSet.add(secondCell);
						}
						else if (secondCell.getDoorDirection() == DoorDirection.UP && type.equals(firstCell.getTileType())) {
							adjSet.add(secondCell);
						}
					}
					else {
						if (type.equals(firstCell.getTileType()) && firstCell.getTileType().equals("W") ) {
							adjSet.add(secondCell);
						}
					}
				}
				else if (i + 1 <= rows - 1) { // handles bottom of the board
					BoardCell secondCell = getCellAt(i + 1, j);
					String type = secondCell.getTileType();
					if(secondCell.isDoorway()) {
						if (secondCell.getDoorDirection() == DoorDirection.UP && type.equals("W")) {
							adjSet.add(secondCell);
						}
						else if (secondCell.getDoorDirection() == DoorDirection.DOWN && type.equals(firstCell.getTileType())) {
							adjSet.add(secondCell);
						}
					}
					else {
						if (type.equals(firstCell.getTileType()) && firstCell.getTileType().equals("W") ) {
							adjSet.add(secondCell);
						}
					}
				}
				else if (j - 1 >= 0){ // handles left side of the board
					BoardCell secondCell = getCellAt(i, j - 1);
					String type = secondCell.getTileType();
					if(secondCell.isDoorway()) {
						if (secondCell.getDoorDirection() == DoorDirection.RIGHT && type.equals("W")) {
							adjSet.add(secondCell);
						}
						else if (secondCell.getDoorDirection() == DoorDirection.LEFT && type.equals(firstCell.getTileType())) {
							adjSet.add(secondCell);
						}
					}
					else {
						if (type.equals(firstCell.getTileType()) && firstCell.getTileType().equals("W") ) {
							adjSet.add(secondCell);
						}
					}
				}
				else if (j + 1 <= cols - 1) { // handles right side  of the board
					BoardCell secondCell = getCellAt(i, j + 1);
					String type = secondCell.getTileType();
					if(secondCell.isDoorway()) {
						if (secondCell.getDoorDirection() == DoorDirection.UP && type.equals("W")) {
							adjSet.add(secondCell);
						}
						else if (secondCell.getDoorDirection() == DoorDirection.DOWN && type.equals(firstCell.getTileType())) {
							adjSet.add(secondCell);
						}
					}
					else {
						if (type.equals(firstCell.getTileType()) && firstCell.getTileType().equals("W") ) {
							adjSet.add(secondCell);
						}
					}
				}
				adjacentTileMap.put(grid[i][j], adjSet); // sets the adjSet to the corresponding grid space

			}
		}
	}
	public Set<BoardCell> getAdjList(int x, int y) {
		BoardCell cell = new BoardCell();
		cell.row = x;
		cell.col = y;
		Set<BoardCell> adjList = adjacentTileMap.get(cell);                                                                                                                                                                                                                                                                                                                                                   
		return adjList;
	}

	public BoardCell getCellAt(int x, int y) {
		return grid[x][y];
	}

	public void calcTargets(int x, int y, int pathLength) {
		BoardCell startCell = new BoardCell(x,y);
		Set<BoardCell> hold = adjacentTileMap.get(startCell);
		hold.add(startCell);
		if (pathLength < 1) {
			System.out.println("Invalid path!");
			return;
		}
		recursiveCalcTargets(hold, pathLength, startCell); 
		return;
	}
	
	public void recursiveCalcTargets(Set<BoardCell> visited, int pathLength, BoardCell cell) {
		// bottom
		if (adjacentTileMap.get(cell).contains(new BoardCell(cell.row + 1, cell.col))) {
			BoardCell secondCell = getCellAt(cell.row + 1, cell.col);
			if (visited.contains(secondCell)) return;
			visited.add(secondCell);
			if (secondCell.isDoorway()) { 					// stops at doorway, handles walls
				recursiveCalcTargets(visited, 0, secondCell);
			}
			recursiveCalcTargets(visited, pathLength - 1, secondCell);
		}
		// top
		if (adjacentTileMap.get(cell).contains(new BoardCell(cell.row - 1, cell.col))) {
			BoardCell secondCell = getCellAt(cell.row - 1, cell.col);
			if (visited.contains(secondCell)) return;	// skip if visited already contains the new cell
			visited.add(secondCell);
			if (secondCell.isDoorway()) {
				recursiveCalcTargets(visited, 0, secondCell);
			}
			recursiveCalcTargets(visited, pathLength - 1, secondCell);
		}

		// right
		if (adjacentTileMap.get(cell).contains(new BoardCell(cell.row, cell.col + 1))) {
			BoardCell secondCell = getCellAt(cell.row, cell.col + 1);
			if (visited.contains(secondCell)) return;
			visited.add(secondCell);
			if (secondCell.isDoorway()) {
				recursiveCalcTargets(visited, 0, secondCell);
			}
			recursiveCalcTargets(visited, pathLength - 1, secondCell);
		}

		// left
		if (adjacentTileMap.get(cell).contains(new BoardCell(cell.row, cell.col - 1))) {
			BoardCell secondCell = getCellAt(cell.row, cell.col - 1);
			if (visited.contains(secondCell)) return;
			visited.add(secondCell);
			if (secondCell.isDoorway()) {
				recursiveCalcTargets(visited, 0, secondCell);
			}
			recursiveCalcTargets(visited, pathLength - 1, secondCell);
		}
	}

	public Set<BoardCell> getTargets(){	
		return validTargets;
	}


}
