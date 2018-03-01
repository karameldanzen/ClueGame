package tests;
import experiment.BoardCell;
import experiment.IntBoard;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.*;

public class IntBoardTest {
	@Before public void beforeAll() {
		IntBoard board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
	}
	/*
	 * Test adjacencies for top left corner
	 */
	// @Test
	// TODO: figure out JUnit testing
/*	public void testAdjacency0() {
		
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
*/
}