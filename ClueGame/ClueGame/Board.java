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
	private HashMap<Character, String> roomTypes = new HashMap<Character, String>(); 
	private String[] validRoomTypes = {"Card", "Other"}; 
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
		grid = new BoardCell[23][25];
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
					System.out.print(cell.tileType + " ");
				}
				System.out.println();
				r++;
			}
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


	public void initializeBoard() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}
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
		BoardCell cell = new BoardCell();
		cell.row = x;
		cell.col = y;
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
