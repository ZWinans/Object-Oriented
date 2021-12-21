import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.MastermindController;
import exceptions.MastermindIllegalColorException;
import exceptions.MastermindIllegalLengthException;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MastermindModel;

/**
 * PURPOSE: To play the gui view of our mastermind game by utilizing java fx to
 * create a playable user interface.
 * 
 * @author zwinans
 *
 */
public class MastermindGUIView extends Application {

	int numberOfRows = 10;
	int numberOfColumns = 4;
	private Circle circle1 = new Circle(20);
	private Circle circle2 = new Circle(20);
	private Circle circle3 = new Circle(20);
	private Circle circle4 = new Circle(20);
	private List<String> guesses;
	private int guessCount = 1;

	/**
	 * PURPOSE: Launches the stage and builds the proper user interface
	 */
	public void start(Stage stage) throws Exception {

		MastermindModel model = new MastermindModel();

		MastermindController control = new MastermindController(model);

		BorderPane window = new BorderPane();

		Scene scene = new Scene(window, 400, 600);

		VBox vbox = buildVBox();

		window.setCenter(vbox);

		GridPane gpane = new GridPane();

		addCirclesToGridPane(gpane);
		changeCircleColor();

		Button btnGuess = new Button("Guess");
		gpane.add(btnGuess, 40, 40);
		btnGuess.setTranslateY(-15);

		window.setBottom(gpane);
		gpane.setStyle("-fx-background-color: aliceblue;");
		gpane.getRowConstraints().add(new RowConstraints(25));

		guesses = new ArrayList<String>();

		handleButton(btnGuess, control, vbox);

		stage.setScene(scene);
		stage.setTitle("Mastermind");
		stage.show();

	}

	/**
	 * PURPOSE: To build the VBox
	 * @return the completed VBox
	 */
	private VBox buildVBox() {
		VBox vbox = new VBox();
		
		Insets inset = new Insets(10, 0, 0, 0);
		
		vbox.setPadding(inset);

		vbox.setSpacing(10);

		vbox.setStyle("-fx-background-color: tan;");
		return vbox;
	}

	/**
	 * PURPOSE: To handle the action of a user pressing a button - handles alerts to
	 * the user as well regarding winning/losing the game and prints the results to
	 * the UI.
	 * 
	 * @param btnGuess the button to click
	 * @param control  the control to reference guesses against
	 * @param vbox     the vbox to build as we play through the game
	 */
	private void handleButton(Button btnGuess, MastermindController control, VBox vbox) {
		btnGuess.setOnAction((event) -> {

			String guess = getAllColors();

			if (guesses.contains("WINNER")) {
				resetPegs();
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("");
				a.setContentText("You won!");
				a.setHeaderText("You already won! Restart the app to play again");
				a.showAndWait();
				return;
			}

			if (guess.contains("illegal")) {

				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("Illegal Argument");
				a.setContentText("You must enter a valid color");
				a.setHeaderText("Invalid move");
				a.showAndWait();

				return;
			}

			if (guessCount <= 10) {
				guesses.add(guess);
				GridPane guessPane = null;
				try {
					guessPane = printGuesses(control, guess);
				} catch (MastermindIllegalLengthException | MastermindIllegalColorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vbox.getChildren().add(guessPane);
				guessCount++;
			}

			resetPegs();

			try {

				if (!control.isCorrect(guess)) {

				} else {
					guesses.add("WINNER");
					Alert a = new Alert(Alert.AlertType.INFORMATION);
					a.setTitle("");
					a.setContentText("You win!");
					a.setHeaderText("Congratulations you're a mastermind!");
					a.showAndWait();
				}
			} catch (MastermindIllegalLengthException | MastermindIllegalColorException e) {
				e.printStackTrace();
			}

			try {
				if (!control.isCorrect(guess) && guessCount > 10) {
					resetPegs();
					Alert a = new Alert(Alert.AlertType.INFORMATION);
					a.setTitle("");
					a.setContentText("You lose!");
					a.setHeaderText("You're out of guesses, please refresh and try again");
					a.showAndWait();

					return;
				}
			} catch (MastermindIllegalLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MastermindIllegalColorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	/**
	 * PURPOSE: To reset all pegs to black
	 */
	private void resetPegs() {
		circle1.setFill(Color.BLACK);
		circle2.setFill(Color.BLACK);
		circle3.setFill(Color.BLACK);
		circle4.setFill(Color.BLACK);

	}

	/**
	 * 
	 * @param control the control to reference against the guess
	 * @param guess   the users guess
	 * @return GridPane that we added the users guesses to
	 * @throws MastermindIllegalLengthException
	 * @throws MastermindIllegalColorException
	 */
	private GridPane printGuesses(MastermindController control, String guess)
			throws MastermindIllegalLengthException, MastermindIllegalColorException {

		GridPane gpane = new GridPane();

		GridPane clues = new GridPane();

		int rightright = control.getRightColorRightPlace(guess);
		int rightwrong = control.getRightColorWrongPlace(guess);

		int totalCircles = rightright + rightwrong;

		clues.setPadding(new Insets(10));
		clues.setHgap(5);
		clues.setVgap(5);

		int count = 0;

		for (int i = 0; i < totalCircles; i++) {
			if (rightright > 0 && i <= 1) {
				clues.add(new Circle(5, Color.BLACK), i, 1);
				rightright--;
			} else if (rightright > 0 && i > 1) {
				clues.add(new Circle(5, Color.BLACK), count, 2);
				rightright--;
				count++;
			} else if (rightright == 0 && rightwrong > 0 && i <= 1) {
				clues.add(new Circle(5, Color.WHITE), i, 1);
				rightwrong--;
			} else if (rightright == 0 && rightwrong > 0 && i > 1) {
				clues.add(new Circle(5, Color.WHITE), count, 2);
				rightwrong--;
				count++;
			}

		}

		Text num = new Text();
		num.setFont(new Font(20));
		num.setText(Integer.toString(guessCount));
		num.setTranslateX(10);
		gpane.add(num, 1, 0);

		gpane.getColumnConstraints().add(new ColumnConstraints(15));
		gpane.add(new Circle(20, circle1.getFill()), 2, 0);
		gpane.getColumnConstraints().add(new ColumnConstraints(60));
		gpane.add(new Circle(20, circle2.getFill()), 3, 0);
		gpane.getColumnConstraints().add(new ColumnConstraints(60));
		gpane.add(new Circle(20, circle3.getFill()), 4, 0);
		gpane.getColumnConstraints().add(new ColumnConstraints(60));
		gpane.add(new Circle(20, circle4.getFill()), 5, 0);
		gpane.getColumnConstraints().add(new ColumnConstraints(60));
		gpane.add(clues, 6, 0);
		gpane.getColumnConstraints().add(new ColumnConstraints(50));

		clues.setTranslateY(-6);
		Insets inset = new Insets(0, 0, 0, 10);
		gpane.setPadding(inset);

		return gpane;

	}

	/**
	 * PURPOSE: To change the circle's colors when clicked
	 */
	private void changeCircleColor() {
		circle1.setOnMouseClicked(e -> {
			changeColor(circle1);
		});

		circle2.setOnMouseClicked(e -> {
			changeColor(circle2);
		});

		circle3.setOnMouseClicked(e -> {
			changeColor(circle3);
		});

		circle4.setOnMouseClicked(e -> {
			changeColor(circle4);
		});

	}

	/**
	 * PURPOSE: to get the colors of the circles
	 * 
	 * @return the users guess
	 */
	private String getAllColors() {

		String guess = "";

		String one = getColor(circle1);

		String two = getColor(circle2);

		String three = getColor(circle3);

		String four = getColor(circle4);

		guess += one;
		guess += two;
		guess += three;
		guess += four;

		return guess;

	}

	/**
	 * 
	 * @param circle color
	 * @return a string representation of the color of the circle
	 */
	private String getColor(Circle circle) {

		if (circle.getFill() == Color.RED) {
			return "r";
		} else if (circle.getFill() == Color.ORANGE) {
			return "o";
		} else if (circle.getFill() == Color.YELLOW) {
			return "y";
		} else if (circle.getFill() == Color.GREEN) {
			return "g";
		} else if (circle.getFill() == Color.BLUE) {
			return "b";
		} else if (circle.getFill() == Color.PURPLE) {
			return "p";
		}

		// else is black
		return "illegal";

	}

	/**
	 * PURPOSE: Adds circles to the gridpane for user's guesses
	 * 
	 * @param gpane that we populated with circles
	 */
	private void addCirclesToGridPane(GridPane gpane) {

		List<Circle> circles = new ArrayList<Circle>();
		for (int i = 0; i <= numberOfColumns; i++) {
			circles.add(new Circle(20));
		}

		ColumnConstraints column = new ColumnConstraints(80);
		column.setHalignment(HPos.CENTER);
		gpane.getColumnConstraints().add(column);

		gpane.add(circle1, 0, 0);
		gpane.getColumnConstraints().add(column);
		gpane.add(circle2, 1, 0);
		gpane.getColumnConstraints().add(column);
		gpane.add(circle3, 2, 0);
		gpane.getColumnConstraints().add(column);
		gpane.add(circle4, 3, 0);

		circle1.setTranslateY(14);
		circle2.setTranslateY(14);
		circle3.setTranslateY(14);
		circle4.setTranslateY(14);

		Insets inset = new Insets(0, 0, 0, 10);
		gpane.setPadding(inset);

	}

	/**
	 * PURPOSE: to cycle through the color options of a circle
	 * 
	 * @param circle with an altered color
	 */
	private void changeColor(Circle circle) {

		if (circle.getFill() == Color.BLACK) {
			circle.setFill(Color.RED);
		} else if (circle.getFill() == Color.RED) {
			circle.setFill(Color.ORANGE);
		} else if (circle.getFill() == Color.ORANGE) {
			circle.setFill(Color.YELLOW);
		} else if (circle.getFill() == Color.YELLOW) {
			circle.setFill(Color.GREEN);
		} else if (circle.getFill() == Color.GREEN) {
			circle.setFill(Color.BLUE);
		} else if (circle.getFill() == Color.BLUE) {
			circle.setFill(Color.PURPLE);
		} else if (circle.getFill() == Color.PURPLE) {
			circle.setFill(Color.RED);
		}

	}

}
