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
		board.setConfigFiles("Clue_Board_Layout_Langfield_Prather.csv", "LegendForClueLayout.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		
	}

	@Test
	public void testRooms()
	{
		
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		
		assertEquals("Kindnessroom", legend.get('K'));
		assertEquals("RepositoryRoom", legend.get('R'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Observatory", legend.get('O'));
		assertEquals("Jumping Jack Room", legend.get('J'));
		assertEquals("Punching Room", legend.get('P'));
		assertEquals("Gaming Room", legend.get('G'));
		assertEquals("Xylophone Room", legend.get('X'));
		assertEquals("Sexy Room", legend.get('S'));
		
		assertEquals("Walkway", legend.get('Y'));
		assertEquals("Closet", legend.get('Q'));

	}
	@Test
	public void testBoardDimensions()
	{
		// We may have an index error off by one
		clueGame.BoardCell room = board.getCellAt(8,3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		


		room = board.getCellAt(12,18);

		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		room = board.getCellAt(20,7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(18,3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(1,0);
		assertTrue(room.isWalkway());
		
	}
	
	@Test
	public void testNumberOfDoors()
	{
		int numDoors = 0;

		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				clueGame.BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(10, numDoors);
	}

	@Test 
	public void testRoomInitials()
	{
		assertEquals((Character)'Y', board.getCellAt(14, 0).getInitial());
		assertEquals((Character)'Y', board.getCellAt(14, 6).getInitial());
		
		assertEquals((Character)'Y', board.getCellAt(9, 20).getInitial());
		assertEquals((Character)'X', board.getCellAt(17, 20).getInitial());


	}



	private Object character(Character initial) {
		// TODO Auto-generated method stub
		return null;
	}

}
