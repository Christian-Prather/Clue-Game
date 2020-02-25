package experiment;
import java.util.*;

public class IntBoard {
	//private static Set<BoardCell> cells = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
 	private Map<BoardCell, Set<BoardCell>> adjacent = new HashMap<BoardCell, Set<BoardCell>>();
 	public static BoardCell[][] grid = new BoardCell[3][3];
	

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

	public void calcAdjacencies()
	{
		// Calcs adjacents and saves to Map adjacent
		// For every cell in cells (do check logic and push into map)
		for (int i = 0; i < grid[0].length; i++)
		{
			for (int j = 0; j < grid[1].length; j++)
			{
				Set<BoardCell> adjacencies = new HashSet<BoardCell>();

				if (grid[i][j].column != 0)
				{
					BoardCell tempCell = new BoardCell(grid[i][j].column -1, grid[i][j].row);
					adjacencies.add(tempCell);
				}
				if (grid[i][j].row != 0)
				{
					BoardCell tempCell = new BoardCell(grid[i][j].column, grid[i][j].row -1);
					adjacencies.add(tempCell);
				}
				if (grid[i][j].row != grid[1].length)
				{
					BoardCell tempCell = new BoardCell(grid[i][j].column, grid[i][j].row + 1);
					adjacencies.add(tempCell);
				}
				if (grid[i][j].column != grid[0].length)
				{
					BoardCell tempCell = new BoardCell(grid[i][j].column +1, grid[i][j].row);
					adjacencies.add(tempCell);
				}
				adjacent.put(grid[i][j], adjacencies);
				System.out.println(adjacent);
			}
		}
			
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell currentCell)
	{
		Set<BoardCell> adjCells = adjacent.get(currentCell);
		System.out.println(adjCells);
		return adjCells;
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
		return this.grid[x][y];
	}
	
	public static void main(String[] args)
	{
		System.out.println("Here");
		//Set<BoardCell> gameBoard = new HashSet<BoardCell>();
		for (int i = 0; i < grid[0].length; i++)
		{
			for (int j = 0; j < grid[1].length; j++)
			{
				BoardCell tempCell = new BoardCell(i,j);
				System.out.println("GRID: " + tempCell.column + " " + tempCell.row);
				//gameBoard.add(tempCell);
				grid[i][j] = tempCell;
			}
		}
		
		IntBoard board = new IntBoard();
		BoardCell cell = board.getCell(0,0);
		System.out.println("CELLLL" +cell);

		Set<BoardCell> testList = board.getAdjList(cell);
		for (BoardCell item : testList)
		{
			System.out.println("Cell" + item);
		}
		

	}
}	
