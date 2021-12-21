import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.MastermindController;
import exceptions.MastermindIllegalColorException;
import exceptions.MastermindIllegalLengthException;
import model.MastermindModel;


/**
 * PURPOSE: To prompt the user for input as well as guesses.
 *         This class creates the controller and the model. If the user
 *         correctly guesses the string of colors, denoted as a string of four
 *         letters in 10 guesses, the program ends and the user is prompted to
 *         play again. If no, the program ends.
 * @author Zach Winans
 *
 */

public class MastermindTextView {

	public static void main(String[] args) throws MastermindIllegalLengthException, MastermindIllegalColorException {

		play();

	}

	/**
	 * PURPOSE: To prompt the user for input and reference that input against
	 * the controller/model
	 * @param args 
	 * @throws MastermindIllegalLengthException
	 * @throws MastermindIllegalColorException
	 */
	public static void play() throws MastermindIllegalLengthException, MastermindIllegalColorException {
		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to Mastermind!");
		System.out.print("Would you like to play? ");

		String myString = getYesOrNo(input);
		MastermindController controller;

		while (myString.toLowerCase().equals("yes")) {
			int guessNumber = 1;
			
			
			MastermindModel model = new MastermindModel();
			controller = new MastermindController(model);
			

			while (guessNumber <= 10) {

				System.out.print("Enter guess number " + guessNumber + ": ");
				String myGuess = getGuess(input);

				// If the guess is correct
				if (controller.isCorrect(myGuess)) {
					System.out.println("Congratulations, you're a mastermind!" + "\n");
					break;

					// else the guess is not correct
				} else {
					getStats(myGuess, controller);
					guessNumber++;
				}
				// If out of guesses and final guess is incorrect
				if (guessNumber > 10 && !controller.isCorrect(myGuess)) {
					System.out.println("Sorry you're out of turns!" + "\n");
				}
			}
			System.out.print("Would you like to play again? ");
			myString = getYesOrNo(input);
		}
		input.close();
	}
	
	/**
	 * 
	 * Purpose: to ensure that the user has answered yes or no, if no, prompt the
	 * user for another response.
	 * 
	 * @param input scanner which allows us to get the user's response.
	 * @return
	 */
	private static String getYesOrNo(Scanner input) {

		List<String> acceptableAnswers = Arrays.asList("yes", "no");

		String myString = input.next();
		System.out.println();

		while (!acceptableAnswers.contains(myString.toLowerCase())) {
			System.out.print("Please answer either yes or no: ");
			myString = input.next();
			System.out.println();
		}
		return myString;
	}

	/**
	 * 
	 * PURPOSE: to get the user's guess and ensure that the guess is acceptable.
	 * @param input scanner which allows us to get the user's guess.
	 * @return the string guess
	 */
	private static String getGuess(Scanner input) {
		String myGuess = "";
		myGuess = input.next();

		// Makes sure that myGuess is an appropriate length
		if (myGuess.length() != 4) {
			while (myGuess.length() != 4) {
				System.out.print("Not a valid input, please guess again: ");
				myGuess = input.next();
			}
		}
		return myGuess;
	}

	/**
	 * PURPOSE: to read the guess and process both the correct colors in the correct
	 * place and the correct colors in the incorrect place
	 * 
	 * @param myGuess the user's guess
	 * @param controller to reference the guess against
	 * @throws MastermindIllegalLengthException
	 * @throws MastermindIllegalColorException
	 */
	private static void getStats(String myGuess, MastermindController controller) throws MastermindIllegalLengthException, MastermindIllegalColorException {
		System.out.println("Colors in the correct place: " + controller.getRightColorRightPlace(myGuess));
		System.out.println(
				"Colors correct but in wrong position:  " + controller.getRightColorWrongPlace(myGuess) + "\n");
	}
}

