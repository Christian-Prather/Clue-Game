package tests;

import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	
	
	@BeforeClass
	public static void setUp() {
		//set up test board
		board = Board.getInstance();
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		board.initialize();
		//set up test game
		
		board.selectAnswer();
		board.dealCards();
	}
	public void checkAccusationTest() {
		Solution answer = new Solution();
		Solution guess = new Solution();
		board.setAnswer(answer);
		//Check correct accusation
		//correct if it contains the correct person, weapon and room
		assertTrue(board.checkAccusation(guess));
		//Check false accusation
		//not correct if the room is wrong, or if the person is wrong, if the weapon is wrong, or if all three are wrong
		//wrong room
		//these guesses need to have "variables" (Ms.face, knife, kitchen)
		guess = new Solution();
		assertFalse(board.checkAccusation(guess));
		//wrong weapon
		guess = new Solution();
		assertFalse(board.checkAccusation(guess));
		//wrong person
		guess = new Solution();
		assertFalse(board.checkAccusation(guess));
	}
	public void disproveSuggestionTest() {
		Solution suggestion = new Solution();
		ComputerPlayer computer1 = new ComputerPlayer();
		ComputerPlayer computer2 = new ComputerPlayer();
		ComputerPlayer computer3 = new ComputerPlayer();
		HumanPlayer human = new HumanPlayer();
		
		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<ComputerPlayer> comps = new ArrayList<ComputerPlayer>();

		Card professorPlum = new Card();
		Card gnomeCard = new Card();
		Card observatoryCard = new Card();
		hand.add(professorPlum);
		assertEquals(computer1.disproveSuggestion(suggestion), professorPlum);
		hand.add(gnomeCard);
		hand.add(observatoryCard);
		
		human.setCards(hand);
		
	}
	public void handleSuggestionTest() {
		
	}
	public void createSuggestion() {
		ComputerPlayer player = new ComputerPlayer();
		java.awt.Point location = new Point(9,19);
		player.setLocation(location);
		player.setCurrentRoom("Dining Room");
		Card mustardCard = new Card("Colonel Mustard", Card.PERSON);
		Card knifeCard = new Card ("Knife", Card.cardType.WEAPON);
		Card libraryCard = new Card("Library", Card.cardType.ROOM);
		player.updateSeen(mustardCard);
		player.updateSeen(knifeCard);
		player.updateSeen(libraryCard);
	}
}
