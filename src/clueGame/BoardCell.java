package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private Character initial;
	
	
	public BoardCell(int x, int y)
	{
		// Flipped for programmer preference
		this.column = x;
		this.row = y;
	}
	
	public Boolean isWalkway()
	{
		return null;
	}
	
	public Boolean isRoom()
	{
		return null;
	}
	
	public Boolean isDoorway()
	{
		return null;
	}
	public DoorDirection getDoorDirection()
	{
		return null;
	}
	public Character getInitial()
	{
		return null;
	}
}
