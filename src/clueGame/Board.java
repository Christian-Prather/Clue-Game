package clueGame;

import java.util.Map;
import java.util.Set;

public class Board 
{
	private int numRows;
	private int numColumns;
	public int MAX_BOARD_SIZE = 50;
	private BoardCell board[][];
	private Map<Character, String> legend;
	private String legendConfig;
	private Map<BoardCell, Set<BoardCell>> adjjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	//private String roomConfigFile;
	private static Board theInstance = new Board();

	
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() 
	{
		return theInstance;
	}
	
	public void initalize()
	{
		
		
	}
	public void loadRoomConfig()
	{
		
	}
	public void loadBoardConfig()
	{
		
	}
	public void calcAdjacencies()
	{
		
	}
	public void calcTargets(BoardCell cell, int pathLength)
	{
		
	}
	public void setConfigs(String boardConfig, String legendConfig)
	{
		this.boardConfigFile = boardConfig;
		this.legendConfig = legendConfig;
		
	}
	public Map<Character, String> getLegend()
	{
		return legend;
	}
	public BoardCell getCellAt(int column, int row)
	{
		return board[column][row];
	}
	
	public int getNumberOfRows()
	{
		return numRows;
	}
	public int getNumberOfColumns()
	{
		return numColumns;
	}
	
	public Character getInitail()
	{
		return null;
	}

}
