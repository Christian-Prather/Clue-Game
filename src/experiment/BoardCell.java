package experiment;

public class BoardCell {
	public int row;
	public int column;
	
	public BoardCell(int x, int y)
	{
		// Flipped for programmer preference
		this.column = x;
		this.row = y;
	}
}
