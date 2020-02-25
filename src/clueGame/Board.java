package clueGame;

import java.util.Map;
import java.util.Set;

public class Board 
{
	private int numRows;
	private int numColumns;
	public int MAX_BOARD_SIZE = 50;
	private Board boardCell[][];
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
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
	public void setConfigs(String boardConfig, String legend)
	{
		this.boardConfigFile = boardConfig;
		this.legend = legend;
		
	}

}
