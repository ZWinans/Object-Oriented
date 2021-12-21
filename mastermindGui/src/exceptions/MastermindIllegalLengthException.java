package exceptions;

/**
 * PURPOSE: to account for an illegal length exception where a user guesses a string
 * that is not of legal length.
 * @author Zachary Winans
 *
 */

public class MastermindIllegalLengthException extends Exception {
	
	public MastermindIllegalLengthException(String message) throws MastermindIllegalLengthException {
		super(message);
		
	}

}
