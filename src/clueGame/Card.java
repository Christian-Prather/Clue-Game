package clueGame;

// Variables and methods should only be called from Player
public class Card
{
    private String cardName;
    private CardType cardType;
    public boolean equals()
    {
        return false;
    };

    public CardType getCardType()
    {
    	return cardType;
    }
    
    public String getCardName()
    {
    	return cardName;
    }
    public void setCardName(String cardName)
    {
        this.cardName = cardName;
    }
    
    public void setCardType(CardType cardType)
    {
    	this.cardType = cardType;
    }
}