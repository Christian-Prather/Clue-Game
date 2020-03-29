package tests;

import static org.junit.Assert.*;
import clueGame.Board;

public class gameSetupTests
{

    @BeforeClass
    public static void setUp()
    {
        // Runs once
        	// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Clue_Board_Layout_Langfield_Prather.csv", "LegendForClueLayout.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
        // Load the player config file
        board.loadPlayerCongig();
        board.makeCards();

    }

    @Before
    public static void before()
    {
        // Runs before every test
        ArrayList<Card> deck = board.deck;
    }


    @test 
    public void testLoadPlayers()
    {
        Player firstPlayer = suspects.get(0);
        assertEquals("Colonel Mustard", firstPlayer.playerName);
        assertEquals(Blue, firstPlayer.color);
        assertEquals(10, firstPlayer.row);
        assertEquals(12, firstPlayer.column);

        Player fourthPlayer = suspects.get(3);
        assertEquals("Mrs.Peacock", fourthPlayer.playerName);
        assertEquals(Red, fourthPlayer.color);
        assertEquals(15, fourthPlayer.row);
        assertEquals(3, fourthPlayer.column);

        Player lastPlayer = suspects.get(suspects.size() -1 );
        assertEquals("Mrs.Meadow-Brook", lastPlayer.playerName);
        assertEquals(Pink, lastPlayer.color);
        assertEquals(12, lastPlayer.row);
        assertEquals(116, lastPlayer.column);

    }

    @test
    public void createCards()
    {
        // Tests the creation of cards from config files

        // Test the number of cards made is correct
        assertEquals(25. deck);

        int weaponCards = 0;
        int roomCards = 0;
        int personCards = 0;
        // Check the deck has correct number of each card

        for (Card currentCard : deck)
        {
            switch (currentCard.cardType)
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
        assertEquals(11, roomCards);

    }
}