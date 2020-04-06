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
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	private static Solution answer;
	
	
	// Make cards for use in tests
	public static Card mustard;
	public static Card library;
	public static Card hammer;
	public static Card face;
	public static Card knife;
	public static Card kitchen;
	public static Card peacock;
	public static Card horseshoe;
	
	@BeforeClass
	public static void setUp() {
		//set up test board
		board = Board.getInstance();
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		board.initialize();
		//set up test game
		board.makeCards();
//		board.selectAnswer();
//		board.dealCards();
		
		mustard = new Card();
		mustard.setCardName("Colonel Mustard");
		mustard.setCardType(CardType.PERSON);
		library = new Card();
		library.setCardName("Library");
		library.setCardType(CardType.ROOM);
		hammer = new Card();
		hammer.setCardName("Hammer");
		hammer.setCardType(CardType.WEAPON);
		face = new Card();
		face.setCardName("Ms.Face");
		face.setCardType(CardType.PERSON);
		knife = new Card();
		knife.setCardName("Knife");
		knife.setCardType(CardType.WEAPON);
		kitchen = new Card();
		kitchen.setCardName("Kitchen");
		kitchen.setCardType(CardType.ROOM);
		peacock = new Card();
		peacock.setCardName("Mrs.Peacock");
		peacock.setCardType(CardType.PERSON);
		horseshoe = new Card();
		horseshoe.setCardName("Horseshoe");
		horseshoe.setCardType(CardType.WEAPON);
		
		// Set answer for test
		answer = new Solution(mustard, library, hammer);

	}
	@Test
	public void checkAccusationTest() {
		Solution guess = new Solution(mustard, library, hammer);
		board.setAnswer(answer);
		//Check correct accusation
		//correct if it contains the correct person, weapon and room
		assertTrue(board.checkAccusation(guess));
		//Check false accusation
		//not correct if the room is wrong, or if the person is wrong, if the weapon is wrong, or if all three are wrong
		//wrong room
		//these guesses need to have "variables" (Ms.face, knife, kitchen)
		guess = new Solution(face, knife, kitchen);
		assertFalse(board.checkAccusation(guess));
		//wrong weapon
		guess = new Solution(mustard, library, horseshoe);
		assertFalse(board.checkAccusation(guess));
		//wrong person
		guess = new Solution(peacock, library, horseshoe);
		assertFalse(board.checkAccusation(guess));
	}
	@Test
	public void disproveSuggestionTest() {
		Solution suggestion = new Solution(peacock, library, horseshoe);
		
		ComputerPlayer computer1 = new ComputerPlayer();
		ComputerPlayer computer2 = new ComputerPlayer();
		ComputerPlayer computer3 = new ComputerPlayer();
		HumanPlayer human = new HumanPlayer();
			
//		ArrayList<ComputerPlayer> comps = new ArrayList<ComputerPlayer>();
//
//		Card professorPlum = new Card();
//		professorPlum.setCardName("professorPlum");
//		professorPlum.setCardType(CardType.PERSON);
//		
//		Card gnomeCard = new Card();
//		gnomeCard.setCardName("gnomeCard");
//		gnomeCard.setCardType(CardType.WEAPON);
//		
//		Card observatoryCard = new Card();
//		observatoryCard.setCardName("observatoryCard");
//		observatoryCard.setCardType(CardType.ROOM);
		
		// Comp 1 get professor Plum
		computer1.addCard(peacock);
		// Comp 2 gets gnome weapon
		computer2.addCard(library);
		// Human gets observatory card
		human.addCard(horseshoe);
		
		//Comp 3 doesnt get a card
		
				
//		hand.add(professorPlum);
		assertEquals(computer1.disproveSuggestion(suggestion), peacock);
		assertEquals(human.disproveSuggestion(suggestion), horseshoe);
		assertEquals(computer3.disproveSuggestion(suggestion), null);

		
	}
	@Test
	public void handleSuggestionTest() {
		
	}
	public void createSuggestion() {
		ComputerPlayer player = new ComputerPlayer();
		//java.awt.Point location = new Point(9,19);
		//player.setLocation(location);
		player.setRow(9);
		player.setColumn(19);
		BoardCell playerSpot = board.getCellAt(player.getPlayerRow(), player.getPlayerColumn());
		Map<Character, String> legend = board.getLegend();
		String roomName = legend.get(playerSpot.getInitial());

		// Reduce weapon and people to only one possible each
		Card firstWeapon = board.weaponsNotGuessed.get(0);
		Card firstPerson = board.peopleNotGuessed.get(0);

		ArrayList<Card> limitedWeapons = new ArrayList<Card>();
		ArrayList<Card> limitedPeople = new ArrayList<Card>();
		limitedWeapons.add(firstWeapon);
		limitedPeople.add(firstPerson);

		board.weaponsNotGuessed = limitedWeapons;
		board.peopleNotGuessed = limitedPeople;


		Solution playerSuggestion = player.createSuggestion();
		// Check the suggestion room is the room player is currently in
		assertEquals(playerSuggestion.room.getCardName(), roomName);
		
		// Check its the only possible person
		assertTrue(playerSuggestion.person.equals(firstPerson));

		// Check its the only possible weapon
		assertTrue(playerSuggestion.weapon.equals(firstWeapon));
	
	}
}
