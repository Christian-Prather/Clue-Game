package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.awt.Color; // BE SURE TO USE THIS IMPORT
// not the one Eclipse suggests
import java.lang.reflect.Field;

import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Player;



public class Board 
{
	// Initalize singlton variables to be used for all methods
	private int numRows;
	private int numColumns;
	public int MAX_BOARD_SIZE = 50;
	private BoardCell board[][];
	private Map<Character, String> legend = new HashMap<Character, String>();
	private String legendConfig;
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	// For the cards
	public ArrayList<Player> suspects = new ArrayList<Player>();

	public ArrayList<Card> deck = new ArrayList<Card>();
	public ArrayList<String> rooms = new ArrayList<String>();
	public Map<Player, ArrayList<Card>> playerCards = new HashMap<Player, ArrayList<Card>>();

	private Card theCard;


	private String boardConfigFile;
	private static Board theInstance = new Board(); // Singlton instance
	boolean firstRun = true; // Variable used in calcTargets
	
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() 
	{
		return theInstance;
	}

	// Load room csv and the legend
	public void initialize()
	{
		try 
		{
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException e) 
		{
			e.printStackTrace();
		} 
		calcAdjacencies();
	
	}

	public void loadRoomConfig() throws BadConfigFormatException
	{
		// Check for bad file
		try {
			File file = new File(legendConfig);
			Scanner scanner = new Scanner(file);
			// Grab every line of the room config (legend) one at a time
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				// Split by every comma
				String[] elements = line.split(",");
				// Extract the 3 elements from the line
				Character closetOrWalkwayChar = elements[0].charAt(0);
				String closetOrWalkwayLegend = elements[1];
				String objectType = elements[2].trim();
				// Only two possible types
				if (!objectType.contentEquals("Card"))
				{
					if (!objectType.contentEquals("Other"))
					{
						throw new BadConfigFormatException();
					}
				}
				if (objectType.equals("Card"))
				{
					rooms.add(elements[1]);
				}
				legend.put(closetOrWalkwayChar, closetOrWalkwayLegend.trim());
			}
			scanner.close();

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	public void loadBoardConfig() throws BadConfigFormatException
	{
		// Set default walkway key for our room
		Character walkwayKey = 'w';
		File file;
		Scanner scanner;
		///file not found exception
		try 
		{
			 file = new File(boardConfigFile);
			 scanner = new Scanner(file);
			for (Map.Entry<Character, String> legendCharToRoomMap : legend.entrySet())
			{
				if(legendCharToRoomMap.getValue().equals("Walkway"))
				{
					walkwayKey = legendCharToRoomMap.getKey();
				}
			}
			// Get the dimensions (this is a dumb way of doing this but quick
			// Check that there are consistent column numbers
			int oldColumns = 0; 
			boolean notFirstRun = false;
			board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
			int row = 0;
			while (scanner.hasNextLine())
			{
				numRows++;
				String line = scanner.nextLine();
				String[] elements = line.split(",");
				numColumns = elements.length;
				if (numColumns != oldColumns && notFirstRun)
				{
					throw new BadConfigFormatException();
				}
				notFirstRun = true;
				oldColumns = numColumns;
				int column = 0;
				while (column < elements.length)
				{
					BoardCell tempCell = new BoardCell(row, column);
					
					tempCell.initial = elements[column].charAt(0);
						// Checking that the inital parsed from the board is actually in the legend
						boolean match = false;
						for (Character key : legend.keySet())
						{
							if(tempCell.initial ==  key)
							{
								match = true;
							}
						} if (!match)
						{
							throw new BadConfigFormatException();
						}

					// This cell is a door
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
					}
					// Cell is a walkway		
					else if(tempCell.initial == walkwayKey)
					{
						tempCell.walkway = true;
					}
					// Cell is a room
					else
					{
						tempCell.room = true;
					}
					// Add cell to board matrix
					board[row][column] = tempCell;
					column++;	
				}
				row++;
			}
			scanner.close();
		}catch(FileNotFoundException e){
				e.printStackTrace();
			}

} 
				

	public void calcAdjacencies()
	{
		System.out.println(numRows + " " + numColumns);
		// Loop through every row
		for (int i = 0; i < numRows; i++)
		{
			// Loop through each column
			for (int j = 0; j < numColumns; j++)
			{
			
				// List of adjacencies for a cell
				Set<BoardCell> adjacencies = new HashSet<BoardCell>();

				// Is the cell a doorway (has special rules)
				if (board[i][j].isDoorway())
				{
					// If door direction is up only allow bottom cell to be added
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
					for (BoardCell adjacent : adjacencies)
					{
						System.out.print("Adj: " + adjacent.row + " " + adjacent.column + " ");
						
					}
					System.out.println();
				}
				///////////////////////////////////Not Doorway///////////////////////////////////////////////
				// Cell not a door or room
				else if(!board[i][j].isRoom())
				{
					// Edge case
					if (board[i][j].row != 0)
					{
						//Handle the in room adjacencies ignore
						if (board[i-1][j].isDoorway()) 
						{
							// Need to check that its enetered from correct direction
							if (board[i-1][j].doorDirection == DoorDirection.DOWN)
							{
								adjacencies.add(board[i -1][j]);
							}
						}
						else if (!board[i-1][j].isRoom())
						{
							adjacencies.add(board[i -1][j]);
						}
					}
					// Edge Case
					if (board[i][j].column != 0)
					{
						if (board[i][j-1].isDoorway())
						{
							if (board[i][j-1].doorDirection == DoorDirection.RIGHT)
							{
								adjacencies.add(board[i][j-1]);
							}
						}else if(!board[i][j-1].isRoom())
						{
							adjacencies.add(board[i][j-1]);
						}
					}
					
					// Edge Case
					if (board[i][j].column != numColumns -1)
					{
						if (board[i][j+1].isDoorway())
						{					
							if (board[i][j+1].doorDirection == DoorDirection.LEFT)
							{
								adjacencies.add(board[i][j+1]);
							}
						}else if(!board[i][j+1].isRoom())
						{
							adjacencies.add(board[i][j+1]);
						}
					}
					// Edge Case
					if (board[i][j].row != numRows -1)
					{
						if (board[i+1][j].isDoorway())
						{
							if ((board[i+1][j].doorDirection == DoorDirection.UP))
							{
								adjacencies.add(board[i + 1][j]);
							}
						}
						else if (!board[i+1][j].isRoom())
						{
							adjacencies.add(board[i + 1][j]);
						}
					}
					// Add (cell, adjacencies) to global adjacencie matrix
					adjMatrix.put(board[i][j], adjacencies);
					// Debug Printing
					// for (BoardCell adjacent : adjacencies)
					// {
					// 	System.out.print("Adj: " + adjacent.row + " " + adjacent.column + " ");
					// }
					// System.out.println();
				}
			}
		}
	}
	
	public void calcTargets(int row, int column, int pathLength)
	{
		// Reset from last cell
		if (firstRun)
		{
			targets.clear();
			visited.clear();
			firstRun = false;
		}
		visited.add(board[row][column]);
		Set<BoardCell> adjCells = getAdjList(row, column);
		
		for(BoardCell eachCell : adjCells) {
			// Have we never seen this cell before
			if(!visited.contains(eachCell))
			{
				visited.add(eachCell);
				if(pathLength == 1 || eachCell.isDoorway())
				{
					// Add call as potential target
					targets.add(eachCell);
				}
				else
				{
					calcTargets(eachCell.row, eachCell.column, pathLength -1);
				}
				visited.remove(eachCell);
			}
		}
// 		Leave me for future debugging
//		System.out.print("Cell Path "+ pathLength +" " + row + " " + column + "| " + "Lenght " + targets.size());
//		for (BoardCell target : targets)
//		{
//			System.out.print("( " + target.row + " " + target.column + " )");
//
//		}
//		System.out.println();
	}
	// Return the list of adjacencies 
	public Set<BoardCell> getAdjList(int row, int column)
	{
		Set<BoardCell> adjCells = adjMatrix.get(board[row][column]);
		if (adjCells == null)
		{
			return new HashSet<BoardCell>();
		}
		return adjCells;
	}
	// Return the list of Targets
	public Set<BoardCell> getTargets()
	{
		firstRun = true;
		return targets;
	}
	
	// Setter for the configs
	public void setConfigFiles(String boardConfig, String legendConfig)
	{
		this.boardConfigFile = boardConfig;
		this.legendConfig = legendConfig;
	}
	// Getter for the legend
	public Map<Character, String> getLegend()
	{
		return legend;
	}
	// Helper to get the cell
	public BoardCell getCellAt(int row, int column)
	{
		return board[row][column];
	}
	// Helper for rows
	public int getNumRows()
	{
		return numRows;
	}
	// Helper for the columns
	public int getNumColumns()
	{
		return numColumns;
	}
	
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
	 Color color;
	 try {
	 // We can use reflection to convert the string to a color
	 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
	 color = (Color)field.get(null);
	 } catch (Exception e) {
	 color = null; // Not defined
	 }
	 return color;
	}
	
	public void loadPersonConfig() throws BadConfigFormatException
	{
		File file;
		Scanner scanner;
	
		try
		{
			file = new File("suspects.txt");
			scanner = new Scanner(file);
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] elements = line.split(",");

					// Make temp person object human or computer?
				if (elements[4].equals("human"))
				{
					Player tempPlayer = new HumanPlayer();
					tempPlayer.setPlayerName(elements[0]);
					tempPlayer.setColor(convertColor(elements[1]));
					tempPlayer.setRow(Integer.valueOf(elements[2]));
					tempPlayer.setColumn(Integer.valueOf(elements[3]));
					// Add person object to global list of suspects
					suspects.add(tempPlayer);
				
				}
				else
				{
					Player tempPlayer = new ComputerPlayer();
					tempPlayer.setPlayerName(elements[0]);
					tempPlayer.setColor(convertColor(elements[1]));
					tempPlayer.setRow(Integer.valueOf(elements[2]));
					tempPlayer.setColumn(Integer.valueOf(elements[3]));
					// Add person object to global list of suspects
					suspects.add(tempPlayer);
				}

			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
		

	// Not sure how we are suppose to be doing this?
	public void makeCards()
	{
		// Loop through every cell in board if room make room card
		 System.out.println("ROOOOM" + rooms.size());

		for (String room : rooms)
		{
			
			Card roomCard = new Card();
			roomCard.setCardName(room);
			roomCard.setCardType(CardType.ROOM);
			deck.add(roomCard);
		}
		
		// Loop through every Player make a player card
		for (Player player : suspects)
		{
			System.out.println("SUS" + suspects.size());

			Card playerCard = new Card();
			playerCard.setCardName(player.getPlayerName());
			playerCard.setCardType(CardType.PERSON);
			deck.add(playerCard);
		}
		// Loop though every weapon file and make a weapon card
		File file;
		Scanner scanner;
	
		try
		{
			file = new File("weapons.txt");
			scanner = new Scanner(file);
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] elements = line.split(",");

				Card weaponCard = new Card();
				weaponCard.setCardName(elements[0]);
				weaponCard.setCardType(CardType.WEAPON);
				deck.add(weaponCard);

			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	// Not sure how we are suppose to be doing this?

	// A b c d e f g h
	// 1 1 1 1 1 1 1 1 
	// 1 1 1 1 1 1 1 1 
	public void dealCards()
	{
		 Collections.shuffle(deck);
		 for (Card card : deck)
		 {
			 System.out.println(card.getCardName());
		 }
		 int index = 0;
		 int newIndex = 0;
//		 while(deck.size() != 0){
////		 	playerCards.put(person[newIndex], deck.at(0));
////		 	deck.remove(index);
////		 	newIndex++;
//		 }
	}

	public void selectAnswer()
	{

	}

	public Card handleSuggestion()
	{
		return null;

	}

	public boolean checkAccusation(Solution accusation)
	{
		return false;

	}
	

}
