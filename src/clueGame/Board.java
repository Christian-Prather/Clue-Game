package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board 
{
	private int numRows;
	private int numColumns;
	public int MAX_BOARD_SIZE = 50;
	private BoardCell board[][];
	private Map<Character, String> legend = new HashMap<Character, String>();
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
	
	public void initialize()
	{
	//	System.out.println("Initialize..");
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void loadRoomConfig() throws BadConfigFormatException
	{
	//	System.out.println("In room config");
		try {
			File file = new File(legendConfig);
			Scanner scanner = new Scanner(file);
			
			
	//		System.out.println("Loaded");
			
			
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] elements = line.split(",");
				
				
					Character tempChar = elements[0].charAt(0);
					String tempRoomName = elements[1];
					String tempType = elements[2].trim();
//					System.out.println(tempType);
				
					if (!tempType.contentEquals("Card"))
					{
						if (!tempType.contentEquals("Other"))
						{
			//				System.out.println(tempType + "testing  ");
							throw new BadConfigFormatException();
	
						}
					}
					
					legend.put(tempChar, tempRoomName.trim());
		//			System.out.println(tempChar + " " + tempRoomName.trim() + " " + tempType.trim());
					
			
			}
		//	System.out.print("Out");
			scanner.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		//	System.out.print("Cant open legend File");
		}
	}
	public void loadBoardConfig() throws BadConfigFormatException
	{
		Character walkwayKey = 'w';
		
	//	System.out.println("In Board config");
		try {
			File file = new File(boardConfigFile);
			Scanner scanner = new Scanner(file);
			
			
	//		System.out.println("Loaded");
			for (Map.Entry<Character, String> entry : legend.entrySet())
			{
				if(entry.getValue().equals("Walkway"))
				{
					walkwayKey = entry.getKey();
					//System.out.println("Walkway");
				}
			}
			
			
			//Get the dimensions (this is a dumb way of doing this but quick
			int oldColumns = 0; 
			boolean notFirstRun = false;
			while (scanner.hasNextLine())
			{
				numRows++;
				String line = scanner.nextLine();
				String[] elements = line.split(",");
				numColumns = elements.length;
				if (numColumns != oldColumns && notFirstRun)
				{
				//	throw new BadConfigFormatException();
				}
				notFirstRun = true;

			}
			scanner.close();
			scanner = new Scanner(file);
			board = new BoardCell[numRows][numColumns];
			////////////////////////////////////////////////////////////////////
			int row = 0;
			
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] elements = line.split(",");
				int column = 0;
				while (column < elements.length)
				{
					BoardCell tempCell = new BoardCell(row, column);
					if (elements[column].length() > 1)
					{
						// Multi Character spot (door)
						tempCell.doorway = true;
						switch (elements[column].charAt(1))
						{
						case 'U':
							tempCell.doorDirection = DoorDirection.UP;
							break;
						case 'D':
							tempCell.doorDirection = DoorDirection.DOWN;
							break;
						case 'L':
							tempCell.doorDirection = DoorDirection.LEFT;
							break;
						case 'R':
							tempCell.doorDirection = DoorDirection.RIGHT;
							break;
						case 'N':
							tempCell.doorDirection = DoorDirection.NONE;
							break;
						};
			//			System.out.println("Row "+ row + "Col: "+ column);
						
					}
					
				
					tempCell.initial = elements[column].charAt(0);
					// Check for walkway
				
					if(tempCell.initial == walkwayKey)
					{
						tempCell.walkway = true;
					}

					board[row][column] = tempCell;
					column++;	
				}
				row++;

			}
		//	System.out.print("Out");
			scanner.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
	//		System.out.print("Cant open legend File");
			e.printStackTrace();
		}
				
	}
	public void calcAdjacencies()
	{
		
	}
	public void calcTargets(int row, int column, int pathLength)
	{
		
	}
	public Set<BoardCell> getAdjList(int row, int column)
	{
		return null;
	}
	public Set<BoardCell> getTargets()
	{
		return null;
	}
	
	public void setConfigFiles(String boardConfig, String legendConfig)
	{
		this.boardConfigFile = boardConfig;
		this.legendConfig = legendConfig;
		
	}
	public Map<Character, String> getLegend()
	{
		return legend;
	}
	public BoardCell getCellAt(int row, int column)
	{
		return board[row][column];
	}
	
	public int getNumRows()
	{
		return numRows;
	}
	public int getNumColumns()
	{
		return numColumns;
	}

}
