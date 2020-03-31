package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class gameSetupTests
{
	private static Board board;

    @BeforeClass
    public static void setUp() throws BadConfigFormatException{
        // Runs once
        	// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Clue_Board_Layout_Langfield_Prather.csv", "LegendForClueLayout.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
        // Load the player config file
       
		board.loadPersonConfig();


    }
    //Calls board object to make a deck of cards, and deal the deck of cards
    //to each of the players in suspects array list
    @Before
    public void before()
    {
    	//board.deck;
        board.makeCards();
        board.dealCards();
    }

    //test for first, fourth, and last players names, locations on board, and colors
    //are valid.
    @Test 
    public void testLoadPlayers()
    {
        Player firstPlayer = board.suspects.get(0);
        //check name validity
        assertEquals("Colonel Mustard", firstPlayer.getPlayerName());
        //check color validity
        assertEquals(Color.blue, firstPlayer.getPlayerColor());
        //check player location on board validity
        assertEquals(2, firstPlayer.getPlayerRow());
        assertEquals(2, firstPlayer.getPlayerColumn());

        Player fourthPlayer = board.suspects.get(3);
        assertEquals("Mrs.Peacock", fourthPlayer.getPlayerName());
        assertEquals(Color.green, fourthPlayer.getPlayerColor());
        assertEquals(13, fourthPlayer.getPlayerRow());
        assertEquals(2, fourthPlayer.getPlayerColumn());

        Player lastPlayer = board.suspects.get(board.suspects.size() -1 );
        assertEquals("Mrs.Meadow-Brook", lastPlayer.getPlayerName());
        assertEquals(Color.pink, lastPlayer.getPlayerColor());
        assertEquals(2, lastPlayer.getPlayerRow());
        assertEquals(20, lastPlayer.getPlayerColumn());

    }

    //take in user input for # of players, 
    @Test
    public void createCardsTest()
    {
        // Tests the creation of cards from config files

        // Test the number of cards made is correct
    	System.out.println(board.deck.size() + "Balls");
        assertEquals(23, board.deck.size());

        int weaponCards = 0;
        int roomCards = 0;
        int personCards = 0;
        // Check the deck has correct number of each card

        for (Card currentCard : board.deck)
        {
            switch (currentCard.getCardType())
            {
                case PERSON:
                    personCards++;
                    break;
                case WEAPON:
                    weaponCards++;
                    break;
                case ROOM:
                    roomCards++;
                    break;
            }
        }
        // Check that there are 6 weapon cards
        assertEquals(6, weaponCards);

        // Check that there are 8 people
        assertEquals(8, personCards);

        // Check that there are 11 rooms
        assertEquals(9, roomCards);

    }

    @Test
    public void dealingCardsTest()
    {
    	// Test all cards were dealt
    	assertEquals(board.counter, board.deck.size());
        // Each player should have roughly the same number of cards
    	boolean thingy = false;
        // each player should have no more than 3 cards, and no less than 2 cards
        for(Map.Entry<Player, ArrayList<Card>> eachPlayer : board.playerCards.entrySet()){
            System.out.println("Key = " + eachPlayer.getKey() + ", value = " + eachPlayer.getValue());
            if(eachPlayer.getValue().size() < 3) {
            	thingy = true;
            }
           
        }
        assertTrue(thingy);
        // no player should have the same card.
        
    	boolean thingy2 = true;
        //for loop designed to deal the cards to each player, at this point the
    	//cards have already been shuffled and are dealt one by one to each
    	//player in the deck evenly, making sure no player is dealt duplicates
        for(Map.Entry<Player, ArrayList<Card>> eachPlayer : board.playerCards.entrySet()){
            //System.out.println("Key = " + eachPlayer.getKey() + ", value = " + eachPlayer.getValue());
            HashSet<String> totalCards = new HashSet<String>();
            for (Card card : eachPlayer.getValue())
            {
            	for(String eachCard : totalCards) {
            		if(eachCard == card.getCardName()) {
            			thingy2 = false;
            			break;
            		}
            	}
            	if (thingy2)
            	{
                    totalCards.add(card.getCardName());
            	}
            }
        }
        assertTrue(thingy2);
    }
}