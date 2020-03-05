package clueGame;

public class BoardCell {
	public int row;
	public int column;
	Character initial;
	boolean room;
	boolean doorway;
	boolean walkway;
	DoorDirection doorDirection;
	
	public BoardCell(int x, int y)
	{
		// Flipped for programmer preference
		this.column = x;
		this.row = y;
		this.room = false;
		this.doorway = false;
		this.walkway = false;
	}
	
	public Boolean isWalkway()
	{
		
		return walkway;
	}
	
	public Boolean isRoom()
	{
		return room;
	}
	
	public Boolean isDoorway()
	{
		return doorway;
	}
	public DoorDirection getDoorDirection()
	{
		return doorDirection;
	}
	public Character getInitial()
	{
		return initial;
	}
}
