package experiment;
import java.util.*;

public class IntBoard {
	private static Set<BoardCell> cells = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
 	private Map<BoardCell, Set<BoardCell>> adjacent = new HashMap<BoardCell, Set<BoardCell>>();
 	public static BoardCell[][] grid = new BoardCell[4][4];
	

 	public IntBoard()
 	{
 		super();
 		calcAdjacencies();
 		for(Map.Entry<BoardCell, Set<BoardCell>> entry : adjacent.entrySet())
 		{
 			System.out.print("Key: (" + entry.getKey().column + ", " + entry.getKey().row + ")" + " Value: ");
 			for (BoardCell cell : entry.getValue())
 			{
 				System.out.print("(" + cell.column +", "+ cell.row + ") ");
 			}
 			System.out.println();
 		}
 		
 	}
	public IntBoard(Set<BoardCell> cells) {
		super();
		this.cells = cells;
		// Will call calcAdject
		calcAdjacencies();
	}
	public void calcAdjacencies()
	{
		// Calcs adjacents and saves to Map adjacent
		// For every cell in cells (do check logic and push into map)
		for (BoardCell cell : cells)
		{
			Set<BoardCell> adjacencies = new HashSet<BoardCell>();

			if (cell.column != 0)
			{
				BoardCell tempCell = new BoardCell(cell.column -1, cell.row);
				adjacencies.add(tempCell);
			}
			if (cell.row != 0)
			{
				BoardCell tempCell = new BoardCell(cell.column, cell.row -1);
				adjacencies.add(tempCell);
			}
			if (cell.row != grid[1].length)
			{
				BoardCell tempCell = new BoardCell(cell.column, cell.row + 1);
				adjacencies.add(tempCell);
			}
			if (cell.column != grid[0].length)
			{
				BoardCell tempCell = new BoardCell(cell.column +1, cell.row);
				adjacencies.add(tempCell);
			}
			adjacent.put(cell, adjacencies);
		}
		
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell currentCell)
	{
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength)
	{
		
	}
	
	public Set<BoardCell> getTargets()
	{
		return null;
	}
	
	public BoardCell getCell(int x, int y)
	{
		return new BoardCell(x,y);
	}
	
	public static void main(String[] args)
	{
		System.out.println("Here");
		Set<BoardCell> gameBoard = new HashSet<BoardCell>();
		for (int i = 0; i < grid[0].length; i++)
		{
			for (int j = 0; j < grid[1].length; j++)
			{
				BoardCell tempCell = new BoardCell(i,j);
				gameBoard.add(tempCell);
			}
		}
		cells = gameBoard;
		IntBoard board = new IntBoard();
		

	}
}	
