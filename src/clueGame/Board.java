package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
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
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
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
		calcAdjacencies();

		
		
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
					tempCell.initial = elements[column].charAt(0);

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
					
				
					// Check for walkway
				
					else if(tempCell.initial == walkwayKey)
					{
						tempCell.walkway = true;
					}
					else
					{
						tempCell.room = true;
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
		System.out.println(board.length + " " + board[0].length);
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
	//			System.out.print(board[i][j].initial + " ");
				//BoardCell keyCell = getCell(grid[i][j]);
				Set<BoardCell> adjacencies = new HashSet<BoardCell>();
				System.out.println("GRID: " + board[i][j].row + " " + board[i][j].column);
				
				if (board[i][j].isDoorway())
				{
					if (board[i][j].doorDirection == DoorDirection.UP)
					{
						adjacencies.add(board[i -1][j]);

					}
				
					
					else if (board[i][j].doorDirection == DoorDirection.LEFT)
					{
						adjacencies.add(board[i][j-1]);

					}
					else if (board[i][j].doorDirection == DoorDirection.RIGHT)
					{
						adjacencies.add(board[i][j+1]);
					}
					else if (board[i][j].doorDirection == DoorDirection.DOWN)
					{
						adjacencies.add(board[i + 1][j]);

					}
					adjMatrix.put(board[i][j], adjacencies);
					System.out.println("ADJ: " + adjacencies);
					
				}
				///////////////////////////////////Not Doorway///////////////////////////////////////////////
				else
				{
					if (board[i][j].row != 0)
					{
						//BoardCell tempCell =  getCell(grid[i][j].column -1, grid[i][j].row)
						//Handle the in room adjacencies ignore
						if (!board[i-1][j].isRoom())
						{
							adjacencies.add(board[i -1][j]);
						}
					}
					if (board[i][j].column != 0)
					{
						if (!board[i][j-1].isRoom())
						{
							adjacencies.add(board[i][j-1]);
						}

					}
					if (board[i][j].column != board[0].length -1)
					{
						if(!board[i][j+1].isRoom())
						{
							adjacencies.add(board[i][j+1]);
						}
					}
					if (board[i][j].row != board.length -1)
					{
						if (!board[i+1][j].isRoom())
						{
							adjacencies.add(board[i + 1][j]);
						}
					}
					adjMatrix.put(board[i][j], adjacencies);
					System.out.println("ADJ: " + adjacencies);
				}
				
	
			}
		}
	}
	public void calcTargets(int row, int column, int pathLength)
	{
		
	}
	public Set<BoardCell> getAdjList(int row, int column)
	{
		Set<BoardCell> adjCells = adjMatrix.get(board[row][column]);
		System.out.println("ADJ:" + adjCells);
		return adjCells;
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
