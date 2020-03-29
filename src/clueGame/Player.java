package clueGame;

import java.awt.Color;

// All its variables and methods should only be called from Board

// Implememts solution

public class Player 
{
    private String playerName;
    private int row;
    private int column;
    private Color color;

    public Card disproveSuggestion(Solution suggestion) {
		return null;
    };

    public String getPlayerName()
    {
    	return this.playerName;
    }
    
    public int getPlayerRow()
    {
    	return this.row;
    }
    
    public int getPlayerColumn()
    {
    	return this.column;
    }
    
    public Color getPlayerColor()
    {
    	return this.color;
    }
}