package clueGame;

import java.io.FileNotFoundException;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() 
	{
		super("Your file is bad");
	}
	public BadConfigFormatException(String message) throws FileNotFoundException{
		super(message);
	}

}
