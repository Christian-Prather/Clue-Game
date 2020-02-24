package experiment;
import java.util.*;

public class IntBoard {
	private Set<BoardCell> cells = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
 	private Map<BoardCell, Set<BoardCell>> adjacent = new HashMap<BoardCell, Set<BoardCell>>();
	

 	public IntBoard()
 	{
 		super();
 	}
	public IntBoard(Set<BoardCell> cells) {
		super();
		this.cells = cells;
		// Will call calcAdject
	}
	public void calcAdjacencies()
	{
		// Calcs adjacents and saves to Map adjacent
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
	
}	
