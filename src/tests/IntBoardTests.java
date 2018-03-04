package tests;
import static org.junit.Assert.*;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {

	IntBoard board;


@ Before 
public void beforeAll() {
  board = new IntBoard();// constructor should call calcAdjacencies() so you can test them
  board.calcAdjacencies();
}

@Test
public void testAdjacency0()
{
	BoardCell cell = board.grid[0][0];
	Set<BoardCell> testList = board.getAdjList(cell);
	assertTrue(testList.contains(board.grid[1][0]));
	assertTrue(testList.contains(board.grid[1][0]));
	assertEquals(2, testList.size());
}
/*
@Test
public void testTargets0_3()
{
	BoardCell cell = board.grid[0][0]; 
	board.calcTargets(cell, 3);
	Set <BoardCell> targets = board.getTargets();
	assertEquals(6, targets.size());
	assertTrue(targets.contains(board.getCell(3, 0)));
	assertTrue(targets.contains(board.getCell(2, 1)));
	assertTrue(targets.contains(board.getCell(0, 1)));
	assertTrue(targets.contains(board.getCell(1, 2)));
	assertTrue(targets.contains(board.getCell(0, 3)));
	assertTrue(targets.contains(board.getCell(1, 0)));
}


*/







}