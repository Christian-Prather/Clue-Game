package clueGame;

import java.awt.Color;
import java.util.ArrayList;

// All its variables and methods should only be called from Board

// Implememts solution

public class Player 
{
    private String playerName;
    private int row;
    private int column;
    private Color color;
    private ArrayList <Card> playerHand = new ArrayList<Card>();
   public Card disproveSuggestion(Solution suggestion) 
   {
     int matchingCards = 0;
     Card match = null;
     for (Card card : playerHand)
     {
      if (card == suggestion.person || card == suggestion.room  || card == suggestion.weapon)
      {
        // We have a person from suggestion
        matchingCards++;
        match = card;
        break;
      }
     }
   	
		return match;
   };

    public void setPlayerName(String playerName) {
    	this.playerName = playerName;
    }
    public void setRow(int row) {
    	this.row = row;
    }
    public void setColumn(int column) {
    	this.column = column;
    }
    public void setColor(Color color) {
    	this.color = color;
    }
    

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
    
    public void addCard(Card card)
    {
      playerHand.add(card);
    }

    public int getHandSize()
    {
      return playerHand.size();
    }

    public ArrayList<Card> getPlayerHand()
    {
      return playerHand;
    }
}