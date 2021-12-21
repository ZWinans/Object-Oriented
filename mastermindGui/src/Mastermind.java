import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.MastermindController;
import exceptions.MastermindIllegalColorException;
import exceptions.MastermindIllegalLengthException;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MastermindModel;

/**
 * 
 * @author Zach Winans 
 * PURPOSE: To launch either the Gui view or the text view
 *         depending on the program arguments
 *
 **/
public class Mastermind {

	/**
	 * PURPOSE: to prompt the user for their initial yes or no input, followed by
	 * initializing the game and prompting the user for guesses.
	 * 
	 * @param args which represent -window or -text, if args are null, the default view is GUI
	 * @throws Exception if the user inputs improper guesses
	 */
	public static void main(String[] args) throws Exception {

		if ((args.length == 0) || args[0].equals("-window")) {
			Application.launch(MastermindGUIView.class, args);

		} else if (args[0].equals("-text")) {
			MastermindTextView textGame = new MastermindTextView();
			textGame.play();

		}

	}

}
