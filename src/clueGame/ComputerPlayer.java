package clueGame;

import java.util.ArrayList;
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
	//	System.out.println("My room" + roomName);
		for ( Card room : board.roomsNotGuessed)
		{
		//	System.out.println(room.getCardName());
			if (room.getCardName().strip().equals(roomName.strip()))
			{
			//	System.out.println("Found match");
				place = room;
			}
		}
		Random rand = new Random();

		// Pick a person
		person = board.peopleNotGuessed.get(rand.nextInt(board.peopleNotGuessed.size()));

		// Pick a Weapon
		thing = board.weaponsNotGuessed.get(rand.nextInt(board.weaponsNotGuessed.size()));

        Solution newSuggestion = new Solution(person, place, thing);
        super.playerSuggestion = newSuggestion;
        return newSuggestion;
    };
    
    public BoardCell chooseTarget(Set<BoardCell> targets, Set<BoardCell> visited)
    {
        // Did not find a valid room so pick at random
        if (targets.isEmpty())
        {
        	return null;
        }
        for (BoardCell target : targets)
        {
        	// Check to see if at door since its basically a room may need to change to be actual room 
        	// later
            if(target.isDoorway())
            {
            	System.out.println("Room");
                // A target is a room so select it if not just visited
                if (!visited.contains(target))
                {
                    return target;
                }
            }
        }
    
        
        Random rand = new Random();
        ArrayList<BoardCell> arrayTargets = new ArrayList(targets);
        BoardCell choice = arrayTargets.get(rand.nextInt(arrayTargets.size()));
        return choice;
        
    }
}
