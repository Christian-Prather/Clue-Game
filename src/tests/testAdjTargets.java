package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import experiment.BoardCell;

public class testAdjTargets 
{
	
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Clue_Board_Layout_Langfield_Prather.csv", "LegendForClueLayout.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	//Check that adjacencies for walkway are only on walkway unless
	// door that is enterable
	@Test
	public void testWalkwayAdj() 
	{
		Set<clueGame.BoardCell> cells = board.getAdjList(13, 15);
		for (clueGame.BoardCell tempCell : cells)
		{
			assertFalse(tempCell.isDoorway());
		}
		assertTrue(cells.contains(board.getCellAt(14, 15)));
	}
	
	// Test that in room there should be no adjacencies
	@Test
	public void testAdjInRoom() 
	{
		Set<clueGame.BoardCell> cells = board.getAdjList(14, 19);
		assertTrue(cells.isEmpty());

	}
	// Check cant go over board
	@Test 
	public void testEdgeOfBoard()
	{
		Set<clueGame.BoardCell> cells = board.getAdjList(20, 22);
		assertFalse(cells.contains(board.getCellAt(40, 40)));
		assertFalse(cells.contains(board.getCellAt(20, 23)));
		
		cells = board.getAdjList(0, 9);
		assertFalse(cells.contains(board.getCellAt(40, 40)));
		assertFalse(cells.contains(board.getCellAt(-1, 9)));

	}
	
	//Check that we cant go into a room without a doorway
	@Test
	public void testNotDoorway()
	{
		Set<clueGame.BoardCell> cells = board.getAdjList(3, 5);
		assertFalse(cells.contains(board.getCellAt(3, 4)));
		
		cells = board.getAdjList(3, 13);
		assertFalse(cells.contains(board.getCellAt(3, 14)));

	}
	
	// Check that we can go into doorway
	@Test 
	public void testEnterDoorAdj()
	{
		// Up
		Set<clueGame.BoardCell> cells = board.getAdjList(11, 18);
		assertTrue(cells.contains(board.getCellAt(12,18)));
		
		//Down
		cells = board.getAdjList(5, 11);
		assertTrue(cells.contains(board.getCellAt(4,11)));
		
		//Right
		cells = board.getAdjList(18, 4);
		assertTrue(cells.contains(board.getCellAt(18,3)));
		
		//Left
		cells = board.getAdjList(20, 6);
		assertTrue(cells.contains(board.getCellAt(20, 7)));
	
	}
	
	// Check locations that are doorways should only let you in to room
	@Test
	public void testDoorAdj()
	{
		Set<clueGame.BoardCell> cells = board.getAdjList(4, 11);
		assertTrue(cells.contains(board.getCellAt(5,11)));
		assertEquals(cells.size(), 1);
		
		cells = board.getAdjList(6, 19);
		assertTrue(cells.contains(board.getCellAt(7,19)));
		assertEquals(cells.size(), 1);

	}
	
	// Check targets along walkways
	@Test
	public void testWalkwayTargets()
	{
		board.calcTargets(15, 9, 2);
		Set<clueGame.BoardCell> cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(16, 9)));
		assertFalse(cells.contains(board.getCellAt(17,9)));
		
		board.calcTargets(5, 13, 2);
		cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(7, 13)));
		assertFalse(cells.contains(board.getCellAt(5,15)));
		
		board.calcTargets(9, 19, 4);
		cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(9, 15)));
		assertFalse(cells.contains(board.getCellAt(13,19)));
		
		board.calcTargets(3, 5, 2);
		cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(5, 5)));
		assertFalse(cells.contains(board.getCellAt(3,3)));

	}
	
	// Test that the user can enter a room with target
	@Test
	public void testEnterRoomTarget()
	{
		board.calcTargets(8, 4, 3);
		Set<clueGame.BoardCell> cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(8, 3)));
		assertEquals(cells.size(), 10);
		
		board.calcTargets(9, 16, 1);
		cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(8, 16)));
		assertEquals(cells.size(), 14);

	}
	
	// Check targets when leaving room
	@Test
	public void testLeaveRoomTargets()
	{
		board.calcTargets(14, 5, 1);
		Set<clueGame.BoardCell> cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(14, 6)));
		
		board.calcTargets(4, 11, 2);
		cells = board.getTargets();
		assertTrue(cells.contains(board.getCellAt(6, 11)));
		
	}


}
