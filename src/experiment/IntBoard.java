package experiment;
import java.util.*;

public class IntBoard {
	//private static Set<BoardCell> cells = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
 	private Map<BoardCell, Set<BoardCell>> adjacent = new HashMap<BoardCell, Set<BoardCell>>();
 	public static BoardCell[][] grid = new BoardCell[4][4];
	

 	public IntBoard()
 	{
 		super();
 		for (int i = 0; i < grid.length; i++) // Column
		{
			for (int j = 0; j < grid[0].length; j++) // ROW
			{
				BoardCell tempCell = new BoardCell(i,j);
				//gameBoard.add(tempCell);
				grid[i][j] = tempCell;
			}
		}
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
		System.out.println("GRID DIMENSIONS " + "ROW " + grid.length + "Column " + grid[0].length);
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				//BoardCell keyCell = getCell(grid[i][j]);
				Set<BoardCell> adjacencies = new HashSet<BoardCell>();
				System.out.println("GRID: " + grid[i][j].column + " " + grid[i][j].row);

				if (grid[i][j].column != 0)
				{
					//BoardCell tempCell =  getCell(grid[i][j].column -1, grid[i][j].row);
					adjacencies.add(grid[i -1][j]);
				}
				if (grid[i][j].row != 0)
				{
					//BoardCell tempCell = getCell(grid[i][j].column, grid[i][j].row -1);
					adjacencies.add(grid[i][j-1]);
				}
				if (grid[i][j].row != grid[1].length -1)
				{
					//BoardCell tempCell = getCell(grid[i][j].column, grid[i][j].row + 1);
					adjacencies.add(grid[i][j+1]);
				}
				if (grid[i][j].column != grid[0].length -1)
				{
				//	BoardCell tempCell = getCell(grid[i][j].column +1, grid[i][j].row);
					adjacencies.add(grid[i + 1][j]);
				}
				adjacent.put(grid[i][j], adjacencies);
				System.out.println(adjacent);
			}
		}
			
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell currentCell)
	{
		Set<BoardCell> adjCells = adjacent.get(currentCell);
		System.out.println("ADJ:" + adjCells);
		return adjCells;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength)
	{
		
		visited.add(startCell);
		Set<BoardCell> adjCells = getAdjList(startCell);
		
		for(BoardCell eachCell : adjCells) {
			
			if(!visited.contains(eachCell))
			{
				visited.add(eachCell);
				if(pathLength == 1)
				{
					targets.add(eachCell);
				}
				else
				{
					calcTargets(eachCell, pathLength -1);
				}
				visited.remove(eachCell);
			}
			
			
			
//			
//			if(pathLength == 1 ) {
//				targets.add(eachCell);
//			}if(!visited.contains(eachCell)) {
//				visited.add(eachCell);
//			}else {
//				calcTargets(eachCell, pathLength - 1);
//			}if(pathLength != 1) {
//				visited.remove(eachCell);
//			}
		
		}
//		for(BoardCell eachCell: adjCells) {
//			visited.remove(eachCell);
//		}
	}
	
	public Set<BoardCell> getTargets()
	{
		
		return targets;
	}
	
	public BoardCell getCell(int x, int y)
	{
		return grid[x][y];
	}
	
	public static void main(String[] args)
	{
		System.out.println("Here");
		//Set<BoardCell> gameBoard = new HashSet<BoardCell>();
	
		
		IntBoard board = new IntBoard();
		BoardCell cell = board.getCell(0,0);
		BoardCell test = board.getCell(1,0);

		System.out.println("CELLLL " +cell);
		System.out.println("Test " + test);


		Set<BoardCell> testList = board.getAdjList(cell);
		System.out.println("SIZE:" + testList.size());
		System.out.println("LIST:" + testList);

		

	}
}	
