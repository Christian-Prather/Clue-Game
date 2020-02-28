package clueGame;

public enum DoorDirection 
{
	UP('U'), DOWN('D'), LEFT('L'), RIGHT('R'), NONE('N');
	 private char direction;

	    DoorDirection(char direction) {
	        this.direction = direction;
	    }
	
}
