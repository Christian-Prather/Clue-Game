package clueGame;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import clueGame.Board;
public class ComputerPlayer extends Player 
{
	private static Board board = Board.getInstance();

    public BoardCell pickLocation(Set<BoardCell> target)
    {
        return null;
    }
    public void makeAccusation() 
    {
    	
    };
    
    public Solution createSuggestion() 
    {
        BoardCell playerSpot = board.getCellAt(this.getPlayerRow(), this.getPlayerColumn());
		Map<Character, String> legend = board.getLegend();
		String roomName = legend.get(playerSpot.getInitial());
		Card place = new Card();
		Card person = new Card();
		Card thing = new Card();
		// Get the right room card 
		for ( Card room : board.roomsNotGuessed)
		{
			if (room.getCardName().equals(roomName))
			{
				place = room;
			}
		}
		Random rand = new Random();

		// Pick a person
		person = board.peopleNotGuessed.get(rand.nextInt(board.peopleNotGuessed.size()));

		// Pick a Weapon
		thing = board.weaponsNotGuessed.get(rand.nextInt(board.weaponsNotGuessed.size()));

        Solution newSuggestion = new Solution(person, place, thing);
        return newSuggestion;
    };
}
