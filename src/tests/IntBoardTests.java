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
 
}

@Test
public void testAdjacency0()
{
	BoardCell cell = board.grid[1][1];
	Set<BoardCell> testList = board.getAdjList(cell);
	assertTrue(testList.contains(board.grid[0][1]));
	assertTrue(testList.contains(board.grid[1][0]));
	assertTrue(testList.contains(board.grid[2][1]));
	assertTrue(testList.contains(board.grid[1][2]));
	assertEquals(4, testList.size());
	BoardCell cell1 = board.grid[0][0];
	Set<BoardCell> testList1 = board.getAdjList(cell1);
	assertTrue(testList1.contains(board.grid[0][1]));
	assertTrue(testList1.contains(board.grid[1][0]));
	assertEquals(2, testList1.size());
	BoardCell cell2 = board.grid[2][2];
	Set<BoardCell> testList2 = board.getAdjList(cell2);
	assertTrue(testList2.contains(board.grid[2][1]));
	assertTrue(testList2.contains(board.grid[2][3]));
	assertTrue(testList2.contains(board.grid[3][2]));
	assertTrue(testList2.contains(board.grid[1][2]));
	assertEquals(4, testList2.size());
	BoardCell cell3 = board.grid[3][3];
	Set<BoardCell> testList3 = board.getAdjList(cell3);
	assertTrue(testList3.contains(board.grid[3][2]));
	assertTrue(testList3.contains(board.grid[2][3]));
	assertEquals(2, testList3.size());
	BoardCell cell4 = board.grid[1][3];
	Set<BoardCell> testList4 = board.getAdjList(cell4);
	assertTrue(testList4.contains(board.grid[2][3]));
	assertTrue(testList4.contains(board.grid[0][3]));
	assertTrue(testList4.contains(board.grid[1][2]));
	assertEquals(3, testList4.size());
	BoardCell cell5 = board.grid[3][0];
	Set<BoardCell> testList5 = board.getAdjList(cell5);
	assertTrue(testList5.contains(board.grid[2][0]));
	assertTrue(testList5.contains(board.grid[3][1]));
	assertEquals(2, testList5.size());
	BoardCell cell6 = board.grid[2][0];
	Set<BoardCell> testList6 = board.getAdjList(cell6);
	assertTrue(testList6.contains(board.grid[3][0]));
}


@Test
public void testTargets0_3(){
	BoardCell cell = board.grid[0][0]; 
	board.calcTargets(cell, 3);
	Set <BoardCell> targets = board.getTargets();
	int temp = targets.size();
	
	assertTrue(targets.contains(board.grid[1][0]));
	assertTrue(targets.contains(board.grid[0][1]));
	assertTrue(targets.contains(board.grid[2][1]));
//	assertTrue(targets.contains(board.grid[1][1]));
//	assertTrue(targets.contains(board.grid[2][0]));
//	assertTrue(targets.contains(board.grid[0][2]));
	assertTrue(targets.contains(board.grid[1][2]));
	assertTrue(targets.contains(board.grid[3][0]));
	assertTrue(targets.contains(board.grid[0][3]));

	assertEquals(6, temp);
}









}