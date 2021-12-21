package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exceptions.MastermindIllegalColorException;
import exceptions.MastermindIllegalLengthException;
import model.MastermindModel;

/**
 * 
 * @author Zach Winans Purpose: To record the answer and also to report the
 *         "correctness" of the user's guess.
 *
 */
public class MastermindController {

	private String answer;

	/**
	 * Purpose: to read the model and build an answer
	 * @param model to create our answer from
	 */
	public MastermindController(MastermindModel model) {

		this.answer = "";

		for (int i = 0; i < 4; i++) {
			this.answer += model.getColorAt(i);
		}

	}

	/**
	 * Purpose: returns true if the guess
	 *               is equal to the answer.
	 *               
	 * @param guess that the user guesses. 
	 * 
	 * @throws MastermindIllegalColorException if guess contains illegal colors
	 * @throws MastermindIllegalLengthException if guess is of an illegal length
	 * @return a boolean true or false
	 **/
	public boolean isCorrect(String guess) throws MastermindIllegalLengthException, MastermindIllegalColorException {

		isLegal(guess);

		if (guess.toLowerCase().equals(this.answer)) {
			return true;
		}
		return false;
	}

	/**
	 * Purpose: reads the guess and
	 *               returns a count of the characters in the correct position.
	 * 
	 * @param guess that the user guesses 
	 * @throws MastermindIllegalColorException if guess contains illegal colors
	 * @throws MastermindIllegalLengthException if guess is of an illegal length
	 * @return the int count of correct characters in the string
	 * 
	 */
	public int getRightColorRightPlace(String guess)
			throws MastermindIllegalLengthException, MastermindIllegalColorException {

		isLegal(guess);

		int count = 0;
		for (int i = 0; i < answer.length(); i++) {
			if (charIsCorrect(guess, i)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Purpose: reads the guess and returns
	 *            a count of the correct characters in the incorrect position.
	 * 
	 * @param guess that the user guesses 
	 * @throws MastermindIllegalColorException if guess contains illegal colors
	 * @throws MastermindIllegalLengthException if guess is of an illegal length
	 * @return the integer count of the correct colors in the incorrect places.
	 * 
	 **/
	public int getRightColorWrongPlace(String guess)
			throws MastermindIllegalLengthException, MastermindIllegalColorException {

		isLegal(guess);

		ArrayList<Character> guessArray = new ArrayList<Character>();
		for (char c : guess.toCharArray()) {
			guessArray.add(c);
		}

		int count = 0;
		char toCheck;

		for (int start = 0; start < answer.length(); start++) {
			toCheck = answer.charAt(start);
			if (answer.charAt(start) == guess.charAt(start)) {
				if (guessArray.size() > 1) {
					guessArray = removeIndex(toCheck, guessArray);
				}

			} else {
				for (int end = 0; end < guess.length(); end++) {
					if (toCheck == guess.charAt(end) && guessArray.contains(answer.charAt(start))) {
						guessArray = removeIndex(toCheck, guessArray);
						count++;
						break;
					}

				}
			}

		}
		return count;
	}

	/**
	 * @param the   character to check
	 * @param array of characters representing the guess Purpose: Removes the index
	 *              of the guess array in order to keep track of if the character
	 *              has been accounted for.
	 */
	private ArrayList<Character> removeIndex(Character toCheck, ArrayList<Character> guessArray) {
		int indexToRemove = guessArray.indexOf(toCheck);
		if (indexToRemove != -1) {
			guessArray.remove(indexToRemove);
		}
		return guessArray;

	}

	/**
	 * @param string guess which contains the user's input
	 * @param int    index of the character Purpose: Returns true if the character
	 *               at the given index of the guess matches the character at the
	 *               given index of the answer.
	 * @return the boolean true or false if the character is in the correct location
	 */
	private boolean charIsCorrect(String guess, Integer index) {
		return (answer.charAt(index) == guess.charAt(index));
	}

	/**
	 * PURPOSE: to check our guess to see if it is legal or not.
	 * 
	 * @param a string guess to check legality
	 * @throws MastermindIllegalLengthException
	 * @throws MastermindIllegalColorException
	 */
	private void isLegal(String guess) throws MastermindIllegalLengthException, MastermindIllegalColorException {

		List<Character> colorOptions = Arrays.asList('r', 'o', 'y', 'g', 'b', 'p');

		if (guess.length() != 4) {
			throw new MastermindIllegalLengthException("Illegal length exception");
		}

		for (int i = 0; i < guess.length(); i++) {
			char temp = guess.charAt(i);
			if (!(colorOptions.contains(temp))) {
				throw new MastermindIllegalColorException("please guess a valid input");
			} else {
				continue;
			}
		}

	}

}
