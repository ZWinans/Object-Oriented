package model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Zach Winans Purpose: To create and store the solution.
 *
 */
public class MastermindModel {
	private String answer = "";
	private List<String> colorOptions = Arrays.asList("r", "o", "y", "g", "b", "p");
	private Random rand = new Random();

	public MastermindModel() {

		answer = buildAnswer();
		

	}

	/**
	 * PURPOSE: to build an answer comprised of randomly selected color options
	 * 
	 * @return A String representing a randomly formulated answer
	 */
	private String buildAnswer() {
		for (int i = 0; i < 4; i++) {
			int n = rand.nextInt(colorOptions.size());
			answer += colorOptions.get(n);
		}

		return answer;
	}

	/**
	 * This method is a special constructor to allow us to use JUnit to test our
	 * model.
	 * 
	 * Instead of creating a random solution, it allows us to set the solution from
	 * a String parameter.
	 * 
	 * 
	 * @param answer A string that represents the four color solution
	 */
	public MastermindModel(String answer) {
		this.answer = answer;
	}

	/**
	 * PURPOSE: To return the color at the given index
	 * @param index to check the color at
	 * @return char at the given index
	 */
	public char getColorAt(int index) {

		return answer.charAt(index);

	}

}
