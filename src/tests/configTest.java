package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.DoorDirection;
import experiment.BoardCell;

public class configTest 
{
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 23;

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigs("C12A1ClueLayOut_Langfield_Prather/Clue_Board_Layout_Langfield_Prather.csv", "C12A1ClueLayOut_Langfield_Prather/LegendForClueLayout.txt");		
		// Initialize will load BOTH config files 
		board.initalize();
		
	}

	@Test
	public void testRooms()
	{
		
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		
		assertEquals("Kindnessroom", legend.get('K'));
		assertEquals("Repositoryroom", legend.get('R'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Observatory", legend.get('O'));
		assertEquals("Jumping Jack Room", legend.get('J'));
		assertEquals("Punching Room", legend.get('P'));
		assertEquals("Gaming Room", legend.get('G'));
		assertEquals("Xylophone", legend.get('X'));
		assertEquals("Sexy Room", legend.get('S'));
		
		assertEquals("Walkway", legend.get('W'));
		assertEquals("Closet", legend.get('Q'));

	}
	@Test
	public void testBoardDimensions()
	{
		// We may have an index error off by one
		clueGame.BoardCell room = board.getCellAt(3,9);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		
		room = board.getCellAt(18,12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		room = board.getCellAt(7,21);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(3,19);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(0,1);
		assertTrue(room.isWalkway());
		
	}
	
	@Test
	public void testNumberOfDoors()
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumberOfRows(); row++)
			for (int col=0; col<board.getNumberOfColumns(); col++) {
				clueGame.BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(10, numDoors);
	}
	
//	@Test 
//	public void testRoomInitials()
//	{
//		assertEquals('R', board.getCellAt(0, 14).getInitial());
//		assertEquals('R', board.getCellAt(6, 14).getInitial());
//		
//		assertEquals('R', board.getCellAt(20, 9).getInitial());
//		assertEquals('R', board.getCellAt(20, 10).getInitial());
//
//
//	}

}
