package tests;

import static org.junit.Assert.*;
import clueGame.Board;

public class gameSetupTests
{

    @BeforeClass
    public static void setUp()
    {
        // Runs once
        // Load the player config file
        Board.loadPlayerCongig();

    }

    @Before
    public static void before()
    {
        // Runs before every test
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
}