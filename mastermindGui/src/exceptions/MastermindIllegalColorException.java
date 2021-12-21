package exceptions;


/**
 * PURPOSE: to account for an illegal color exception where a user guesses a character
 * that is not legal.
 * @author Zachary Winans
 *
 */
public class MastermindIllegalColorException extends Exception {

	public MastermindIllegalColorException(String message) throws MastermindIllegalColorException {

		super(message);

	}

}
